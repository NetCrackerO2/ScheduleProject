package mvc.Commands;


import account.AccountManager;
import mvc.Command;
import mvc.Controller;


public class RemoveAccountCommand implements Command {

    @Override
    public void activate() {
        String accountName = Controller.getStringResponse("ACCOUNT");
        if (!AccountManager.getInstance().isExist(accountName))
            throw new ElementNotExistsException();

        AccountManager.getInstance().removeAccount(accountName);
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_REMOVE";
    }
}
