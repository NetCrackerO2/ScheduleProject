package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

import org.json.simple.JSONObject;

import account.Account;
import account.AccountImpl;
import account.AccountManager;
import account.role.Permission;

public class AccountEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Account data = AccountImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (AccountManager.getInstance()) {
            Account modified = AccountManager.getInstance().getObject(index);
            String backup_name = modified.getName();
            int backup_group = modified.getGroupIndex();
            int backup_cathedra = modified.getCathedraIndex();
            try {
                modified.setName(data.getName());
                modified.setGroupIndex(data.getGroupIndex());
                modified.setCathedraIndex(data.getCathedraIndex());
            } catch (Exception e) {
                modified.setName(backup_name);
                modified.setGroupIndex(backup_group);
                modified.setCathedraIndex(backup_cathedra);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new AccountChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ACCOUNT_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.EditAccount };
    }
}
