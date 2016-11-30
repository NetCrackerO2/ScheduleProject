package account;


import java.util.*;


public class RoleManager {
	private static RoleManager instance;
	private List<Role> roles;
	private Map<Account, List<Role>> map;


	private RoleManager() {
		roles = new ArrayList<>();
		map = new HashMap<>();
	}

	/**
	 * Singletone instance
	 *
	 * @return new RoleManager if called before, buffered one otherwise
	 */
	public static RoleManager getInstance() {
		if (instance == null)
			instance = new RoleManager();
		return instance;
	}

	/**
	 * Проверка существования роли в некотором списке ролей
	 *
	 * @param name Имя искомой роли
	 * @param list Список, в котором ищестя роль
	 * @return true - роль найдена, иначе false
	 */
	private boolean isExistInList(String name, List<Role> list) {
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
	 * Создание новой роли
	 *
	 * @param name Имя новой роли
	 * @return Объект созданной роли
	 */
	public Role createRole(String name) {
		// TODO: заменить на более подходящий тип исключения
		if (isExist(name))
			throw new RuntimeException("Роль с таким именем уже существует.");

		Role newRole = new Role(name);
		roles.add(newRole);

		return newRole;
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
	 * Получение роли по её имени
	 *
	 * @param name Имя искомой роли
	 * @return Объект роли с указанным именем
	 */
	public Role getRole(String name) {
		for (Role current : roles)
			if (current.getName().equals(name))
				return current;

		throw new NoSuchElementException();
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

	/**
	 * Полечение списка всех существующих ролей
	 *
	 * @return Список всех существующих ролей
	 */
	public List<Role> getAllRoles() {
		return new ArrayList<>(roles);
	}

	/**
	 * Проверка существования роли с указанным именем
	 *
	 * @param name Имя искомой роли
	 * @return true - роль существует, иначе false
	 */
	public boolean isExist(String name) {
		return isExistInList(name, roles);
	}
}
