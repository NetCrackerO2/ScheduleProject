package mvc.Commands;


import group.GroupManager;
import mvc.Command;
import mvc.Controller;


public class RemoveGroupCommand implements Command {
    @Override
    public void activate() {
        int groupNumber = Controller.getIntResponse("GROUP");
        if (!GroupManager.getInstance().isExist(groupNumber)) {
            throw new ElementNotExistsException();
        }

        try {
            GroupManager.getInstance().removeGroup(groupNumber);
        } catch (Exception ex) {
            throw new ElementNotRemovedException();
        }
    }

    @Override
    public String getTitle() {
        return "CMD_GROUP_REMOVE";
    }
}
