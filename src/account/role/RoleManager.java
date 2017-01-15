package account.role;


import account.Account;
import manager.GenericEntityManager;

import java.util.*;

//TODO: реорганизовать класс для использования индекса аккаунта вместо ссылки на аккаунт.
public class RoleManager extends GenericEntityManager<Role> {
    private static RoleManager instance;
    private Map<Account, List<Role>> map = new HashMap<>();

    private RoleManager() {
        super();
    }

    public static RoleManager getInstance() {
        if (instance == null)
            instance = new RoleManager();
        return instance;
    }

    @Override
    protected Role newObject(int index) {
        return new RoleImpl(index);
    }

    /**
     * Проверка существования роли в некотором списке ролей
     *
     * @param name Имя искомой роли
     * @param list Список, в котором ищестя роль
     * @return true - роль найдена, иначе false
     */
    private static boolean isExistInList(String name, List<Role> list) {
        return list.stream().anyMatch(currentRole -> Objects.equals(currentRole.getName(), name));
    }

    /**
     * Получение списка ролей аккаунта. Если его нет, то создатся новый
     *
     * @param account Объект аккаунта, для которого ищется список ролей
     * @return Список ролей указанного аккаунта
     */
    private List<Role> getOrCreateRoleList(Account account) {
        List<Role> list = map.get(account);

        if (list == null) {
            map.put(account, list = new ArrayList<>());
        }

        return list;
    }

    /**
     * Связывание аккаунта и роли
     *
     * @param account Объект аккаунта, к которому будет привязана роль
     * @param role    Объект роли, который будет привязан к аккаунту
     */
    public Account addRole(Account account, Role role) {
        // TODO: название метода необходимо изменить на более отражающее его назначение
        List<Role> list = getOrCreateRoleList(account);

        if (isExistInList(role.getName(), list))
            // TODO: исключение?..
            return account;

        list.add(role);

        map.put(account, list);
        return account;
    }

    /**
     * Получение ролей указанного аккаунта
     *
     * @param account Объект аккаунта, роли которого необходимо получить
     * @return Список ролей указанного аккаунта
     */
    public List<Role> getRoles(Account account) {
        return new ArrayList<>(getOrCreateRoleList(account));
    }

    /**
     * Проверка на принадлежность аккаунта конкретной роли
     *
     * @param account Объект проверяемого аккаунта
     * @param name    Название роли, принадлежность которой проверяется
     * @return true - аккаунт является обладателем указанной роли, иначе false
     */
    public boolean hasRole(Account account, String name) {
        // TODO: выбрасывать ли исключение при несуществовании указанной роли?
        // TODO: принимать имя роли или её объект?
        // TODO: нужно нормальное имя
        return isExistInList(name, getRoles(account));
    }

    /**
     * Проверка принадлежности права аккаунту
     *
     * @param account    Объект аккаунта, принадлежность которому проверяется
     * @param permission Право, принадлежность которого проверяется
     * @return true - право принадлежит аккаунту, иначе false
     */
    public boolean hasPermission(Account account, Permission permission) {
        return getRoles(account).stream().anyMatch(role -> role.hasPermission(permission));
    }
}
