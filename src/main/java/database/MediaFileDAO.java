package database;

import model.MediaFile;

import java.util.Arrays;

public final class MediaFileDAO extends DataAcessObject<String, MediaFile> {

    public MediaFileDAO() {
        super(new MediaFile(), "MEDIAFILES", Arrays.asList(new String[]{"name", "artist"}));
    }

    public MediaFile get(final String key) {
        return super.get(key);
    }

    public MediaFile put(final String key, final MediaFile value) {
        return super.put(key, value);
    }

    public MediaFile remove(final String key) {
        return super.remove(key);
    }
}
