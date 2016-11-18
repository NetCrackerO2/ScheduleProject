package mvc.Commands;


import account.AccountManager;
import group.Group;
import group.GroupsManager;
import mvc.Command;
import mvc.View;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class AddNewAccountCommand implements Command {

    @Override
    public void activate() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;

        View.writeMessage("Введите фио нового пользователя: ");
        String accountName = scanner.nextLine();

        Group group = null;
        View.writeMessage("Введите номер группы пользователя: ");
        number = Integer.parseInt(scanner.nextLine());
        try{
            group = GroupsManager.getInstance().getGroup(number);
        }
        catch (NoSuchElementException e){
            View.writeError("Группа с таким именем не найдена!");
            return;
        }

        try {
            AccountManager.getInstance().getNewAccount(
                    accountName,
                    group
            );
        } catch (Exception e) {
            View.writeError(e.getMessage());
            return;
        }

        View.writeMessage("Аккаунт добавлен.\n");
    }

    @Override
    public String getTitle() {
        return "Добавить новый аккаунт";
    }
}
