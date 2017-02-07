package connection;


import mvc.Localization;

import java.io.IOException;

public interface ConnectionAssistant {
    void initialize() throws IOException;

    void stop();

    //TODO: может проверять, что connectionIndex в сообщении == 0?
    //т.к. соединение только с сервером (его индекс = 0)
    void sendMessage(Message message);

    Message getNextMessage() throws InterruptedException;

    default void log(String text) {
        System.out.println(Localization.getInstance().getString(text));
    }
}
