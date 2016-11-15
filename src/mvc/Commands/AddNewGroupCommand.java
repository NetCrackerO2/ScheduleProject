package mvc.Commands;


import cathedra.Cathedra;
import cathedra.CathedraManager;
import group.GroupsManager;
import mvc.Command;
import mvc.View;

import java.util.NoSuchElementException;
import java.util.Scanner;


public class AddNewGroupCommand implements Command {

    @Override
    public void activate() {
        Scanner scanner = new Scanner(System.in);
        int number = 0;

        View.writeMessage("Введите номер новой группы: ");

        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            View.writeError("Некорректный ввод номера группы.");
            return;
        }

        Cathedra cathedra;
        View.writeMessage("Введите название кафедры группы: ");
        String cathedraName = scanner.nextLine();
        try{
            cathedra = CathedraManager.getInstance().getCathedra(cathedraName);
        }
        catch (NoSuchElementException e){
            View.writeError("Кафедра с таким именем не найдена!");
            return;
        }


        try {
            GroupsManager.getInstance().getNewGroup(
                    cathedra,
                    number
            );
        } catch (Exception e) {
            View.writeError(e.getMessage());
            return;
        }

        View.writeMessage("Группа добавлена.\n");
    }

    @Override
    public String getTitle() {
        return "Добавить новую группу";
    }
}
