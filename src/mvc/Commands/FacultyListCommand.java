package mvc.Commands;


import connection.Message;
import connection.MessageBuilder;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

import java.util.ArrayList;


public class FacultyListCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        messageBuilder.put("data", new ArrayList<>(FacultyManager.getInstance().getAllObjects()));

        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getTitle() {
        return "FACULTY_LIST";
    }
}
