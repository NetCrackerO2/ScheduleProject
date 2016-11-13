package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListGroupsCommand implements Command {

    public ViewListGroupsCommand() {
    }

    @Override
    public void activate() {
        View.writeAllGroups();
    }

    @Override
    public String getTitle() {
        return "Вывод всех групп";
    }
}
