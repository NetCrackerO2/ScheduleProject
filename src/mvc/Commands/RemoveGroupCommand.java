package mvc.Commands;

import group.GroupsManager;
import mvc.Command;
import mvc.Controller;
import java.util.NoSuchElementException;

/**
 * Created by Dmi3 on 24.11.2016.
 */
public class RemoveGroupCommand implements Command {
    @Override
    public void activate() {
        try {
            GroupsManager.getInstance().removeGroup(Controller.getIntResponse("GROUP"));
        } catch (NoSuchElementException ex) {
            throw new ElementNotExistsException();
        } catch (Exception ex) {
            throw new ElementNotRemovedException();
        }
    }

    @Override
    public String getTitle() {
        return "CMD_GROUP_REMOVE";
    }
}
