package mvc.Commands;

import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;
import faculty.FacultyImpl;

public class AddNewCathedraCommand implements Command {
    @Override
    public void activate() {
        String name = Controller.getStringResponse("NEW_CATHEDRA");

        FacultyImpl faculty = new FacultyImpl(11);

        CathedraManager.getInstance().addNewCathedra(faculty, name);
    }

    @Override
    public String getTitle() {
        return "CMD_NEW_CATHEDRA";
    }
}
