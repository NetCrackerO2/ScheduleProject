package mvc;


public interface Command {
    /**
     * Метод, вызываемый при активации команды
     */
    void activate();

    /**
     * Метод, вызываемый при выводе команды на экран
     *
     * @return Название команды
     */
    String getTitle();
}
