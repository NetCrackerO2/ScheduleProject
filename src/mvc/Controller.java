package mvc;


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
        isStop = false;

        while (!isStop) {
            View.writeAllCommands(commandsList);
            int currentCommand = getIntResponse("CMD");

            if (currentCommand < 0 || commandsList.size() < currentCommand) {
                View.setStatus(new RuntimeException("ERR_INVALID_NUMBER"));
                continue;
            }

            if (commandsList.size() == currentCommand) {
                isStop = true;
                continue;
            }

            try {
                activateCommand(currentCommand);
                View.setStatus(null);
            } catch (Exception e) {
                View.setStatus(e);
            }
        }

    }

    public static int getIntResponse(String request) {
        View.request(request);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int number;

        try {
            number = scanner.nextInt();
        } catch (Exception e) {
            throw new RuntimeException("ERR_INVALID_NUMBER");
        }

        return number;
    }
    
    public static String getStringResponse(String request) {
        View.request(request);
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
