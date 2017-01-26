package mvc;

import connection.Message;
import connection.MessageBuilder;
import connection.ServerAssistant;
import mvc.Commands.NoPermissionsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import account.role.Permission;
import account.role.Role;
import account.role.RoleManager;

public class Controller {
    private static Controller controller;
    private List<Command> commandsList;
    private ServerAssistant connectionAssistant;
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

    public ServerAssistant getConnectionAssistant() {
        return connectionAssistant;
    }

    public void setConnectionAssistant(ServerAssistant connectionAssistant) {
        this.connectionAssistant = connectionAssistant;
    }

    public void start() {
        isStop = false;
        Role currentRole;
        synchronized (RoleManager.getInstance()) {
            currentRole = RoleManager.getInstance().getObject(3);
        }

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
                synchronized (RoleManager.getInstance()) {
                    for (Permission perm : needCommand.getRequiredPermissions())
                        if (!currentRole.hasPermission(perm))
                            throw new NoPermissionsException();
                }
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
