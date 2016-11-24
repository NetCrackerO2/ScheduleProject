package mvc.Commands;

import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;

import java.util.NoSuchElementException;

public class RemoveCathedraCommand implements Command {
    @Override
    public void activate() {
        View.writeMessage("Введите название кафедры: ");
        try {
            CathedraManager.getInstance().removeCathedra(Controller.readString());
        }
        catch (NoSuchElementException ex)
        {
            View.writeElementDoesNotExists();
        }
        catch (Exception ex){
            View.writeMessage("Кафедра не удалена: "+ex.getMessage());
        }
        View.writeMessage("Кафедра успешно удалена");
    }

    @Override
    public String getTitle() {
        return "Удалить кафедру";
    }
}
