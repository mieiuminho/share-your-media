package model;

import database.DataClass;
import database.MediaFileDAO;
import database.UserCollection;
import database.UserCollectionDAO;

import java.util.*;

public final class RegularUser extends User implements DataClass<String> {

    private UserCollectionDAO collection;
    private Map<String, Playlist> playlists;

    public RegularUser() {
        super();
        this.collection = UserCollectionDAO.getInstance();
    }

    public RegularUser(final String email, final String name, final String password, final String salt) {
        super(email, name, password, salt);
        this.collection = UserCollectionDAO.getInstance();
    }

    public RegularUser(final String email, final String name, final String input) {
        super(email, name, input);
        this.collection = UserCollectionDAO.getInstance();
        this.playlists = new HashMap<>();
    }

    public RegularUser(final List<String> values) {
        super(values);
        this.collection = UserCollectionDAO.getInstance();
    }

    @Override
    public DataClass<String> fromRow(final List<String> row) {
        return new RegularUser(row);
    }

    @Override
    public String toString() {
        return "RegularUser: " + super.toString() + '}';
    }

    public List<MediaFile> getCollection(final MediaFileDAO mf) {
        List<MediaFile> r = new ArrayList<>();

        List<UserCollection> col = this.collection.get(this.getEmail());

        for (UserCollection c : col)
            r.add(c.getMediaFile(mf));

        return r;
    }

    public void setCollection(final List<MediaFile> collection) {

        UserCollection r;

        for (MediaFile m : collection) {
            this.collection.remove(m.getName(), m.getArtist(), this.getEmail());
            r = new UserCollection(m.getName(), m.getArtist(), this.getEmail());
            this.collection.put(r, m.getName(), m.getArtist(), this.getEmail());
        }

    }

    public void addPlaylist(final String playListName, final Playlist playlist) {
        this.playlists.put(playListName, playlist);
    }
}
