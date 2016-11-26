package mvc.Commands;


import cathedra.CathedraManager;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;


public class AddNewCathedraCommand implements Command {
    @Override
    public void activate() {
        String cathedraName = Controller.getStringResponse("NEW_CATHEDRA");
        if (CathedraManager.getInstance().isExist(cathedraName))
            throw new ElementAlreadyExistsException();

        int facultyNumber = Controller.getIntResponse("FACULTY");
        if (!FacultyManager.getInstance().isExist(facultyNumber))
            throw new ElementNotExistsException();

        CathedraManager.getInstance().addNewCathedra(FacultyManager.getInstance().getFaculty(facultyNumber), cathedraName);
    }

    @Override
    public String getTitle() {
        return "CMD_CATHEDRA_NEW";
    }
}
