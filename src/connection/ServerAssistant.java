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
                //TODO: лог
                System.out.println("СЕРВЕР: Ожидаем подключения клиента... ");
                try {
                    Socket newClient = serverSocket.accept();
                    connectionManager.addNewConnection(newClient);
                    //TODO: лог
                    System.out.println("СЕРВЕР: Клиент подключен.");
                } catch (IOException e) {
                    //TODO: лог
                    System.out.println("СЕРВЕР: неудачная попытка соединения с клиентом.");
                }
            }
        });
    }

    @Override
    public void initialize() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("СЕРВЕР: Не удалось подключиться к порту 4444. Инициализация сервера невозможна.");
            System.exit(1);
        }

        isRun = true;
        serverThread.start();
    }

    @Override
    public void stop() {
        isRun = false;

        try {
            serverSocket.close();
        } catch (IOException e) {
            //TODO: уточнить, когда такое может произойти.
            System.out.println("СЕРВЕР: Ошибка закрытия серверного сокета...");
        }

        connectionManager.closeAllConnections();
    }

    @Override
    public void sendMessage(Message message) {
        connectionManager.sendMessage(message);
    }

    @Override
    public Message getNextMessage() {
        return connectionManager.getNextMessage();
    }
}
