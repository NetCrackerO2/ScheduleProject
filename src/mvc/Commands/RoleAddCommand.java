package mvc.Commands;

import org.json.simple.JSONObject;

import account.role.Permission;
import account.role.Role;
import account.role.RoleManager;
import account.role.UnregistredRole;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Role data = new UnregistredRole((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (RoleManager.getInstance()) {
            Role inserted = RoleManager.getInstance().createObject();
            try {
                inserted.setName(data.getName());
                inserted.setPermissions(data.getPermissions().toArray(new Permission[0]));
                index = inserted.getIndex();
            } catch (Exception e) {
                RoleManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new RoleChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
