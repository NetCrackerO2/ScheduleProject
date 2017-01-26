package mvc.Commands;

import java.util.List;

import org.json.simple.JSONObject;

import account.role.Permission;
import account.role.Role;
import account.role.RoleImpl;
import account.role.RoleManager;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class RoleEditCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Role data = RoleImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = data.getIndex();
        synchronized (RoleManager.getInstance()) {
            Role modified = RoleManager.getInstance().getObject(index);
            String backup_name = modified.getName();
            List<Permission> backup_perms = modified.getPermissions();
            try {
                modified.setName(data.getName());
                modified.setPermissions(data.getPermissions().toArray(new Permission[0]));
            } catch (Exception e) {
                modified.setName(backup_name);
                modified.setPermissions(backup_perms.toArray(new Permission[0]));
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new RoleChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "ROLE_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
