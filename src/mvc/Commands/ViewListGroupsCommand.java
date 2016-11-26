package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListGroupsCommand implements Command {
    @Override
    public void activate() {
        View.request("GROUP_LIST");
    }

    @Override
    public String getTitle() {
        return "CMD_GROUP_LIST";
    }
}
