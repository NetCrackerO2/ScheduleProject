package mvc.Commands;


import account.AccountManager;
import faculty.Faculty;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;


public class EditFacultyCommand implements Command {
    @Override
    public void activate() {
        int facultyNum = Controller.getIntResponse("FACULTY");
        String dean = Controller.getStringResponse("ACCOUNT");
        try {
            Faculty faculty = FacultyManager.getInstance().getFaculty(facultyNum);
            try {
                faculty.setNumber(Controller.getIntResponse("NEW_FACULTY"));
            } catch (Exception e) {
                View.setStatus(new ElementNotEditedException());
            }
            try {
                faculty.setDean(AccountManager.getInstance().getAccount(dean));
            } catch (Exception e) {
                View.setStatus(new ElementNotEditedException());
            }
        } catch (Exception e) {
            View.setStatus(new ElementNotEditedException());
        }
    }

    @Override
    public String getTitle() {
        return "CMD_FACULTY_EDIT";
    }
}

