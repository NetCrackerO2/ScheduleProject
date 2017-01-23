package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

public class SubjectChangedCommand implements Command {
    int index;

    public SubjectChangedCommand(int index) {
        this.index = index;
    }

    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", getType());
        messageBuilder.put("index", index);
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public String getType() {
        return "SUBJECT_CHANGED";
    }

}
