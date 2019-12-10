package model;

import database.DataClass;

import java.util.*;

public final class MediaFile implements DataClass<String> {

    private String name;
    private String artist;
    private String album;
    private String series;
    // TODO: Falta ver as tabelas para as categorias e uploaders
    private Set<String> defaultCategories;
    private Map<String, Set<String>> customCategories;
    private Set<String> uploaders;

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
        this.defaultCategories = new HashSet<>(categories);
        this.customCategories = new HashMap<>();
        this.uploaders = new HashSet<>();
        this.uploaders.add(uploader);
    }

    public MediaFile(final String fileName, final String artist, final String album, final String series) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.series = series;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public MediaFile(final List<String> values) {
        this.name = values.get(0);
        this.artist = values.get(1);
        this.album = values.get(2);
        this.series = values.get(3);
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
    public Set<String> getDefaultCategories() {
        return this.defaultCategories;
    }

    /**
     * Provides, if exists, a user's categories for a Media Files. If the user hasn't set his own categories, returns
     * the default.
     *
     * @return A user's custom Media Files categories
     */
    public Set<String> getCustomCategories(final String username) {

        if (this.customCategories.containsKey(username)) {
            return this.customCategories.get(username);
        } else {
            return this.defaultCategories;
        }
    }

    /**
     * Provides the MediaFile's uploaders.
     *
     * @return MediaFile's uploaders.
     */
    public Set<String> getUploaders() {
        return this.uploaders;
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

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    /**
     * Allows to set the MediaFile's categories.
     *
     * @param categories Desired categories.
     */
    public void setCategories(final Set<String> categories) {
        this.defaultCategories = new HashSet<>(categories);
    }

    /**
     * Allows to set the MediaFile's uploaders.
     *
     * @param uploaders Desired uploaders.
     */
    public void setUploaders(final Set<String> uploaders) {
        this.uploaders = new HashSet<>(uploaders);
    }

    /**
     * Removes an uploader from the uploaders Set.
     *
     * @param uploader Email of the uploader to remove.
     */
    public void removeUploader(final String uploader) {
        if (this.uploaders.contains(uploader)) {
            this.uploaders.remove(uploader);
        }
    }

    /**
     * Change MediaFile's default categories.
     *
     * @param newCategorias New categories.
     */
    public void bindDefaultCategories(final Collection<String> newCategorias) {
        this.defaultCategories.clear();
        this.defaultCategories.addAll(newCategorias);
    }

    /**
     * Change MediaFile's custom categories for a given user.
     *
     * @param newCategories New categories.
     */
    public void bindCustomCategories(final String username, final Collection<String> newCategories) {
        this.customCategories.put(username, new HashSet<String>());
        this.customCategories.get(username).addAll(newCategories);
    }

    /**
     * Checks if a Media File belongs to a given category for a given user. if the User hasn't set his custom
     * categories, uses the default
     */
    public boolean filter(final String username, final String category) {
        if (this.customCategories.containsKey(username)) {
            return this.customCategories.get(username).contains(category);
        } else {
            return this.defaultCategories.contains(category);
        }
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
