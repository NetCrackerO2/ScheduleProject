package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class CathedraEditCommand implements Command {
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
        return "CATHEDRA_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}
