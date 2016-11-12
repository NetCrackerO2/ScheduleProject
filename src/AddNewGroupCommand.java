import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.FacultyImpl;
import group.GroupsManager;

import java.util.Scanner;


public class AddNewGroupCommand extends Command {

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

        FacultyImpl faculty = new FacultyImpl(13);
        Cathedra cathedra = CathedraManager.getInstance().addNewCathedra(faculty, "test");

        try {
            GroupsManager.getInstance().getNewGroup(
                    faculty,
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
