package mvc;

import account.role.Permission;
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
    String getType();

    Permission[] getRequiredPermissions();
}
