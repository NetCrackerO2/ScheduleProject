package mvc.Commands;

import cathedra.CathedraManager;
import group.Group;
import group.GroupManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;

import java.util.NoSuchElementException;

public class EditGroupCommand implements Command {

	@Override
	public void activate() {
		int groupNum = Controller.getIntResponse("GROUP");
		try {
			Group group = GroupManager.getInstance().getGroup(groupNum);
			try {
				group.setNumber(Controller.getIntResponse("NEW_GROUP"));                                
			} catch (Exception e) {
			    View.setStatus(new ElementNotEditedException());
			}
			String cathedraName = Controller.getStringResponse("CATHEDRA");
			try {
				group.setCathedra(CathedraManager.getInstance().getCathedra(cathedraName));
			} catch (Exception e) {
			    View.setStatus(new ElementNotEditedException());
			}
		} catch (NoSuchElementException e) {
		    throw new ElementNotExistsException();
		}
	}

	@Override
	public String getTitle() {
		return "CMD_GROUP_EDIT";
	}

}
