package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListFacultiesCommand implements Command {

    @Override
    public void activate() {
        View.request("FACULTY_LIST");
    }

    @Override
    public String getTitle() {
        return "CMD_FACULTY_LIST";
    }
}
