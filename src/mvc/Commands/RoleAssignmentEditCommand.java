package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import account.role.RoleAssignment;
import account.role.RoleAssignmentManager;
import account.role.UnregistredRoleAssignment;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleAssignmentEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        RoleAssignment data = new UnregistredRoleAssignment((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (RoleAssignmentManager.getInstance()) {
            RoleAssignment modified = RoleAssignmentManager.getInstance().getObject(index);
            int backup_account = modified.getAccountIndex();
            int backup_role = modified.getRoleIndex();
            try {
                modified.setAccountIndex(data.getAccountIndex());
                modified.setRoleIndex(data.getRoleIndex());
            } catch (Exception e) {
                modified.setAccountIndex(backup_account);
                modified.setRoleIndex(backup_role);
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new RoleAssignmentChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_ASSIGNMENT_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
