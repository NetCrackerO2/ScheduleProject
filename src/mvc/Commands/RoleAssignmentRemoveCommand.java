package mvc.Commands;

import account.role.Permission;
import account.role.RoleAssignmentManager;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleAssignmentRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int) (long) (Long) message.getValue("index");
        synchronized (RoleAssignmentManager.getInstance()) {
            RoleAssignmentManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new RoleAssignmentChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_ASSIGNMENT_REMOVE";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
