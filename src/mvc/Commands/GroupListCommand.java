package mvc.Commands;


import connection.Message;
import connection.MessageBuilder;
import group.GroupManager;
import mvc.Command;
import mvc.Controller;

import java.util.ArrayList;


public class GroupListCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        synchronized(GroupManager.getInstance()){
            messageBuilder.put("data", new ArrayList<>(GroupManager.getInstance().getAllObjects()));
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "GROUP_LIST";
    }
}
