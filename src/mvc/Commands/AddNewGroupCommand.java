package mvc.Commands;


import cathedra.Cathedra;
import cathedra.CathedraManager;
import group.GroupsManager;
import mvc.Command;
import mvc.Controller;


public class AddNewGroupCommand implements Command {

    @Override
    public void activate() {
        int groupNumber = Controller.getIntResponse("NEW_GROUP");
        if (GroupsManager.getInstance().isExist(groupNumber))
            throw new ElementAlreadyExistsException();

        String cathedraName = Controller.getStringResponse("CATHEDRA");
        if (!CathedraManager.getInstance().isExist(cathedraName))
            throw new ElementNotExistsException();

        Cathedra cathedra = CathedraManager.getInstance().getCathedra(cathedraName);
        GroupsManager.getInstance().getNewGroup(cathedra, groupNumber);
    }

    @Override
    public String getTitle() {
        return "CMD_GROUP_NEW";
    }
}
