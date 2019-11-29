package model;

import java.util.Set;
import java.util.TreeSet;

public class RegularUser extends User {
    private Set<String> collection;

    public RegularUser(final String email, final String name, final String password, final String salt) {
        super(email, name, password, salt);
    }

    public RegularUser(final String email, final String name, final String input) {
        super(email, name, input);
        this.collection = new TreeSet<>();
    }

    public Set<String> getCollection() {
        Set<String> collection = new TreeSet<>();

        for (String music : this.collection)
            collection.add(music);

        return collection;
    }

    public void setCollection(Set<String> collection) {
        this.collection = new TreeSet<>();

        for (String music : collection)
            this.collection.add(music);
    }
}
