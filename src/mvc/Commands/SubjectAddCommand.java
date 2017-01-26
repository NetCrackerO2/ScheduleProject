package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import subject.Subject;
import subject.SubjectImpl;
import subject.SubjectManager;

public class SubjectAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Subject data = SubjectImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (SubjectManager.getInstance()) {
            Subject inserted = SubjectManager.getInstance().createObject();
            try {
                inserted.setName(data.getName());
                inserted.setCathedraIndex(data.getCathedraIndex());
                index = inserted.getIndex();
            } catch (Exception e) {
                SubjectManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new SubjectChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "SUBJECT_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.AddOrRemoveSubject };
    }
}
