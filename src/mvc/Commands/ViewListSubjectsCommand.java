package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListSubjectsCommand implements Command {

    @Override
    public void activate() {
        View.request("SUBJECT_LIST");
    }

    @Override
    public String getTitle() {
        return "CMD_SUBJECT_LIST";
    }

}
