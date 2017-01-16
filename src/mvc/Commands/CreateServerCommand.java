package mvc.Commands;


import connection.Message;
import connection.ServerAssistant;
import mvc.Command;
import mvc.Localization;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;


public class CreateServerCommand implements Command {
    @Override
    public void activate() {
        ServerAssistant serverAssistant = new ServerAssistant();

        try {
            serverAssistant.initialize();
        } catch (IOException e) {
            System.out.println(Localization.getInstance().getString("SERVER_INITIALIZATION_ERROR"));
            return;
        }

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
