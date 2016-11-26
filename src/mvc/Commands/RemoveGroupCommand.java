package mvc.Commands;

import group.GroupsManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;

import java.util.NoSuchElementException;

/**
 * Created by Dmi3 on 24.11.2016.
 */
public class RemoveGroupCommand implements Command {
    @Override
    public void activate() {
        View.writeMessage("Введите номер группы: ");
        try {
            GroupsManager.getInstance().removeGroup(Controller.readInt());
        }
        catch (NoSuchElementException ex)
        {
            View.writeElementDoesNotExists();
        }
        catch (Exception ex){
            View.writeMessage("Группа не удалена: "+ex.getMessage());
        }
        View.writeMessage("Группа успешно удалена");
    }

    @Override
    public String getTitle() {
        return "Удалить группу";
    }
}
