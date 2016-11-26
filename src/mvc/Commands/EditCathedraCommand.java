package mvc.Commands;

import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;
import java.util.NoSuchElementException;

/**
 * Created by Dmi3 on 24.11.2016.
 */
public class EditCathedraCommand implements Command {

    @Override
    public void activate() {
        try {
            String oldCathedraName = Controller.getStringResponse("CATHEDRA");
            String newCathedraName = Controller.getStringResponse("NEW_CATHEDRA");
            Faculty fac = FacultyManager.getInstance().getFaculty(Controller.getIntResponse("FACULTY"));
            CathedraManager.getInstance().setCathedra(oldCathedraName, newCathedraName, fac);
        }
        catch(NoSuchElementException e)
        {
            throw new ElementNotExistsException();
        }
        catch(Exception e){
            throw new ElementNotEditedException();
        }
    }


    @Override
    public String getTitle() {
        return "CMD_CATHEDRA_EDIT";
    }
}
