package model;

public class RegularUser extends User {

    public RegularUser(final String email, final String name, final String password, final String salt) {
        super(email, name, password, salt);
    }

    public RegularUser(final String email, final String name, final String input) {
        super(email, name, input);
    }
}
