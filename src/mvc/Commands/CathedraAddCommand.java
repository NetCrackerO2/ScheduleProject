package mvc.Commands;

import connection.Message;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Controller;

import org.json.simple.JSONObject;

import cathedra.Cathedra;
import cathedra.CathedraImpl;
import cathedra.CathedraManager;

public class CathedraAddCommand implements Command {
    @Override
    public void activate(Message message) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(message.getConnectionIndex());
        messageBuilder.put("type", message.getValue("type"));
        Cathedra data = CathedraImpl.fromJSONObject((JSONObject) message.getValue("data"));
        int index = -1;
        synchronized (CathedraManager.getInstance()) {
            Cathedra inserted = CathedraManager.getInstance().createObject();
            try {
                inserted.setName(data.getName());
                inserted.setHeadAccountIndex(data.getHeadAccountIndex());
                inserted.setFacultyIndex(data.getFacultyIndex());
                index = inserted.getIndex();
            } catch (Exception e) {
                CathedraManager.getInstance().removeObject(inserted.getIndex());
                throw e;
            }
        }
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        new CathedraChangedCommand(index).activate(message);
    }

    @Override
    public String getType() {
        return "CATHEDRA_ADD";
    }
}
