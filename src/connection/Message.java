package connection;


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
        jsonObject = null;
    }


    /**
     * Проверяет наличие ошибки выполнения запроса.
     */
    public boolean isError() {
        return jsonObject.containsKey("err");
    }

    //TODO: проверка существования ключа?
    public Object getValue(String key) {
        return jsonObject.get(key);
    }

    public int getConnectionIndex() {
        return connectionIndex;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        //TODO: Плохо, метод проврерки ещё и поле изменяет, придумать, как исправить.
        try {
            jsonObject = (JSONObject) (new JSONParser().parse(text));
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
