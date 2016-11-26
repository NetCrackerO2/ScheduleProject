package mvc.Commands;

import mvc.Command;
import mvc.View;

public class ViewListCathedraCommand implements Command{
    @Override
    public void activate(){
        View.request("CATHEDRA_LIST");
    }

    @Override
    public String getTitle(){
        return "CMD_CATHEDRA_LIST";
    }
}
