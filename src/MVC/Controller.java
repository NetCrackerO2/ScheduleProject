package MVC;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Controller {
    private List<Command> commandsList;
    private boolean isStop;

    public Controller() {
        commandsList = new ArrayList<>();
        isStop = true;
    }

    public void addCommand(Command newCommand) {
        commandsList.add(newCommand);
    }

    public void activateCommand(int number) {
        commandsList.get(number).activate();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        isStop = false;
        int currentCommand;
        String readCommand;

        while (!isStop) {
            View.writeAllCommands(commandsList);


            try {
                readCommand = scanner.nextLine();
                currentCommand = Integer.parseInt(readCommand);
            } catch (Exception e) {
                View.writeError("Некорректный ввод номера команды.");
                continue;
            }

            if (currentCommand < 0 || commandsList.size() < currentCommand) {
                View.writeError("Команды с таким номером не существует.");
                continue;
            }

            if (commandsList.size() == currentCommand) {
                isStop = true;
                continue;
            }

            activateCommand(currentCommand);
        }

        scanner.close();
    }
}
