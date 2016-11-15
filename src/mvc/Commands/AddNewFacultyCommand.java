package mvc.Commands;

import java.util.Scanner;

import faculty.FacultyManager;
import mvc.Command;
import mvc.View;

public class AddNewFacultyCommand implements Command {

	@SuppressWarnings("resource")
	@Override
	public void activate() {
		Scanner scanner = new Scanner(System.in);
        int number = 0;

        View.writeMessage("Введите номер нового факультета: ");

        try {
            number = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            View.writeError("Некорректный ввод номера факультета.");
            return;
        }

        try {
            FacultyManager.getInstance().addNewFaculty(number);
        } catch (Exception e) {
            View.writeError(e.getMessage());
            return;
        }

        View.writeMessage("Факультет добавлен.\n");
	}

	@Override
	public String getTitle() {
        return "Добавить новый факультет";
	}

}
