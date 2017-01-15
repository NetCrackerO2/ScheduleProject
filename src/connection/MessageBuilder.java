package connection;


import manager.Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;


public class MessageBuilder {
    private int connectionIndex;
    private JSONObject jsonObject;


    public MessageBuilder() {
        initialize();
    }

    public void initialize() {
        connectionIndex = -1;
        jsonObject = new JSONObject();
    }

    public void setConnectionIndex(int connectionIndex) {
        if (connectionIndex < 0)
            throw new IllegalArgumentException("Некорректный индекс соединения.\nconnectionIndex: " + connectionIndex);

        this.connectionIndex = connectionIndex;
    }

    private boolean isFreeKey(String key) {
        return !jsonObject.containsKey(key);
    }

    public void put(String key, Object value) {
        if (!isFreeKey(key))
            throw new IllegalArgumentException("Такой ключ уже существует.\nKey: " + key);

        jsonObject.put(key, value);
    }

    public void put(String key, List<Entity> values) {
        if (!isFreeKey(key))
            throw new IllegalArgumentException("Такой ключ уже существует.\nKey: " + key);

        JSONArray jsonArray = new JSONArray();
        for (Entity entity : values)
            jsonArray.add(entity.getJSONObject());

        jsonObject.put(key, jsonArray);
    }

    public Message toMessage() {
        if (connectionIndex < 0)
            throw new IllegalStateException("Не задан индекс соединения.");

        return new Message(connectionIndex, jsonObject.toString());
    }
}
