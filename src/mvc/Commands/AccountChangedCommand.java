package mvc.Commands;

import account.role.Permission;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class AccountChangedCommand implements Command {
    int index;

    public AccountChangedCommand(int index) {
        this.index = index;
    }

    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.put("type", getType());
        messageBuilder.put("index", index);
        Controller.getController().getConnectionAssistant().sendMessageAll(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "ACCOUNT_CHANGED";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
