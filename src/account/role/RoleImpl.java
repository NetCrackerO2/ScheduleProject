package account.role;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class RoleImpl implements Role {
    private int index;
    private String name;
    private List<Permission> permissionList;

    RoleImpl(int index) {
        permissionList = new ArrayList<>();
        this.index = index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    /**
     * Установка нового имени роли
     *
     * @param name Новое имя роли
     */
    public void setName(String name) {
        if (RoleManager.getInstance().getAllObjects().stream().anyMatch(role -> Objects.equals(role.getName(), name)))
            throw new IllegalArgumentException("Роль с таким именем уже существует.");

        this.name = name;
    }

    @Override
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

    @Override
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

    @Override
    /**
     * Получение списка всех прав роли
     *
     * @return Список всех прав роли
     */
    public List<Permission> getAllPermissions() {
        return new ArrayList<>(permissionList);
    }

    @Override
    public int getIndex() {
        return index;
    }
}
