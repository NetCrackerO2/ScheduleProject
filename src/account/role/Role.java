package account.role;

import manager.Entity;

import java.util.List;

public interface Role extends Entity {
    String getName();

    void setName(String name);

    void addPermissions(Permission... permissions);

    void setPermissions(Permission... permissions);

    boolean hasPermission(Permission permission);

    List<Permission> getPermissions();
}