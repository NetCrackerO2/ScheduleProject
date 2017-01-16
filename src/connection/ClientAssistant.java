package connection;


import java.io.IOException;
import java.net.Socket;


/**
 * Обеспечивает функционал клиентского соединения:
 * Соединение с единственным сервером;
 * Приём сообщений от сервера;
 * Отправка сообщений серверу.
 */
public class ClientAssistant implements ConnectionAssistant {
    private ConnectionManager connectionManager;
    private String serverAddres;
    private int serverPort;
    //=============


    public ClientAssistant() {
        //TODO: реализовать нормлаьную настройку соединения.
        serverAddres = "localhost";
        serverPort = 4444;

        connectionManager = new ConnectionManager();
    }

    @Override
    public void initialize() throws IOException {
        connectionManager.addNewConnection(new Socket(serverAddres, serverPort));
        log("CLIENT_SERVER_CONNECTED");
    }

    @Override
    public void stop() {
        connectionManager.closeAllConnections();
    }

    //TODO: может проверять, что connectionIndex в сообщении == 0?
    //т.к. соединение только с сервером (его индекс = 0)
    @Override
    public void sendMessage(Message message) {
        connectionManager.sendMessage(message);
    }

    @Override
    public Message getNextMessage() {
        return connectionManager.getNextMessage();
    }
}
