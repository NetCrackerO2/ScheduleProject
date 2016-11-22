package mvc.Commands;


import account.Account;
import account.AccountManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;


public class RemoveAccountFromGroupCommand implements Command {

    @Override
    public void activate() {
        // Считываем аккаунт
        View.writeReadAccountFIO();
        String accountFIO = Controller.readString();
        if (!AccountManager.getInstance().isExist(accountFIO)) {
            View.writeElementDoesNotExists();
            return;
        }
        Account needAccount = AccountManager.getInstance().getAccount(accountFIO);

        if (needAccount.getGroup() == null) {
            //TODO: убрать, когда реформируем систему вывода строк
            View.writeError("Данный аккаунт не имеет привязки к группе.");
            return;
        }

        needAccount.setGroup(null);
        View.writeOperationCompletedSuccessfully();
    }

    @Override
    public String getTitle() {
        return "Удалить аккаунт из группы.";
    }
}
