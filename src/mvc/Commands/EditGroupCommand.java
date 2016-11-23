package mvc.Commands;

import java.util.NoSuchElementException;
import cathedra.CathedraManager;
import group.Group;
import group.GroupsManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;

public class EditGroupCommand implements Command {

	@Override
	public void activate() {
		View.writeReadGroupNumber();
		int groupNum = Controller.readInt();
		try {
			Group group = GroupsManager.getInstance().getGroup(groupNum);

			View.writeMessage("Введите новый номер группы ("+group.getNumber()+"): ");
			try {
				group.setNumber(Controller.readInt());
			} catch (Exception e) {
				View.writeError("Номер группы не был изменен: "+e.getMessage());
			}
			
			View.writeMessage("Введите новое название кафедры группы ("+group.getCathedra().getName()+"): ");
			String cathedraName = Controller.readString();
			try {
				group.setCathedra(CathedraManager.getInstance().getCathedra(cathedraName));
			} catch (Exception e) {
				View.writeError("Кафедра не была изменена: "+e.getMessage());
			}
		} catch (NoSuchElementException e) {
			View.writeElementDoesNotExists();
		}

	}

	@Override
	public String getTitle() {
		return "Изменить группу";
	}

}
