package mvc;

import connection.ConnectionAssistant;
import connection.Message;
import connection.MessageBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Controller {
    private static Controller controller;
    private List<Command> commandsList;
    private ConnectionAssistant connectionAssistant;
    private boolean isStop;

    public Controller() {
        commandsList = new ArrayList<>();
        isStop = true;
        connectionAssistant = null;
    }

    public static Controller getController() {
        return controller;
    }

    public static void setController(Controller controller) {
        Controller.controller = controller;
    }

    public void addCommand(Command newCommand) {
        commandsList.add(newCommand);
    }

    public ConnectionAssistant getConnectionAssistant() {
        return connectionAssistant;
    }

    public void setConnectionAssistant(ConnectionAssistant connectionAssistant) {
        this.connectionAssistant = connectionAssistant;
    }

    public void start() {
        isStop = false;

        while (!isStop) {
            Message nextMessage = connectionAssistant.getNextMessage();

            Command needCommand = null;
            for (Command command : commandsList) {
                if (Objects.equals(command.getType(), nextMessage.getValue("type"))) {
                    needCommand = command;
                    break;
                }
            }

            if (needCommand == null) {
                // TODO: лог
                System.out.println("Невозможно обработать поступивший запрос.\nType: " + nextMessage.getValue("type"));
                continue;
            }

            try {
                needCommand.activate(nextMessage);
            } catch (Exception e) {
                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.setConnectionIndex(nextMessage.getConnectionIndex());
                messageBuilder.put("type", nextMessage.getValue("type"));
                messageBuilder.put("err", e.getMessage());
                messageBuilder.put("err_msg", e.toString());
                getConnectionAssistant().sendMessage(messageBuilder.toMessage());
            }
        }

    }
}
