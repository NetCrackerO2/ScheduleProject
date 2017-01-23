package mvc.Commands;


import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

import java.util.ArrayList;

import cathedra.CathedraManager;


public class CathedraListCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        synchronized(CathedraManager.getInstance()){
            messageBuilder.put("data", new ArrayList<>(CathedraManager.getInstance().getAllObjects()));
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "CATHEDRA_LIST";
    }
}
