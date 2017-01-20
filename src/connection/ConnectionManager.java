package connection;


import mvc.Localization;

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
    private final List<Connection> connections;
    private final Queue<Message> messages;
    private int nextIndex;
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
        connection.start();
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
        Connection needConnection = null;
        synchronized (connections) {
            for (Connection connection : connections) {
                if (connection.connectionIndex != message.getConnectionIndex())
                    continue;

                needConnection = connection;
                break;
            }
        }

        if (needConnection == null) {
            log(message.getConnectionIndex(), "CONN_NOT_EXIST");
            return;
        }

        needConnection.sendText(message.toString());
    }

    /**
     * Отправляет сообщение по всем установленным соединениям.
     * Номер соединения в сообщении игнорируется.
     */
    void sendMessageAll(Message message) {
        synchronized (connections) {
            for (Connection connection : connections) {
                connection.sendText(message.toString());
            }
        }
    }

    /**
     * Вызывается АВТОМАТИЧЕСКИ, ТОЛЬКО из класса Connection при его закрытии.
     */
    private void removeConnection(Connection connection) {
        synchronized (connections) {
            connections.remove(connection);
        }
    }

    /**
     * Сюда стекаются сообщения ото всех соединений.
     * Здесь принимается решение о том, что делать с пришедшим сообщением.
     * Вызывается из метода run() классов Connection.
     *
     * @param message Поступившее сообщение.
     */
    private void processingMessage(Message message) {
        synchronized (messages) {
            messages.add(message);
        }
        newMessage.set();
    }

    private void log(int connectionIndex, String text) {
        Localization localization = Localization.getInstance();

        System.out.println(localization.getString("CONN")
                + " №"
                + connectionIndex
                + ": "
                + localization.getString(text)
        );
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
        }

        public void run() {
            isRun = true;
            String text;

            while (isRun) {
                try {
                    text = in.readUTF();
                } catch (IOException e) {
                    log("CONN_CLOSED");
                    isRun = false;
                    continue;
                }

                if (Message.isCorrectJSON(text))
                    connectionManager.processingMessage(new Message(connectionIndex, text));
                else {
                    log("CONN_INCORRECT_MESSAGE\n" + text);
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
                log("CONN_CLOSING_ERROR");
            }
        }

        private void sendText(String text) {
            try {
                out.writeUTF(text);
            } catch (IOException e) {
                log("CONN_SEND_ERROR");
            }
        }

        private void log(String text) {
            connectionManager.log(connectionIndex, text);
        }
    }
}



