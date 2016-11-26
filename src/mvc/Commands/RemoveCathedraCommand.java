package mvc.Commands;

import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;
import java.util.NoSuchElementException;

public class RemoveCathedraCommand implements Command {
    @Override
    public void activate() {
        try {
            CathedraManager.getInstance().removeCathedra(Controller.getStringResponse("CATHEDRA"));
        } catch (NoSuchElementException ex) {
            throw new ElementNotExistsException();
        } catch (Exception ex) {
            throw new ElementNotRemovedException();
        }
    }

    @Override
    public String getTitle() {
        return "CMD_CATHEDRA_REMOVE";
    }
}
