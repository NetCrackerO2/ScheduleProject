package mvc.Commands;


import connection.ClientAssistant;
import connection.Message;
import connection.MessageBuilder;
import mvc.Localization;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;


public class CreateClientCommand {
    public static void main(String[] args) {
        new CreateClientCommand().activate();
    }

    public void activate() {
        ClientAssistant clientAssistant = new ClientAssistant();

        try {
            clientAssistant.initialize();
        } catch (IOException e) {
            System.out.println(Localization.getInstance().getString("CLIENT_INITIALIZATION_ERROR"));
            return;
        }

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "FACULTY_LIST");

        while (true) {
            clientAssistant.sendMessage(messageBuilder.toMessage());
            Message nextMessage = clientAssistant.getNextMessage();

            System.out.println("Список факультетов:");

            JSONArray jsonArray = (JSONArray) nextMessage.getValue("data");
            for (Object object : jsonArray.toArray())
                System.out.println(((JSONObject) object).get("number"));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public String getTitle() {
        return "CreateClient";
    }
}
