package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import connection.Message;
import connection.MessageBuilder;
import group.Group;
import group.GroupManager;
import group.UnregistredGroup;
import mvc.Command;
import mvc.Controller;

public class GroupEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Group data = new UnregistredGroup((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (GroupManager.getInstance()) {
            Group modified = GroupManager.getInstance().getObject(index);
            int backup_number = modified.getNumber();
            int backup_cathedra = modified.getCathedraIndex();
            int backup_prof = modified.getProfessionCode();
            int backup_year = modified.getReceiptYear();
            try {
                modified.setNumber(data.getNumber());
                modified.setCathedraIndex(data.getCathedraIndex());
                modified.setProfessionCode(data.getProfessionCode());
                modified.setReceiptYear(data.getReceiptYear());
            } catch (Exception e) {
                modified.setNumber(backup_number);
                modified.setCathedraIndex(backup_cathedra);
                modified.setProfessionCode(backup_prof);
                modified.setReceiptYear(backup_year);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new GroupChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "GROUP_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.EditGroup };
    }
}
