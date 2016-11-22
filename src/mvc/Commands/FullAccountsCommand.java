package mvc.Commands;


import mvc.Command;
import mvc.Controller;


public class FullAccountsCommand implements Command{

    @Override
    public void activate() {
        Controller controller = new Controller();
        controller.addCommand(new ViewListAccountsCommand());
        controller.addCommand(new AddNewAccountCommand());
        controller.addCommand(new AddAccountToGroupCommand());
        controller.addCommand(new RemoveAccountFromGroupCommand());
        controller.start();
    }

    @Override
    public String getTitle() {
        return "- Работа с аккаунтами.";
    }
}
