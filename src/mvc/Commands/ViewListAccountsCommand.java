package mvc.Commands;


import mvc.Command;
import mvc.View;


public class ViewListAccountsCommand implements Command {

    public ViewListAccountsCommand() {
    }

    @Override
    public void activate() {
        View.writeAllAccounts();
    }

    @Override
    public String getTitle() {
        return "Вывод всех аккаунтов";
    }
}
