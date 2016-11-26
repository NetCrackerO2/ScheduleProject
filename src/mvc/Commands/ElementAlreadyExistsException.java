package mvc.Commands;


public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException() {
        super("ERR_ELEMENT_ALREADY_EXISTS");
    }
}
