package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import account.AccountManager;

public class AccountRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int)(Integer)message.getValue("index");
        synchronized (AccountManager.getInstance()) {
            AccountManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new AccountChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ACCOUNT_REMOVE";
    }
}
