package account.role;

import org.json.simple.JSONObject;

public class RoleAssignmentImpl implements RoleAssignment {
    private int accountIndex = -1;
    private int roleIndex = -1;
    private int index;

    public RoleAssignmentImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getAccountIndex() {
        return accountIndex;
    }

    @Override
    public int getRoleIndex() {
        return roleIndex;
    }

    @Override
    public void setAccountIndex(int accountIndex) {
        if (RoleAssignmentManager.getInstance().getAllObjects().stream()
                .anyMatch(ra -> ra.getAccountIndex() == accountIndex && ra.getRoleIndex() == roleIndex && ra != this))
            throw new IllegalArgumentException("Аккаунт уже имеет эту роль.");
        this.accountIndex = accountIndex;
    }

    @Override
    public void setRoleIndex(int roleIndex) {
        if (RoleAssignmentManager.getInstance().getAllObjects().stream()
                .anyMatch(ra -> ra.getAccountIndex() == accountIndex && ra.getRoleIndex() == roleIndex && ra != this))
            throw new IllegalArgumentException("Аккаунт уже имеет эту роль.");
        this.roleIndex = roleIndex;
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
    public String toString() {
        return getIndex() + "";
    }
}
