package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class RoleAddCommand implements Command {
    @Override
    public void activate(Message message) {
        if (message.isError()) {
            // TODO: лог
            System.out.println(message.getValue("err").toString());
            return;
        }

        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType() {
        return "ROLE_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}
