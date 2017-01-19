package mvc;


import connection.Message;

public interface Command {
    /**
     * Метод, вызываемый при активации команды
     */
    void activate(Message message);

    /**
     * Метод, вызываемый при выводе команды на экран
     *
     * @return Название команды
     */
    String getTitle();
}
