package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListFacultiesCommand implements Command {

    @Override
    public void activate() {
        View.writeAllFaculties();
    }

    @Override
    public String getTitle() {
        return "Вывод всех факультетов";
    }
}
