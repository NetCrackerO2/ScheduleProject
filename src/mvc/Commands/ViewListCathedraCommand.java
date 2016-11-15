package mvc.Commands;

import mvc.Command;
import mvc.View;

public class ViewListCathedraCommand implements Command{
    @Override
    public void activate(){
        View.writeAllCathedra();
    }

    @Override
    public String getTitle(){
        return "Список всех кафедр";
    }
}
