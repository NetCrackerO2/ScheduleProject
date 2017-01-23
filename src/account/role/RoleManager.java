package account.role;


import manager.GenericEntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//TODO: реорганизовать класс для использования индекса аккаунта вместо ссылки на аккаунт.
public class RoleManager extends GenericEntityManager<Role> {
    private static volatile RoleManager instance;
    private Map<Integer, List<Role>> map = new HashMap<>();

    private RoleManager() {
        super();
    }

    public static RoleManager getInstance() {
        if (instance == null)
            synchronized(RoleManager.class){
                if (instance == null)
                    instance = new RoleManager();
            }
        return instance;
    }

    @Override
    protected Role newObject(int roleIndex) {
        return new RoleImpl(roleIndex);
    }

    /**
     * Проверка существования роли в некотором списке ролей
     *
     * @param roleIndex Индекс искомой роли
     * @param list      Список, в котором ищестя роль
     * @return true - роль найдена, иначе false
     */
    private static boolean isExistInList(int roleIndex, List<Role> list) {
        return list.stream().anyMatch(currentRole -> currentRole.getIndex() == roleIndex);
    }

    /**
     * Получение списка ролей аккаунта. Если его нет, то создатся новый
     *
     * @param accountIndex Индекс аккаунта, для которого ищется список ролей
     * @return Список ролей указанного аккаунта
     */
    private List<Role> getOrCreateRoleList(int accountIndex) {
        List<Role> list = map.get(accountIndex);

        if (list == null) {
            map.put(accountIndex, list = new ArrayList<>());
        }

        return list;
    }

    /**
     * Связывание аккаунта и роли
     *
     * @param accountIndex Индекс аккаунта, к которому будет привязана роль
     * @param role         Объект роли, который будет привязан к аккаунту
     */
    public void addRole(int accountIndex, Role role) {
        // TODO: название метода необходимо изменить на более отражающее его назначение
        List<Role> list = getOrCreateRoleList(accountIndex);

        if (isExistInList(role.getIndex(), list))
            // TODO: исключение?..
            return;

        list.add(role);

        map.put(accountIndex, list);
    }

    /**
     * Получение ролей указанного аккаунта
     *
     * @param accountIndex Индекс аккаунта, роли которого необходимо получить
     * @return Список ролей указанного аккаунта
     */
    public List<Role> getRoles(int accountIndex) {
        return new ArrayList<>(getOrCreateRoleList(accountIndex));
    }

    /**
     * Проверка на принадлежность аккаунта конкретной роли
     *
     * @param accountIndex Индекс проверяемого аккаунта
     * @param roleIndex    Индекс роли, принадлежность которой проверяется
     * @return true - аккаунт является обладателем указанной роли, иначе false
     */
    public boolean hasRole(int accountIndex, int roleIndex) {
        // TODO: выбрасывать ли исключение при несуществовании указанной роли?
        // TODO: принимать имя роли или её объект?
        // TODO: нужно нормальное имя
        return isExistInList(roleIndex, getRoles(accountIndex));
    }

    /**
     * Проверка принадлежности права аккаунту
     *
     * @param accountIndex Индекс аккаунта, принадлежность которому проверяется
     * @param permission   Право, принадлежность которого проверяется
     * @return true - право принадлежит аккаунту, иначе false
     */
    public boolean hasPermission(int accountIndex, Permission permission) {
        return getRoles(accountIndex).stream().anyMatch(role -> role.hasPermission(permission));
    }
}
