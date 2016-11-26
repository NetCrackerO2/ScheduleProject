package mvc.Commands;


public class ElementNotExistsException extends RuntimeException {
    public ElementNotExistsException() {
        super("ERR_ELEMENT_NOT_EXISTS");
    }
}
