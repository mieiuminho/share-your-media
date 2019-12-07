package model;

import database.DataClass;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.List;

public final class RegularUser extends User implements DataClass<String> {
    private Set<String> collection;
    private Map<String, Playlist> playlists;

    public RegularUser() {
        super();
    }

    public RegularUser(final String email, final String name, final String password, final String salt) {
        super(email, name, password, salt);
    }

    public RegularUser(final String email, final String name, final String input) {
        super(email, name, input);
        this.collection = new TreeSet<>();
        this.playlists = new HashMap<>();
    }

    public RegularUser(final List<String> values) {
        super(values);
    }

    @Override
    public DataClass<String> fromRow(final List<String> row) {
        return new RegularUser(row);
    }

    @Override
    public String toString() {
        return "RegularUser: " + super.toString() + '}';
    }

    public Set<String> getCollection() {
        Set<String> result = new TreeSet<>();

        for (String music : this.collection)
            result.add(music);

        return result;
    }

    public void setCollection(final Set<String> collection) {
        this.collection = new TreeSet<>();

        for (String music : collection)
            this.collection.add(music);
    }

    public void addPlaylist(final String playListName, final Playlist playlist) {
        this.playlists.put(playListName, playlist);
    }
}
