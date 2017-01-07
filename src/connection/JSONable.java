package connection;


public interface JSONable {
    /**
     * Создаёт объект своего типа, основываясь на данных из сообщения
     * Самостоятельно проверяет возможности такой операции
     *
     * @param message сообщение типа XXX_ADD
     * @return
     */
    JSONable createFromMessage(Message message);


    /**
     * Возвращает строковое представление объекта для включения его в сообщение.
     * (для XXX_LIST)
     *
     * @return
     */
    String convertToString();


    /**
     * Обновляет объект, основываясь на данных из сообщения
     *
     * @param message message сообщение типа XXX_EDIT
     */
    void updateFromMessage(Message message);


    /**
     * Удаляет объект, основываясь на данных из сообщения
     *
     * @param message message сообщение типа XXX_REMOVE
     */
    void removeFromMessage(Message message);
}
