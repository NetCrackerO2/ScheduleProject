package mvc.Commands;


import account.Account;
import account.AccountManager;
import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;


public class AddAccountToCathedraCommand implements Command {

    @Override
    public void activate() {
        String cathedraName = Controller.getStringResponse("CATHEDRA");
        if (!CathedraManager.getInstance().isExist(cathedraName)) {
            throw new ElementNotExistsException();
        }

        String accountFIO = Controller.getStringResponse("ACCOUNT");
        if (!AccountManager.getInstance().isExist(accountFIO)) {
            throw new ElementNotExistsException();
        }
        Account needAccount = AccountManager.getInstance().getAccount(accountFIO);

        if (needAccount.getCathedra() != null) {
            throw new RuntimeException("ERR_ACCOUNT_ALREADY_BOUND_TO_CATHEDRA");
        }

        needAccount.setCathedra(CathedraManager.getInstance().getCathedra(cathedraName));
    }

    @Override
    public String getTitle() {
        return "CMD_ACCOUNT_BIND_TO_CATHEDRA";
    }
}
