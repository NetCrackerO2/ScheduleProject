package account.role;

import manager.Entity;

public interface RoleAssignment extends Entity {
    int getAccountIndex();

    void setAccountIndex(int accountIndex);

    int getRoleIndex();

    void setRoleIndex(int roleIndex);
}
