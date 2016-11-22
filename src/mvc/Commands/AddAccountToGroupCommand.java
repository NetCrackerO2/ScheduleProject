package mvc.Commands;


import account.Account;
import account.AccountManager;
import group.GroupsManager;
import mvc.Command;
import mvc.Controller;
import mvc.View;


public class AddAccountToGroupCommand implements Command {

    @Override
    public void activate() {
        // Считываем группу
        View.writeReadGroupNumber();
        int groupNumber = Controller.readInt();
        //TODO: if (!GroupsManager.getInstance().isExist(groupNumber)) {
        if (!GroupsManager.getInstance().getAllGroups().stream().anyMatch(group -> group.getNumber() == groupNumber)) {
            View.writeElementDoesNotExists();
            return;
        }

        // Считываем аккаунт
        View.writeReadAccountFIO();
        String accountFIO = Controller.readString();
        if (!AccountManager.getInstance().isExist(accountFIO)) {
            View.writeElementDoesNotExists();
            return;
        }
        Account needAccount = AccountManager.getInstance().getAccount(accountFIO);

        //TODO: убрать, когда реформируем систему вывода строк
        if (needAccount.getGroup() != null) {
            View.writeError("Данный аккаунт уже привязан к группе. Удалите привязку для проведения данной операции.");
            return;
        }

        needAccount.setGroup(GroupsManager.getInstance().getGroup(groupNumber));
        View.writeOperationCompletedSuccessfully();
    }

    @Override
    public String getTitle() {
        return "Добавить аккаунт в группу.";
    }
}
