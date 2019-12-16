package model;

import java.io.*;
import java.net.Socket;
import java.util.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import database.AdminUserDAO;
import database.MediaFileDAO;
import database.RegularUserDAO;

import exceptions.*;

/**
 * TODO: Tínhamos falado em meter as categorias num ENUM TODO: Os critérios para gerar playlists também podem ser uma
 * coisa do género
 */

public final class MediaCenter {

    private static final String HOSTNAME = System.getenv("SYM_SERVER_HOSTNAME");
    private static final int PORT = Integer.parseInt(System.getenv("SYM_SERVER_PORT"));
    private static final String USER_DATA_DIR = System.getenv("SYM_USER_DATA_DIR");

    private Socket socket;
    private String loggedIn;
    private boolean isAdmin;
    private AdminUserDAO admins;
    private RegularUserDAO users;
    private MediaFileDAO mediafiles;
    private Map<String, Playlist> albums;
    private Map<String, Playlist> seasons;

    public MediaCenter() {
        this.loggedIn = null;
        this.isAdmin = false;
        this.admins = AdminUserDAO.getInstance();
        this.users = RegularUserDAO.getInstance();
        this.mediafiles = MediaFileDAO.getInstance();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(final boolean admin) {
        this.isAdmin = admin;
    }

    public void loginUser(final String email, final String password) throws AuthenticationException {
        if (this.users.containsKey(email)) {
            if (this.users.get(email).validate(password))
                this.loggedIn = email;
            else
                throw new AuthenticationException("Wrong password!");
        } else
            throw new AuthenticationException("This user is not registered!\n\nPlease type a valid email!");
    }

    public void loginAdmin(final String email, final String password) throws AuthenticationException {
        if (this.admins.containsKey(email)) {
            if (this.admins.get(email).validate(password))
                this.loggedIn = email;
            else
                throw new AuthenticationException("Wrong password!");
        } else
            throw new AuthenticationException("This user is not registered!\n\nPlease type a valid email!");
    }

    public void logout() {
        this.loggedIn = null;
        this.isAdmin = false;
    }

    public void registerRegularUser(final String email, final String name, final String password)
            throws DuplicatedUserException {
        if (!this.users.containsKey(email)) {
            this.users.put(new RegularUser(email, name, password));
        } else
            throw new DuplicatedUserException("This email is already registered with another user!");
    }

    public void registerAdminUser(final String email, final String name, final String password)
            throws DuplicatedUserException {
        if (!this.admins.containsKey(email)) {
            this.admins.put(new AdminUser(email, name, password));
        } else
            throw new DuplicatedUserException("This email is already registered with another admin!");
    }

    public void editRegularUserName(final String email, final String newName) throws NoSuchUserException {
        if (this.users.containsKey(email)) {
            this.users.get(email).setName(newName);
        } else
            throw new NoSuchUserException();
    }

    public void editRegularUserEmail(final String email, final String newEmail) throws NoSuchUserException {
        if (this.users.containsKey(email)) {
            this.users.get(email).setEmail(newEmail);
        } else
            throw new NoSuchUserException();
    }

    public void editRegularUserPassword(final String email, final String newPassword) throws NoSuchUserException {
        if (this.users.containsKey(email)) {
            this.users.get(email).setPassword(newPassword);
        } else
            throw new NoSuchUserException();
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public void uploadMedia(String name, String artist, String album, String series, final File file)
            throws IOException, LackOfPermissions {

        if (this.loggedIn == null) {
            throw new LackOfPermissions("You're not currently logged in. This action is not available.");
        }

        InputStream input = new FileInputStream(file);
        ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata();
        Parser parser = new Mp3Parser();
        ParseContext parseCtx = new ParseContext();
        try {
            parser.parse(input, handler, metadata, parseCtx);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
        input.close();

        if (name.isBlank())
            name = metadata.get("title");
        if (artist.isBlank())
            artist = metadata.get("xmpDM:artist");
        if (album.isBlank())
            album = metadata.get("xmpDM:album");

        List<String> categories = new ArrayList<>(3);
        categories.add(metadata.get("xmpDM:genre"));
        categories.add("");
        categories.add("");

        String data = Base64.getEncoder().encodeToString(com.google.common.io.Files.toByteArray(file));

        String message = "upload " + name.replaceAll(" ", "_") + "+" + artist.replaceAll(" ", "_") + " " + data;

        this.socket = new Socket(HOSTNAME, PORT);

        // BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(this.socket.getOutputStream());

        out.println(message);
        out.flush();

        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();

        if (this.mediafiles.containsKey(name, artist)) {
            MediaFile mediaFile = this.mediafiles.get(name, artist);
            mediaFile.addUploader(this.loggedIn);
            mediaFile.setAlbum(album);
            mediaFile.setSeries(series);
            this.mediafiles.put(mediaFile);
        } else {
            this.mediafiles.put(new MediaFile(name, artist, album, series, categories, this.loggedIn));
        }
    }

    public String downloadMedia(final String name, final String artist) throws IOException {
        String fileName = name.replaceAll(" ", "_") + "+" + artist.replaceAll(" ", "_");

        if (!new File(USER_DATA_DIR + fileName).isFile()) {
            this.socket = new Socket(HOSTNAME, PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

            out.println("download " + fileName);

            String data = in.readLine();
            com.google.common.io.Files.write(Base64.getDecoder().decode(data), new File(USER_DATA_DIR + fileName));

            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }

        return USER_DATA_DIR + fileName;
    }

    private void organize(final String fileName, final String groupId) {

        // referimo-nos nos diagrama a esta operacao como: getExtension()
        String ext = com.google.common.io.Files.getFileExtension(fileName);

        if (groupId == null) {
            return;
        }

        if (ext.equals("mp3")) {
            if (this.albums.containsKey(groupId)) {
                this.albums.get(groupId).addMediaFile(fileName);
            } else {
                Playlist novoAlbum = new Playlist(groupId, "system", null);
                novoAlbum.addMediaFile(fileName);
                this.albums.put(groupId, novoAlbum);
            }
        } else if (ext.equals("mp4")) {
            if (this.seasons.containsKey(groupId)) {
                this.seasons.get(groupId).addMediaFile(fileName);
            } else {
                Playlist novaSeason = new Playlist(groupId, "system", null);
                novaSeason.addMediaFile(fileName);
                this.seasons.put(groupId, novaSeason);
            }
        }
    }

    public void removeFromMediaCenter(final String mediaFileName) throws NoSuchMediaFile, LackOfPermissions {
        if (this.mediafiles.containsKey(mediaFileName)) {
            MediaFile mf = this.mediafiles.get(mediaFileName);
            if (mf.getUploaders().contains(loggedIn)) {
                if (mf.getUploaders().size() == 1) {
                    this.mediafiles.remove(mediaFileName);
                } else {
                    mf.getUploaders().remove(loggedIn);
                }
            } else
                throw new LackOfPermissions("User doesn't have permission to remove this mediafile!");
        } else
            throw new NoSuchMediaFile("The Media File you tried to delete does not exist!");
    }

    public void changeMediaFileCategories(final String mediaFileName, final List<String> categories)
            throws NoSuchMediaFile, LackOfPermissions {

        if (this.loggedIn == null) {
            throw new LackOfPermissions("You're not currently logged in. This action is not available");
        }

        if (this.mediafiles.containsKey(mediaFileName)) {
            this.mediafiles.get(mediaFileName).setCustomCategories(this.loggedIn, categories);
        } else
            throw new NoSuchMediaFile("The Media File does not exist!");
    }

    /**
     * Creates a playlist following the given criteria. This playlist belongs and is added to the user currently logged
     * into the system.
     */
    @SuppressWarnings({"checkstyle:EmptyBlock"})
    public void createPlaylist(final String playListName, final String type, final String argument)
            throws LackOfPermissions {

        if (this.loggedIn == null) {
            throw new LackOfPermissions("You're not currently logged in. This feature is not available");
        }

        Playlist novaPlaylist = new Playlist(playListName, this.loggedIn, type);

        switch (type.toLowerCase()) {
            case "random" :
                // TODO: Maneira de escolher MFs random
                break;

            case "artist" :
                for (MediaFile mf : this.mediafiles.values()) {
                    if (mf.getArtist().toLowerCase().equals(argument.toLowerCase()))
                        novaPlaylist.addMediaFile(mf.getName());
                }
                break;

            case "category" :
                for (MediaFile mf : this.mediafiles.values()) {
                    if (mf.filter(this.loggedIn, argument.toLowerCase()))
                        novaPlaylist.addMediaFile(mf.getName());
                }
                break;
            default :
                // Não sei se devíamos por aqui alguma coisa
                break;
        }

        this.users.get(this.loggedIn).addPlaylist(playListName, novaPlaylist);

    }

    public Set<MediaFile> searchMediaByNameOrArtist(final String value) {
        return this.mediafiles.searchByNameOrArtist(value);
    }

    public Collection<MediaFile> getMediaFiles() {
        return this.mediafiles.values();
    }

    public void addMedia(final MediaFile media) {
        this.mediafiles.put(media);
    }
}
