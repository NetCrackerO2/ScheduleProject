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
                .anyMatch(ra -> ra.getAccountIndex() == accountIndex && ra.getRoleIndex() == roleIndex))
            throw new IllegalArgumentException("Аккаунт уже имеет эту роль.");
        this.accountIndex = accountIndex;
    }

    @Override
    public void setRoleIndex(int roleIndex) {
        if (RoleAssignmentManager.getInstance().getAllObjects().stream()
                .anyMatch(ra -> ra.getAccountIndex() == accountIndex && ra.getRoleIndex() == roleIndex))
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

    public static RoleAssignment fromJSONObject(JSONObject jsonObject) {
        int index = (int) (long) (Long) jsonObject.get("index");
        int account = (int) (long) (Long) jsonObject.get("account");
        int role = (int) (long) (Long) jsonObject.get("role");

        return new RoleAssignment() {

            @Override
            public int getIndex() {
                return index;
            }

            @Override
            public JSONObject getJSONObject() {
                return null;
            }

            @Override
            public int getAccountIndex() {
                return account;
            }

            @Override
            public void setAccountIndex(int accountIndex) {
                throw new RuntimeException("Immutable object");
            }

            @Override
            public int getRoleIndex() {
                return role;
            }

            @Override
            public void setRoleIndex(int roleIndex) {
                throw new RuntimeException("Immutable object");
            }
            
        };
    }
}
