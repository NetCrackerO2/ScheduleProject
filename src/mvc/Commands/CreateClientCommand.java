package mvc.Commands;


import connection.ClientAssistant;
import connection.Message;
import mvc.Command;


public class CreateClientCommand implements Command {
    @Override
    public void activate() {
        ClientAssistant clientAssistant = new ClientAssistant();
        clientAssistant.initialize();

        while (true) {
            clientAssistant.sendMessage(new Message(0, "{\"type\": \"toster\"}"));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //Убейтесь исключения
            }
        }
    }

    @Override
    public String getTitle() {
        return "CreateClient";
    }
}
