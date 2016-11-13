package MVC.Commands;


import MVC.Command;
import MVC.View;


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
