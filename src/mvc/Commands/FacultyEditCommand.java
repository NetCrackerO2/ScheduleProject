package mvc.Commands;

import org.json.simple.JSONObject;

import connection.Message;
import connection.MessageBuilder;
import faculty.Faculty;
import faculty.FacultyImpl;
import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

public class FacultyEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Faculty data = FacultyImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (FacultyManager.getInstance()) {
            Faculty modified = FacultyManager.getInstance().getObject(index);
            int backup_number = modified.getNumber();
            int backup_dean = modified.getDeanAccountIndex();
            try {
                modified.setNumber(data.getNumber());
                modified.setDeanAccountIndex(data.getDeanAccountIndex());
            } catch (Exception e) {
                modified.setNumber(backup_number);
                modified.setDeanAccountIndex(backup_dean);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new FacultyChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "FACULTY_EDIT";
    }
}
