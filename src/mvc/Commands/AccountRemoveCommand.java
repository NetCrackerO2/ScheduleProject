package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;

public class AccountRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        if (message.isError()) {
            // Выводим сообщение об ошибке удаления пользователя
            System.out.println(message.getValue("err").toString());
            return;
        }

        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType()  {
        return "ACCOUNT_REMOVE";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{Permission.AddOrRemoveAccount};
    }
}
