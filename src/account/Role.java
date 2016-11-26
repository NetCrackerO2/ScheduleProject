/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Александр
 */
public class Role {
	private String name;
	private List<Permission> permissionList;

	protected Role(String name) {
		permissionList = new ArrayList<>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Установка нового имени роли
	 *
	 * @param name Новое имя роли
	 */
	public void setName(String name) {
		// TODO: Проверка на возможность изменения с помощью менеджера.
		// Если невозмонжо, то исключение или false?
		// Мне кажется, что если в менеджере будет метод проверки существования роли с определённым именем, то тут можно исключение.
		this.name = name;
	}

	/**
	 * Добавление перечня прав в роль
	 *
	 * @param permissions Перечень добавляемых прав
	 */
	public void addPermissions(Permission... permissions) {
		for (Permission permission : permissions)
			if (!permissionList.contains(permission))
				permissionList.add(permission);
		// TODO: надо ли кидать исключение при попытке повторного добавления разрешения?
	}

	/**
	 * Проверка принадлежности определённого права данной роли
	 *
	 * @param permission Право, принадлежность которого проверяется
	 * @return true - если принадлежит, иначе false
	 */
	public boolean hasPermission(Permission permission) {
		// TODO: нужно более адекватное имя
		return permissionList.stream().anyMatch(currentPermission -> currentPermission == permission);
	}

	/**
	 * Получение списка всех прав роли
	 *
	 * @return Список всех прав роли
	 */
	public List<Permission> getAllPermissions() {
		return new ArrayList<>(permissionList);
	}
}
