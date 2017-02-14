package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import subject.Subject;
import subject.SubjectManager;
import subject.UnregistredSubject;

public class SubjectEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Subject data = new UnregistredSubject((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (SubjectManager.getInstance()) {
            Subject modified = SubjectManager.getInstance().getObject(index);
            String backup_name = modified.getName();
            int backup_cathedra = modified.getCathedraIndex();
            try {
                modified.setName(data.getName());
                modified.setCathedraIndex(data.getCathedraIndex());
            } catch (Exception e) {
                modified.setName(backup_name);
                modified.setCathedraIndex(backup_cathedra);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new SubjectChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "SUBJECT_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.EditSubject };
    }
}
