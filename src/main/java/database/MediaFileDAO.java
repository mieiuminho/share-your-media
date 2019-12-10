package database;

import model.MediaFile;
import util.CustomCategories;
import util.DefaultCategories;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Collection;

public final class MediaFileDAO extends DataAcessObject<String, MediaFile> {

    private CustomCategoriesDAO customCategories;
    private DefaultCategoriesDAO defaultCategories;

    public MediaFileDAO() {
        super(new MediaFile(), "MEDIAFILES", Arrays.asList("name", "artist", "ALBUM_name", "SERIES_name"));
        customCategories = new CustomCategoriesDAO();
        defaultCategories = new DefaultCategoriesDAO();
    }

    public boolean containsKey(final String name, final String artist) {
        return super.containsKey(name, artist);
    }

    public List<MediaFile> findByNameAndArtist(final String name, final String artist) {
        return super.find(name, artist);
    }

    public List<MediaFile> findByName(final String name) {
        return super.find(name);
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

    public List<String> userCategories(final String username, final String mediafileName,
            final String mediafileArtist) {
        CustomCategories ct = this.customCategories.get(username, mediafileName, mediafileArtist);
        return ct.getCategories();
    }

    public List<String> updateUserCategories(final String username, final String mediafileName,
            final String mediafileArtist, final String category1, final String category2, final String category3) {
        CustomCategories ct = new CustomCategories(username, mediafileName, mediafileArtist, category1, category2,
                category3);
        return this.customCategories.put(username, mediafileName, mediafileArtist, ct).getCategories();
    }

    public List<String> getDefaultCategories(final String mediafileName, final String mediafileArtist) {
        return this.defaultCategories.get(mediafileName, mediafileArtist).getCategories();
    }

    public List<String> updateDefaultCategories(final String mediafileName, final String mediafileArtist,
            final DefaultCategories dc) {
        return this.defaultCategories.put(mediafileName, mediafileArtist, dc).getCategories();
    }

    // TODO Temos de definir este metodo. Temos de o ter para percorrer os Media Files
    public Collection<MediaFile> values() {
        return null;
    }
}
