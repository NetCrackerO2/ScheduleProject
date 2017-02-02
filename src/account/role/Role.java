package account.role;

import manager.Entity;

import java.util.List;

public interface Role extends Entity {
    String getName();

    void setName(String name);

    void addPermissions(Permission... permissions);

    boolean hasPermission(Permission permission);

    List<Permission> getPermissions();

    void setPermissions(Permission... permissions);
}