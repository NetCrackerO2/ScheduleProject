package mvc.Commands;


import account.Account;
import account.AccountManager;
import mvc.Command;
import mvc.Controller;


public class RemoveAccountFromCathedraCommand implements Command{

    @Override
    public void activate() {
        String accountFIO = Controller.getStringResponse("ACCOUNT");
        if (!AccountManager.getInstance().isExist(accountFIO))
            throw new ElementNotExistsException();

        Account needAccount = AccountManager.getInstance().getAccount(accountFIO);
        if (needAccount.getCathedra() == null)
            throw new RuntimeException("ERR_ACCOUNT_NOT_BOUND_TO_CATHEDRA");

        needAccount.setCathedra(null);
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_UNBIND_TO_CATHEDRA";
    }
}
