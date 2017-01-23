package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import group.GroupManager;
import mvc.Command;
import mvc.Controller;

public class GroupRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int)(long)(Long) message.getValue("index");
        synchronized (GroupManager.getInstance()) {
            GroupManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new GroupChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "GROUP_REMOVE";
    }
}
