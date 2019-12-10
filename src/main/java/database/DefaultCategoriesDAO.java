package database;

import util.DefaultCategories;

import java.util.Arrays;

public final class DefaultCategoriesDAO extends DataAcessObject<String, DefaultCategories> {

    public DefaultCategoriesDAO() {
        super(new DefaultCategories(), "DEFAULT_CATEGORIES",
                Arrays.asList("MEDIAFILE_name", "MEDIAFILE_artist", "category1", "category2", "category3"));
    }

    public DefaultCategories get(final String mediafileName, final String mediafileArtist) {
        return super.get(mediafileName, mediafileArtist);
    }

    public DefaultCategories put(final String mediafileName, final String mediafileArtist, final DefaultCategories dc) {
        return super.put(dc, mediafileName, mediafileArtist);
    }

    public DefaultCategories remove(final String mediafileName, final String mediafileArtist) {
        return super.remove(mediafileName, mediafileArtist);
    }

}
