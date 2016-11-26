package mvc.Commands;

import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

public class AddNewFacultyCommand implements Command {

    @Override
    public void activate() {
        int number = Controller.getIntResponse("NEW_FACULTY");

        FacultyManager.getInstance().addNewFaculty(number);
    }

    @Override
    public String getTitle() {
        return "CMD_FACULTY_NEW";
    }

}
