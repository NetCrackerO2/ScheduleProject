package mvc.Commands;

import org.json.simple.JSONObject;

import connection.Message;
import connection.MessageBuilder;
import faculty.Faculty;
import faculty.FacultyImpl;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

public class FacultyAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Faculty data = FacultyImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (FacultyManager.getInstance()) {
            Faculty inserted = FacultyManager.getInstance().createObject();
            try {
                inserted.setNumber(data.getNumber());
                inserted.setDeanAccountIndex(data.getDeanAccountIndex());
                index = inserted.getIndex();
            } catch (Exception e) {
                FacultyManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new FacultyChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "FACULTY_ADD";
    }
}
