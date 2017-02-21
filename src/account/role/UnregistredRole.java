package account.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UnregistredRole implements Role {
    private int index = -1;
    private String name;
    private List<Permission> permissionList = new ArrayList<>();

    public UnregistredRole(int index) {
        this.index = index;
    }

    public UnregistredRole(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        name = (String) jsonObject.get("name");
        List<Permission> perms = new ArrayList<>();
        ((JSONArray) jsonObject.get("permissionList")).stream().forEach(x -> {
            perms.add(Permission.valueOf((String) x));
        });
        setPermissions(perms.toArray(new Permission[] {}));
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
            jsonArray.add(permission.name());

        jsonObject.put("permissionList", jsonArray);

        return jsonObject;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addPermissions(Permission... permissions) {
        for (Permission permission : permissions)
            if (!permissionList.contains(permission))
                permissionList.add(permission);
    }

    @Override
    public void setPermissions(Permission... permissions) {
        permissionList.clear();
        addPermissions(permissions);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return permissionList.stream().anyMatch(currentPermission -> currentPermission == permission);
    }

    @Override
    public List<Permission> getPermissions() {
        return new ArrayList<>(permissionList);
    }

    @Override
    public String toString() {
        return getName();
    }
}
