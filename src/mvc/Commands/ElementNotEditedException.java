package mvc.Commands;

public class ElementNotEditedException extends RuntimeException {
    public ElementNotEditedException(){
        super("ERR_ELEMENT_NOT_EDITED");
    }
}
