package connection;


public interface ConnectionAssistant {
    void initialize();

    void stop();

    //TODO: может проверять, что connectionIndex в сообщении == 0?
    //т.к. соединение только с сервером (его индекс = 0)
    void sendMessage(Message message);

    Message getNextMessage();
}
