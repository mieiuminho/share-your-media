package model;

import java.util.HashSet;
import java.util.Set;

public final class Playlist {

    private String name;
    private String creator;
    private String criteria;
    private Set<String> mediafiles;

    /**
     * Constructor
     *
     * @param name Playlist's name.
     * @param creator Playlist's creator email.
     * @param criteria Playlist's criteria.
     */
    public Playlist(final String name, final String creator, final String criteria) {
        this.name = name;
        this.creator = creator;
        this.criteria = criteria;
        this.mediafiles = new HashSet<>();
    }

    public void addMediaFile(final String mediaFileName) {
        this.mediafiles.add(mediaFileName);
    }
}
