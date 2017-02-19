package mvc.Commands;


import account.Account;
import account.UnregistredAccount;
import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AccountListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<Account> accountList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            accountList.add(new UnregistredAccount((JSONObject) object));
        MainForm.getMainForm().setAccountList(accountList);
    }

    @Override
    public String getType() {
        return "ACCOUNT_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{};
    }
}