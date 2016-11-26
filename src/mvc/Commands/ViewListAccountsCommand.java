package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListAccountsCommand implements Command {
    @Override
    public void activate() {
        View.request("ACCOUNT_LIST");
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_LIST";
    }
}
