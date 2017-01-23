package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

public class FacultyRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int)(Integer) message.getValue("index");
        synchronized (FacultyManager.getInstance()) {
            FacultyManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new FacultyChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "FACULTY_REMOVE";
    }
}
