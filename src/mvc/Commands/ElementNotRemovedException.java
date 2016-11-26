package mvc.Commands;


public class ElementNotRemovedException extends RuntimeException {
    public ElementNotRemovedException() {
        super("ERR_ELEMENT_NOT_REMOVED");
    }
}
