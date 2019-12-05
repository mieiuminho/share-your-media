package model;

import database.AdminUserDAO;
import database.MediaFileDAO;
import database.RegularUserDAO;
import exceptions.*;

import java.util.Arrays;
import java.util.Set;

/**
 * TODO: Tínhamos falado em meter as categorias num ENUM TODO: Os critérios para gerar playlists também podem ser uma
 * coisa do género
 */

public final class MediaCenter {

    private static String[] supportedExtensions = {"mp3", "mp4"};

    private static boolean checkExtension(final String fileName) {
        String extension = fileName.split(".")[1];
        return Arrays.stream(supportedExtensions).anyMatch(e -> e.equals(extension));
    }

    private String loggedIn;
    private boolean isAdmin;
    private AdminUserDAO admins;
    private RegularUserDAO users;
    private MediaFileDAO mediafiles;

    public MediaCenter() {
        this.loggedIn = null;
        this.isAdmin = false;
        this.admins = new AdminUserDAO();
        this.users = new RegularUserDAO();
        this.mediafiles = new MediaFileDAO();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
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
            this.users.put(email, new RegularUser(email, name, password));
        } else
            throw new DuplicatedUserException("This email is already registered with another user!");
    }

    public void registerAdminUser(final String email, final String name, final String password)
            throws DuplicatedUserException {
        if (!this.admins.containsKey(email)) {
            this.admins.put(email, new AdminUser(email, name, password));
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

    public void uploadToMediaCenter(final String fileName, final String filePath) {
        // TODO: copiar do filesystem do user para o sítio onde se vai armazenar
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

    public void changeMediaFileCategories(final String mediaFileName, final Set<String> categories)
            throws NoSuchMediaFile {
        if (this.mediafiles.containsKey(mediaFileName)) {
            this.mediafiles.get(mediaFileName).bindCategories(categories);
        } else
            throw new NoSuchMediaFile("The Media File does not exist!");
    }
}
