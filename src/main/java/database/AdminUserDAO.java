package database;

import model.AdminUser;
import model.RegularUser;

import java.util.Arrays;
import java.util.Set;

public final class AdminUserDAO extends DataAcessObject<String, AdminUser> {

    public AdminUserDAO() {
        super(new AdminUser(), "ADMIN_USERS", Arrays.asList(new String[]{"email", "name", "password", "salt"}));
    }

    public AdminUser get(final String key) {
        return super.get(key);
    }

    public Set<AdminUser> searchByNameOrEmail(final String value) {
        return super.search(value, 1, 0);
    }

    public Set<AdminUser> searchByEmail(final String value) {
        return super.search(value, 0);
    }

    public Set<AdminUser> searchByName(final String value) {
        return super.search(value, 1);
    }

    public AdminUser put(final AdminUser value) {
        return super.put(value, value.getEmail());
    }

    public AdminUser remove(final String key) {
        return super.remove(key);
    }
}
