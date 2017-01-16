package mvc.Commands;


import account.AccountManager;
import connection.ClientAssistant;
import connection.MessageBuilder;
import mvc.Command;
import mvc.Localization;

import java.io.IOException;
import java.util.ArrayList;


public class CreateClientCommand implements Command {
    @Override
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
        messageBuilder.put("type", "ACCOUNT_POST_TEST");
        messageBuilder.put("data", new ArrayList<>(AccountManager.getInstance().getAllObjects()));

        while (true) {
            clientAssistant.sendMessage(messageBuilder.toMessage());

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @Override
    public String getTitle() {
        return "CreateClient";
    }
}
