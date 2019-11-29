package exceptions;

public class LackOfPermissions extends Exception {

    public LackOfPermissions(String message) {
        super(message);
    }
}
