package mvc.Commands;

import cathedra.Cathedra;
import cathedra.CathedraManager;
import group.GroupsManager;
import mvc.Command;
import mvc.Controller;
import java.util.NoSuchElementException;

public class AddNewGroupCommand implements Command {

    @Override
    public void activate() {
        int number = Controller.getIntResponse("NEW_GROUP");

        String cathedraName = Controller.getStringResponse("CATHEDRA");
        Cathedra cathedra = null;
        try {
            cathedra = CathedraManager.getInstance().getCathedra(cathedraName);
        } catch (NoSuchElementException e) {
            throw new ElementNotExistsException();
        }

        GroupsManager.getInstance().getNewGroup(cathedra, number);
    }

    @Override
    public String getTitle() {
        return "CMD_GROUP_NEW";
    }
}
