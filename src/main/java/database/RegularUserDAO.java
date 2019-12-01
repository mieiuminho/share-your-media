package database;

import model.RegularUser;

import java.util.Arrays;

public final class RegularUserDAO extends DataAcessObject<String, RegularUser> {

    public RegularUserDAO() {
        super(new RegularUser(), "REGULAR_USERS", Arrays.asList(new String[]{"email", "name", "password", "salt"}));
    }

    public RegularUser get(final String key) {
        return super.get(key);
    }

    public RegularUser put(final String key, final RegularUser value) {
        return super.put(key, value);
    }

    public RegularUser remove(final String key) {
        return super.remove(key);
    }
}
