package connection;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Менеджер соединений: отвечает за работу со всеми установленными соединениями.
 */
class ConnectionManager {
    private int nextIndex;
    private final List<Connection> connections;
    private final Queue<Message> messages;
    private ManualResetEvent newMessage;
    //=============


    ConnectionManager() {
        nextIndex = 0;
        connections = new ArrayList<>();
        messages = new LinkedList<>();
        newMessage = new ManualResetEvent(false);
    }

    void addNewConnection(Socket newConnection) throws IOException {
        Connection connection = new Connection(this, nextIndex++, newConnection);
        synchronized (connections) {
            connections.add(connection);
        }
    }

    void closeAllConnections() {
        while (connections.size() > 0)
            connections.get(0).close();
    }

    /**
     * @return Возвращает самое раннее из пришедших сообщений.
     * Если очередь сообщений пуста, блокирует поток до прихода нового.
     */
    Message getNextMessage() {
        if (messages.size() == 0)
            newMessage.waitOne();
        newMessage.reset();

        synchronized (messages) {
            return messages.remove();
        }
    }

    /**
     * Отправляет сообщение по соединению с указанным индексом.
     */
    void sendMessage(Message message) {
        synchronized (connections) {
            for (Connection connection : connections) {
                if (connection.connectionIndex != message.getConnectionIndex())
                    continue;

                connection.sendText(message.getText());
                return;
            }
        }

        //TODO: лог
        System.out.println("Соединение №" + message.getConnectionIndex() + ": не существует. Отправка сообщения невозможна.");
        //TODO: Я не знаю, что тут делать. Исключение кидать? False возвращать?
    }

    /**
     * Вызывается из класса Connection при его закрытии.
     */
    private void removeConnection(Connection connection) {
        synchronized (connections) {
            connections.remove(connection);
        }
    }

    //TODO: Плохое название метода - outPoint. Придумать лучше.

    /**
     * Сюда стекаются сообщения ото всех соединений.
     * Здесь принимается решение о том, что делать с пришедшим сообщением.
     * Вызывается из метода run() классов Connection.
     *
     * @param message Поступившее сообщение.
     */
    private void outPoint(Message message) {
        synchronized (messages) {
            messages.add(message);
        }
        newMessage.set();
    }


    /**
     * Класс, отвечающий за взаимодействие (отправка и приём сообщений) с одним соединением
     */
    class Connection extends Thread {
        private ConnectionManager connectionManager;
        private boolean isRun;
        private int connectionIndex;
        private Socket connection;
        private DataInputStream in;
        private DataOutputStream out;
        //=============


        Connection(ConnectionManager connectionManager, int connectionIndex, Socket connection) throws IOException {
            this.connectionManager = connectionManager;
            this.connectionIndex = connectionIndex;
            this.connection = connection;

            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());

            isRun = true;
            this.start();
            this.interrupt();
        }

        public void run() {
            while (isRun) {
                try {
                    String text = in.readUTF();

                    if (Message.isCorrectJSON(text))
                        connectionManager.outPoint(new Message(connectionIndex, text));
                    else {
                        //TODO: лог
                        System.out.println("Cоединение №" + connectionIndex + ": принято и пропущено некорректное сообщение: " + text + ".");
                    }
                } catch (IOException e) {
                    //TODO: лог
                    System.out.println("Cоединение №" + connectionIndex + ": разорвано.");
                    isRun = false;
                }
            }

            connectionManager.removeConnection(this);
        }

        private void close() {
            try {
                in.close();
                out.close();
                connection.close();
            } catch (IOException e) {
                //TODO: лог
                System.out.println("Cоединение №" + connectionIndex + ": ошибка закрытия (:|) хехе что хотите то и делайте");
            }
        }

        private void sendText(String text) {
            try {
                out.writeUTF(text);
            } catch (IOException e) {
                //TODO: лог
                System.out.println("Cоединение №" + connectionIndex + ": отправка сообщения невозможна.");
            }
        }
    }
}



