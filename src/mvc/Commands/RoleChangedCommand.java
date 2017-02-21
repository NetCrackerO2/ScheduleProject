package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class RoleChangedCommand implements Command {
    @Override
    public void activate(Message message) {
        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType() {
        return "ROLE_CHANGED";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}
