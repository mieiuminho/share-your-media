package model;

import database.CustomCategoriesDAO;
import database.DataClass;
import database.DefaultCategoriesDAO;
import database.UploadersDAO;
import database.CustomCategories;
import database.DefaultCategories;
import database.Uploader;

import java.util.*;

public final class MediaFile implements DataClass<String> {

    private String name;
    private String artist;
    private String album;
    private String series;
    private DefaultCategoriesDAO defaultCategories;
    private CustomCategoriesDAO customCategories;
    private UploadersDAO uploaders;

    /**
     * Constructor
     *
     * @param name MediaFile's name.
     * @param artist MediaFile's artist.
     * @param categories MediaFile's categories.
     * @param uploader MediaFile's uploader.
     */
    public MediaFile(final String name, final String artist, final String album, final String series,
            final Collection<String> categories, final String uploader) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.series = series;
        this.defaultCategories = DefaultCategoriesDAO.getInstance();
        this.customCategories = CustomCategoriesDAO.getInstance();
        this.uploaders = UploadersDAO.getInstance();
    }

    public MediaFile(final String name, final String artist, final String album, final String series) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.series = series;
        this.defaultCategories = DefaultCategoriesDAO.getInstance();
        this.customCategories = CustomCategoriesDAO.getInstance();
        this.uploaders = UploadersDAO.getInstance();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public MediaFile(final List<String> values) {
        this.name = values.get(0);
        this.artist = values.get(1);
        this.album = values.get(2);
        this.series = values.get(3);
        this.defaultCategories = DefaultCategoriesDAO.getInstance();
        this.customCategories = CustomCategoriesDAO.getInstance();
        this.uploaders = UploadersDAO.getInstance();
    }

    public MediaFile() {
    }

    /**
     * Provides the MediaFile's name.
     *
     * @return MediaFile's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Provides the MediaFile's artist.
     *
     * @return MediaFile's artist.
     */
    public String getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getSeries() {
        return series;
    }

    /**
     * Provides the MediaFile's categories.
     *
     * @return MediaFile's categories.
     */
    public List<String> getDefaultCategories() {
        return this.defaultCategories.get(this.name, this.artist).getCategories();
    }

    public String getDefaultCategories1() {
        return this.getDefaultCategories().get(0);
    }

    public String getDefaultCategories2() {
        return this.getDefaultCategories().get(1);
    }

    public String getDefaultCategories3() {
        return this.getDefaultCategories().get(2);
    }

    /**
     * Provides, if exists, a user's categories for a Media Files. If the user hasn't set his own categories, returns
     * the default.
     *
     * @return A user's custom Media Files categories
     */
    public List<String> getCustomCategories(final String username) {
        return this.customCategories.get(username, this.name, this.artist).getCategories();
    }

    public String getCustomCategory1(final String username) {
        return this.getCustomCategories(username).get(0);
    }

    public String getCustomCategory2(final String username) {
        return this.getCustomCategories(username).get(1);
    }

    public String getCustomCategory3(final String username) {
        return this.getCustomCategories(username).get(2);
    }
    /**
     * Provides the MediaFile's uploaders.
     *
     * @return MediaFile's uploaders.
     */
    public Set<String> getUploaders() {
        List<Uploader> u = this.uploaders.get(this.name, this.artist);
        Set<String> r = new HashSet<>();
        for (Uploader up : u)
            r.add(up.getUploader());
        return r;
    }

    /**
     * Allows to set the MediaFile's name.
     *
     * @param name Desired name.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Allows to set the MediaFile's artist.
     *
     * @param artist Desired artist name.
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public void setAlbum(final String album) {
        this.album = album;
    }

    public void setSeries(final String series) {
        this.series = series;
    }

    /**
     * Allows to set the MediaFile's categories.
     *
     * @param categories Desired categories.
     */
    public void setDefaultCategories(final List<String> categories) {
        DefaultCategories dc = new DefaultCategories(this.name, this.artist, categories.get(0), categories.get(1),
                categories.get(2));
        this.defaultCategories.put(this.name, this.artist, dc);
    }

    /**
     * Allows to set the MediaFile's uploaders.
     *
     * @param uploaders Desired uploaders.
     */
    public void setUploaders(final Set<String> uploaders) {
        List<Uploader> ups = new ArrayList<>();
        for (String u : uploaders) {
            Uploader n = new Uploader(this.name, this.artist, u);
            this.uploaders.put(n, this.name, this.artist, u);
        }
    }

    /**
     * Removes an uploader from the uploaders Set.
     *
     * @param uploader Email of the uploader to remove.
     */
    public void removeUploader(final String uploader) {
        if (this.uploaders.contains(this.name, this.artist, uploader)) {
            this.uploaders.remove(this.name, this.artist, uploader);
        }
    }

    /**
     * Change MediaFile's custom categories for a given user.
     *
     * @param newCategories New categories.
     */
    public void setCustomCategories(final String username, final List<String> newCategories) {
        CustomCategories cc = new CustomCategories(username, this.name, this.artist, newCategories.get(0),
                newCategories.get(1), newCategories.get(2));
        this.customCategories.put(username, this.name, this.artist, cc);
    }

    /**
     * Checks if a Media File belongs to a given category for a given user. if the User hasn't set his custom
     * categories, uses the default
     */
    public boolean filter(final String username, final String category) {
        List<String> categories = this.customCategories.get(username, this.name, this.artist).getCategories();
        return categories.contains(category);
    }

    public void addUploader(final String email) {
        Uploader u = new Uploader(this.name, this.artist, email);
        this.uploaders.put(u);
    }

    @Override
    public DataClass<String> fromRow(final List<String> row) {
        return new MediaFile(row);
    }

    @Override
    public List<String> toRow() {
        List<String> row = new ArrayList<>();
        row.add(this.name);
        row.add(this.artist);
        row.add(this.album);
        row.add(this.series);
        return row;
    }
}
