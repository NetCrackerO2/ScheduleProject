package account.role;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Установка нового имени роли
     *
     * @param name
     *            Новое имя роли
     */
    @Override
    public void setName(String name) {
        if (RoleManager.getInstance().getAllObjects().stream().anyMatch(role -> Objects.equals(role.getName(), name)))
            throw new IllegalArgumentException("Роль с таким именем уже существует.");

        this.name = name;
    }

    /**
     * Добавление перечня прав в роль
     *
     * @param permissions
     *            Перечень добавляемых прав
     */
    @Override
    public void addPermissions(Permission... permissions) {
        for (Permission permission : permissions)
            if (!permissionList.contains(permission))
                permissionList.add(permission);
        // TODO: надо ли кидать исключение при попытке повторного добавления
        // разрешения?
    }

    /**
     * Установление перечня прав
     *
     * @param permissions
     *            Перечень прав
     */
    @Override
    public void setPermissions(Permission... permissions) {
        permissionList.clear();
        addPermissions(permissions);
    }

    /**
     * Проверка принадлежности определённого права данной роли
     *
     * @param permission
     *            Право, принадлежность которого проверяется
     * @return true - если принадлежит, иначе false
     */
    @Override
    public boolean hasPermission(Permission permission) {
        // TODO: нужно более адекватное имя
        return permissionList.stream().anyMatch(currentPermission -> currentPermission == permission);
    }

    /**
     * Получение списка всех прав роли
     *
     * @return Список всех прав роли
     */
    @Override
    public List<Permission> getPermissions() {
        return new ArrayList<>(permissionList);
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", index);
        jsonObject.put("name", name);

        JSONArray jsonArray = new JSONArray();
        for (Permission permission : permissionList)
            jsonArray.add(permission);

        jsonObject.put("permissionList", jsonArray);

        return jsonObject;
    }
}
