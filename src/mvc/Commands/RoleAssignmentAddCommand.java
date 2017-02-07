package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import account.role.RoleAssignment;
import account.role.RoleAssignmentImpl;
import account.role.RoleAssignmentManager;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleAssignmentAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        RoleAssignment data = RoleAssignmentImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (RoleAssignmentManager.getInstance()) {
            RoleAssignment inserted = RoleAssignmentManager.getInstance().createObject();
            try {
                inserted.setAccountIndex(data.getAccountIndex());
                inserted.setRoleIndex(data.getRoleIndex());
                index = inserted.getIndex();
            } catch (Exception e) {
                RoleAssignmentManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new RoleAssignmentChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_ASSIGNMENT_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
