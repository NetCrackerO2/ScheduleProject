package mvc.Commands;

import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;

import java.util.NoSuchElementException;

/**
 * Created by Dmi3 on 24.11.2016.
 */
public class EditCathedraCommand implements Command {

    @Override
    public void activate() {
        try {
            View.writeMessage("Введите название кафедры: ");
            String oldCathedraName = Controller.readString();
            View.writeMessage("Введите новое название кафедры: ");
            String newCathedraName = Controller.readString();
            CathedraManager manager = CathedraManager.getInstance();
            View.writeMessage("Введите номер факультета: ");
            Faculty fac = FacultyManager.getInstance().getFaculty(Controller.readInt());
            manager.setCathedra(oldCathedraName, newCathedraName, fac);
        }
        catch(NoSuchElementException e)
        {
         View.writeElementDoesNotExists();
        }
        catch(Exception e){
            View.writeMessage("Кафедра не изменена: "+e.getMessage());
        }
        View.writeMessage("Кафедра успешно изменена");
    }


    @Override
    public String getTitle() {
        return "Изменить кафедру";
    }
}
