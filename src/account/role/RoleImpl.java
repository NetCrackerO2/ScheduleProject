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
     * @param name Новое имя роли
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
     * @param permissions Перечень добавляемых прав
     */
    @Override
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
    public List<Permission> getAllPermissions() {
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
    

    public static Role fromJSONObject(JSONObject jsonObject) {
        int index = (int)(Integer) jsonObject.get("index");
        String name = (String) jsonObject.get("name");
        List permissionList = Arrays.asList(((JSONArray) jsonObject.get("permissionList")).toArray());

        return new Role() {
            @Override
            public int getIndex() {
                return index;
            }

            @Override
            public JSONObject getJSONObject() {
                return jsonObject;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void setName(String name) {
                throw new RuntimeException("Immutable object");
            }

            @Override
            public void addPermissions(Permission... permissions) {
                throw new RuntimeException("Immutable object");
            }

            @Override
            public boolean hasPermission(Permission permission) {
                return permissionList.indexOf(permission)>=0;
            }

            @SuppressWarnings("unchecked")
            @Override
            public List<Permission> getAllPermissions() {
                return permissionList;
            }
        };
    }
}
