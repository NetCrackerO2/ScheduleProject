package mvc.Commands;

import account.role.RoleManager;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int) (long) (Long) message.getValue("index");
        synchronized (RoleManager.getInstance()) {
            RoleManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new GroupChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_REMOVE";
    }
}
