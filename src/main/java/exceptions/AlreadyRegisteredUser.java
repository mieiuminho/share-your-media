package exceptions;

public class AlreadyRegisteredUser extends Exception {

    public AlreadyRegisteredUser() {
        super("This email already belongs to another user!");
    }
}
