package mvc.Commands;


public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException() {
        super("ERR_NO_PERMISSIONS");
    }
}
