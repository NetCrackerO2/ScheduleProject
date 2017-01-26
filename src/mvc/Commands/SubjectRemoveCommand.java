package mvc.Commands;

import account.role.Permission;
import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import subject.SubjectManager;

public class SubjectRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int) (long) (Long) message.getValue("index");
        synchronized (SubjectManager.getInstance()) {
            SubjectManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new SubjectChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "SUBJECT_REMOVE";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] { Permission.AddOrRemoveSubject };
    }
}
