package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

import java.util.ArrayList;
import account.AccountManager;
import account.AccountManager.*;

public class AccountListCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        String name = (String) message.getValue("name");
        String cathedra = (String) message.getValue("cathedra");
        String group = (String) message.getValue("group");
        synchronized (AccountManager.getInstance()) {
            messageBuilder.put("data", new ArrayList<>(AccountManager.getInstance().find(new NameIs(name).and(new GroupIs(group)).and(new CathedraIs(cathedra)))));
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "ACCOUNT_LIST";
    }
}
