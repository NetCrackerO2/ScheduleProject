package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class CathedraChangedCommand implements Command {
    @Override
    public void activate(Message message) {
        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType() {
        return "CATHEDRA_CHANGED";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}
