package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class RoleRemoveCommand implements Command {
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
        return "ROLE_REMOVE";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}
