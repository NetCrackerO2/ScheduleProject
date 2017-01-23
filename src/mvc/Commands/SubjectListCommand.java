package mvc.Commands;


import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import subject.SubjectManager;

import java.util.ArrayList;


public class SubjectListCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        synchronized(SubjectManager.getInstance()){
            messageBuilder.put("data", new ArrayList<>(SubjectManager.getInstance().getAllObjects()));
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "SUBJECT_LIST";
    }
}
