package mvc.Commands;

import org.json.simple.JSONObject;

import connection.Message;
import connection.MessageBuilder;
import group.Group;
import group.GroupImpl;
import group.GroupManager;
import mvc.Command;
import mvc.Controller;

public class GroupAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Group data = GroupImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (GroupManager.getInstance()) {
            Group inserted = GroupManager.getInstance().createObject();
            try {
                inserted.setNumber(data.getNumber());
                inserted.setCathedraIndex(data.getCathedraIndex());
                inserted.setProfessionCode(data.getProfessionCode());
                inserted.setReceiptYear(data.getReceiptYear());
                index = inserted.getIndex();
            } catch (Exception e) {
                GroupManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new GroupChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "GROUP_ADD";
    }
}
