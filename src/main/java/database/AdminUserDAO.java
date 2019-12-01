package database;

import model.AdminUser;

import java.util.Arrays;

public final class AdminUserDAO extends DataAcessObject<String, AdminUser> {

    public AdminUserDAO() {
        super(new AdminUser(), "ADMIN_USERS", Arrays.asList(new String[]{"email", "name", "password", "salt"}));
    }

    public AdminUser get(final String key) {
        return super.get(key);
    }

    public AdminUser put(final String key, final AdminUser value) {
        return super.put(key, value);
    }

    public AdminUser remove(final String key) {
        return super.remove(key);
    }
}
