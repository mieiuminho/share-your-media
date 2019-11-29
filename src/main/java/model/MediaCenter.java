package model;

import exceptions.*;

import java.util.Map;
import java.util.Set;

/**
 * TODO: Tínhamos falado em meter as categorias num ENUM TODO: Os critérios para gerar playlists também podem ser uma
 * coisa do género
 */

public final class MediaCenter {

    private static String[] supportedExtensions = {"mp3", "mp4"};

    @SuppressWarnings("checkstyle:MagicNumber")
    private static int passwordScore(final String password) throws NotSecureEnoughPassword {
        int passwordScore = 0;
        if (password.length() > 8) {
            if (password.length() > 10)
                passwordScore += 2;
            else
                passwordScore += 1;
        } else
            throw new NotSecureEnoughPassword("Your password must contain at least 8 characters!");

        if (password.matches("(?=.*[0-9]).*"))
            passwordScore += 2;
        else
            throw new NotSecureEnoughPassword("Your password must contain at least an alphanumeric character!");

        if ((password.matches("(?=.*[a-z]).*")) && (password.matches("(?=.*[A-Z]).*")))
            passwordScore += 2;
        else
            throw new NotSecureEnoughPassword("Your password must contain both upper and lower case characters!");

        if (password.matches("(?=.*[~!@#$%^&*()_-]).*"))
            passwordScore += 3;

        return passwordScore;
    }

    private static boolean checkExtension(final String fileName) {
        String extension = fileName.split(".")[1];
        boolean supported = false;
        for (int i = 0; i < supportedExtensions.length && supported == false; i++)
            if (extension.equals(supportedExtensions[i]))
                supported = true;
        return supported;
    }

    private String loggedIn;
    private Map<String, MediaFile> mediafiles;
    private Map<String, User> users;

    public String getLoggedIn() {
        return this.loggedIn;
    }

    public Map<String, MediaFile> getMediafiles() {
        return this.mediafiles;
    }

    public Map<String, User> getUsers() {
        return this.users;
    }

    public void setLoggedIn(final String email) {
        this.loggedIn = email;
    }

    public void setMediafiles(final Map<String, MediaFile> mediafiles) {
        this.mediafiles = mediafiles;
    }

    public void setUsers(final Map<String, User> users) {
        this.users = users;
    }

    public void login(final String email, final String password) throws AuthenticationException {
        if (this.users.containsKey(email)) {
            if (this.users.get(email).validate(password))
                this.loggedIn = email;
        } else
            throw new AuthenticationException("This user is not registered! \n Please type a valid email!");
    }

    public void logout() {
        this.setLoggedIn(null);
    }

    public void addUserBasic(final String username, final String email, final String password,
            final int permissionLevel) throws AlreadyRegisteredUser {
        if (this.users.containsKey(email))
            throw new AlreadyRegisteredUser();
        else {
            if (permissionLevel == 1) {
                AdminUser n = new AdminUser(username, email, password);
                this.users.put(email, n);
            } else {
                RegularUser n = new RegularUser(username, email, password);
                this.users.put(email, n);
            }
        }
    }

    public void editName(final String email, final String newName) throws NoSuchUserException {
        if (this.users.containsKey(email)) {
            this.users.get(email).setName(newName);
        } else
            throw new NoSuchUserException();
    }

    public void editEmail(final String email, final String newEmail) throws NoSuchUserException {
        if (this.users.containsKey(email)) {
            this.users.get(email).setEmail(newEmail);
        } else
            throw new NoSuchUserException();
    }

    public void editPassword(final String email, final String newPassword) throws NoSuchUserException {
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
