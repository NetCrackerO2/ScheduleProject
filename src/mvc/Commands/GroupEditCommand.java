package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;


public class GroupEditCommand implements Command {
    @Override
    public void activate(Message message) {
        if (message.isError()) {
            // Выводим сообщение об ошибке редактирования факультета
            System.out.println(message.getValue("err").toString());
            return;
        }

        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType() {
        return "GROUP_EDIT";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[0];
    }
}