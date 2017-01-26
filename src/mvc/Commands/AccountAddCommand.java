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

public class AccountAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Account data = AccountImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (AccountManager.getInstance()) {
            Account inserted = AccountManager.getInstance().createObject();
            try {
                inserted.setName(data.getName());
                inserted.setGroupIndex(data.getGroupIndex());
                inserted.setCathedraIndex(data.getCathedraIndex());
                index = inserted.getIndex();
            } catch (Exception e) {
                AccountManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new AccountChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ACCOUNT_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.AddOrRemoveAccount };
    }
}
