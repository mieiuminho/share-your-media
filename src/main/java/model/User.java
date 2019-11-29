package model;

import util.Passwords;

public abstract class User {
    private String email;
    private String name;
    private String password;
    private String salt;

    /**
     * Constructor to recreate a User.
     *
     * @param email User's email.
     * @param name User's name.
     * @param password User's hashed password.
     * @param salt User's salt for password checking.
     */
    public User(final String email, final String name, final String password, final String salt) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Constructor to create a new User from scratch.
     *
     * @param email User's email.
     * @param name User's name.
     * @param input User's desired password.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    public User(final String email, final String name, final String input) {
        this.email = email;
        this.name = name;
        this.salt = Passwords.getSalt(8);
        this.password = Passwords.generate(input, salt);
    }

    /**
     * Provides the User's username.
     *
     * @return User's username.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Allows to set the User's username.
     *
     * @param email Desired username.
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Provides the User's email.
     *
     * @return User's email.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Provides the User's hashed password.
     *
     * @return User's hashed password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Provides the salt used to hash the User's password.
     *
     * @return User's salt.
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * @param salt
     */
    public void setSalt(final String salt) {
        this.salt = salt;
    }

    /**
     * @param input
     * @return
     */
    public boolean validate(final String input) {
        return Passwords.verify(input, this.getPassword(), this.getSalt());
    }

    /**
     *
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (!this.getEmail().equals(user.getEmail()))
            return false;
        if (!this.getName().equals(user.getName()))
            return false;
        if (!this.getPassword().equals(user.getPassword()))
            return false;
        return this.getSalt().equals(user.getSalt());
    }

    /**
     *
     */
    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public int hashCode() {
        int result = this.getEmail().hashCode();
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getPassword().hashCode();
        result = 31 * result + this.getSalt().hashCode();
        return result;
    }
}
