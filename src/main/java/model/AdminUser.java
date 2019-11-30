package model;

public class AdminUser extends User {

    public AdminUser(final String email, final String name, final String password, final String salt) {
        super(email, name, password, salt);
    }

    public AdminUser(final String email, final String name, final String input) {
        super(email, name, input);
    }
}
