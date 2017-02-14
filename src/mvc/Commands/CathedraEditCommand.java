package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

import org.json.simple.JSONObject;

import account.role.Permission;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import cathedra.UnregistredCathedra;

public class CathedraEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Cathedra data = new UnregistredCathedra((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (CathedraManager.getInstance()) {
            Cathedra modified = CathedraManager.getInstance().getObject(index);
            String backup_name = modified.getName();
            int backup_head = modified.getHeadAccountIndex();
            int backup_faculty = modified.getFacultyIndex();
            try {
                modified.setName(data.getName());
                modified.setHeadAccountIndex(data.getHeadAccountIndex());
                modified.setFacultyIndex(data.getFacultyIndex());
            } catch (Exception e) {
                modified.setName(backup_name);
                modified.setHeadAccountIndex(backup_head);
                modified.setFacultyIndex(backup_faculty);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new CathedraChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "CATHEDRA_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.EditCathedra };
    }
}
