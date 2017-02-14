package account.role;

import org.json.simple.JSONObject;

public class UnregistredRoleAssignment implements RoleAssignment {
    private int accountIndex = -1;
    private int roleIndex = -1;
    private int index = -1;

    public UnregistredRoleAssignment(int index) {
        this.index = index;
    }

    public UnregistredRoleAssignment(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        accountIndex = (int) (long) (Long) jsonObject.get("account");
        roleIndex = (int) (long) (Long) jsonObject.get("role");
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("index", index);
        jsonObject.put("account", accountIndex);
        jsonObject.put("role", roleIndex);
        return jsonObject;
    }

    @Override
    public int getAccountIndex() {
        return accountIndex;
    }

    @Override
    public void setAccountIndex(int accountIndex) {
        this.accountIndex = accountIndex;
    }

    @Override
    public int getRoleIndex() {
        return roleIndex;
    }

    @Override
    public void setRoleIndex(int roleIndex) {
        this.roleIndex = roleIndex;
    }

}
