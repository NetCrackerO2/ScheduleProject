package mvc.Commands;


import account.AccountManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;


public class AddNewAccountCommand implements Command {

    @Override
    public void activate() {
        View.writeReadAccountFIO();
        String accountName = Controller.readString();

        if(AccountManager.getInstance().isExist(accountName))
            View.writeElementAlreadyExists();
        else
            AccountManager.getInstance().getNewAccount(accountName);

        View.writeOperationCompletedSuccessfully();
    }

    @Override
    public String getTitle() {
        return "Добавить новый аккаунт";
    }
}
