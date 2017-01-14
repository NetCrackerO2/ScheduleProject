package connection;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Класс, содержащий информацию о пришедшем сообщении.
 * Не позволяет менять эту информацию.
 */
public class Message {
    private int connectionIndex;
    private String text;
    private JSONObject jsonObject;

    /**
     * @param connectionIndex Индекс соединения, по которому пришло / будет отправляться сообщение
     * @param text            Текст сообщения
     */
    public Message(int connectionIndex, String text) {
        this.connectionIndex = connectionIndex;
        this.text = text;

        try {
            jsonObject = (JSONObject) (new JSONParser().parse(text));
        } catch (ParseException e) {
            throw new RuntimeException("Ааааа сообщение некорректно!!!" + "\n" + connectionIndex + "\n" + text);
        }
    }


    public int getConnectionIndex() {
        return connectionIndex;
    }

    //TODO: проверка существования ключа?
    public Object getValue(String key) {
        return jsonObject.get(key);
    }

    public Object[] getValues(String key) {
        JSONArray ja = (JSONArray) jsonObject.get(key);
        return ja.toArray();
    }

    public String getText() {
        return text;
    }

    /**
     * Проверяет наличие ошибки выполнения запроса.
     */
    public boolean isError() {
        return jsonObject.containsKey("err");
    }


    static boolean isCorrectJSON(String text) {
        try {
            new JSONParser().parse(text);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
