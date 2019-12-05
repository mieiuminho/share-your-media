package database;

import model.AdminUser;
import model.MediaFile;

import java.util.Arrays;
import java.util.Set;

public final class MediaFileDAO extends DataAcessObject<String, MediaFile> {
    public MediaFileDAO() {
        super(new MediaFile(), "MEDIAFILES", Arrays.asList(new String[]{"name", "artist"}));
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
}
