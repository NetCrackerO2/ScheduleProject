package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;
import cathedra.CathedraManager;

public class CathedraRemoveCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        int index = (int)(long)(Long) message.getValue("index");
        synchronized (CathedraManager.getInstance()) {
            CathedraManager.getInstance().removeObject(index);
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new CathedraChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "CATHEDRA_REMOVE";
    }
}
