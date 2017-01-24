package connection;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Обеспечивает функционал серверного соединения:
 * Соединение с множеством клиентов;
 * Приём сообщений от клиентов;
 * Отправка сообщений клиентам.
 */
public class ServerAssistant implements ConnectionAssistant {
    private boolean isRun;
    private ConnectionManager connectionManager;
    private ServerSocket serverSocket;
    private Thread serverThread;
    //=============


    public ServerAssistant() {
        isRun = false;
        connectionManager = new ConnectionManager();

        serverThread = new Thread(() -> {
            while (isRun) {
                log("SERVER_WAIT_CLIENT");

                try {
                    Socket newClient = serverSocket.accept();
                    connectionManager.addNewConnection(newClient);
                    log("SERVER_CLIENT_CONNECTED");
                } catch (IOException e) {
                    log("SERVER_CLIENT_CONNECTION_ERROR");
                }
            }
        });
    }

    @Override
    public void initialize() throws IOException {
        serverSocket = new ServerSocket(4444);

        isRun = true;
        serverThread.start();
    }

    @Override
    public void stop() {
        isRun = false;

        try {
            serverSocket.close();
        } catch (IOException e) {
            log("SERVER_CLOSING_ERROR");
        }

        connectionManager.closeAllConnections();
    }

    @Override
    public void sendMessage(Message message) {
        connectionManager.sendMessage(message);
    }
    
    public void sendMessageAll(Message message) {
        connectionManager.sendMessageAll(message);
    }

    @Override
    public Message getNextMessage() {
        return connectionManager.getNextMessage();
    }
}
