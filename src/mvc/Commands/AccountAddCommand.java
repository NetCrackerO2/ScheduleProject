package mvc.Commands;


import account.role.Permission;
import connection.Message;
import gui.MainForm;
import mvc.Command;

public class AccountAddCommand implements Command {
    @Override
    public void activate(Message message) {
        if (message.isError()) {
            // Выводим сообщение об ошибке добавления пользователя
            System.out.println(message.getValue("err").toString());
            return;
        }

        MainForm.getMainForm().updateAllData();
    }

    @Override
    public String getType()  {
        return "ACCOUNT_ADD";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{Permission.AddOrRemoveAccount};
    }
}
