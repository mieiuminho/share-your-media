package database;

import model.MediaFile;

import java.util.Arrays;
import java.util.Set;
import java.util.Collection;

public final class MediaFileDAO extends DataAcessObject<String, MediaFile> {
    public MediaFileDAO() {
        super(new MediaFile(), "MEDIAFILES", Arrays.asList("name", "artist", "ALBUM_name", "SERIES_name"));
    }

    public boolean containsKey(final String name, final String artist) {
        return super.containsKey(name, artist);
    }

    public Set<MediaFile> searchByNameOrArtist(final String value) {
        return super.search(value, 0, 1);
    }

    public Set<MediaFile> searchByName(final String value) {
        return super.search(value, 0);
    }

    public Set<MediaFile> searchByArtist(final String value) {
        return super.search(value, 1);
    }

    public MediaFile get(final String name, final String artist) {
        return super.get(name, artist);
    }

    public MediaFile put(final MediaFile value) {
        return super.put(value, value.getName(), value.getArtist());
    }

    public MediaFile remove(final String name, final String artist) {
        return super.remove(name, artist);
    }

    // TODO Temos de definir este metodo. Temos de o ter para percorrer os Media Files
    public Collection<MediaFile> values() {
        return null;
    }
}
