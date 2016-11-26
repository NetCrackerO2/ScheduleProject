package mvc.Commands;


import account.Account;
import account.AccountManager;
import group.GroupManager;
import mvc.Command;
import mvc.Controller;


public class AddAccountToGroupCommand implements Command {

    @Override
    public void activate() {
        int groupNumber = Controller.getIntResponse("GROUP");
        if (!GroupManager.getInstance().isExist(groupNumber)) {
            throw new ElementNotExistsException();
        }

        String accountFIO = Controller.getStringResponse("ACCOUNT");
        if (!AccountManager.getInstance().isExist(accountFIO)) {
            throw new ElementNotExistsException();
        }
        Account needAccount = AccountManager.getInstance().getAccount(accountFIO);

        if (needAccount.getGroup() != null) {
            throw new RuntimeException("ERR_ACCOUNT_ALREADY_BOUND_TO_GROUP");
        }

        needAccount.setGroup(GroupManager.getInstance().getGroup(groupNumber));
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_BIND_TO_GROUP";
    }
}
