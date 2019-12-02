package database;

import model.MediaFile;

import java.util.Arrays;

public final class MediaFileDAO extends DataAcessObject<String, MediaFile> {
    public MediaFileDAO() {
        super(new MediaFile(), "MEDIAFILES", Arrays.asList(new String[]{"name", "artist"}));
    }

    public boolean containsKey(final String name, final String artist) {
        return super.containsKey(name, artist);
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
