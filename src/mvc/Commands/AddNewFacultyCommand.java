package mvc.Commands;


import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;


public class AddNewFacultyCommand implements Command {

    @Override
    public void activate() {
        int facultyNumber = Controller.getIntResponse("NEW_FACULTY");

        if (FacultyManager.getInstance().isExist(facultyNumber))
            throw new ElementAlreadyExistsException();

        FacultyManager.getInstance().getNewFaculty(facultyNumber);
    }

    @Override
    public String getTitle() {
        return "CMD_FACULTY_NEW";
    }

}
