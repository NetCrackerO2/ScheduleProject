package mvc.Commands;


import connection.Message;
import connection.ServerAssistant;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CreateServerCommand implements Command {
    @Override
    public void activate() {
        ServerAssistant serverAssistant = new ServerAssistant();
        serverAssistant.initialize();

        while (true) {
            Message nextMessage = serverAssistant.getNextMessage();

            System.out.println("Принято сообщение:\n" + nextMessage.toString());
            System.out.println("Имена принятых аккаунтов:\n");

            JSONArray jsonArray = (JSONArray) nextMessage.getValue("data");
            for (Object object : jsonArray.toArray())
                System.out.println(((JSONObject) object).get("name"));
        }
    }

    @Override
    public String getTitle() {
        return "CreateServer";
    }
}
