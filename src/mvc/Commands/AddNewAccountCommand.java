package mvc.Commands;


import account.AccountManager;
import mvc.Command;
import mvc.Controller;


public class AddNewAccountCommand implements Command {

    @Override
    public void activate() {
        String accountName = Controller.getStringResponse("NEW_ACCOUNT");

        if (AccountManager.getInstance().isExist(accountName))
            throw new ElementAlreadyExistsException();

        AccountManager.getInstance().getNewAccount(accountName);
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_NEW";
    }
}
