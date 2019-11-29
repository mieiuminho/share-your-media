package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class MediaFile {

    private String name;
    private String artist;
    private Set<String> categories;
    private Set<String> uploaders;

    /**
     * Constructor
     * 
     * @param name MediaFile's name.
     * @param artist MediaFile's artist.
     * @param categories MediaFile's categories.
     * @param uploader MediaFile's uploader.
     */
    public MediaFile(final String name, final String artist, final Collection<String> categories,
            final String uploader) {
        this.name = name;
        this.artist = artist;
        this.categories = new HashSet<>(categories);
        this.uploaders = new HashSet<>();
        this.uploaders.add(uploader);
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

    /**
     * Provides the MediaFile's categories.
     * 
     * @return MediaFile's categories.
     */
    public Set<String> getCategories() {
        return this.categories;
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

    /**
     * Allows to set the MediaFile's categories.
     * 
     * @param categories Desired categories.
     */
    public void setCategories(final Set<String> categories) {
        this.categories = new HashSet<>(categories);
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
     * Change MediaFile's categories.
     * 
     * @param newCategorias New categories.
     */
    public void bindCategories(final Collection<String> newCategorias) {
        this.categories.clear();
        this.categories.addAll(newCategorias);
    }
}