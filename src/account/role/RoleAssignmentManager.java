package account.role;

import manager.GenericEntityManager;

public class RoleAssignmentManager extends GenericEntityManager<RoleAssignment> {
    private static volatile RoleAssignmentManager instance;

    private RoleAssignmentManager() {
        super();
    }

    public static RoleAssignmentManager getInstance() {
        if (instance == null)
            synchronized (RoleAssignmentManager.class) {
                if (instance == null)
                    instance = new RoleAssignmentManager();
            }
        return instance;
    }

    @Override
    protected RoleAssignment newObject(int roleIndex) {
        return new RoleAssignmentImpl(roleIndex);
    }

    /**
     * WARNING: locks RoleManager
     * 
     * @param accountIndex
     * @param permission
     * @return
     */
    public boolean hasPermission(int accountIndex, Permission permission) {
        synchronized (RoleManager.getInstance()) {
            return this.getAllObjects().stream().anyMatch(ra -> accountIndex == ra.getAccountIndex()
                    && RoleManager.getInstance().getObject(ra.getRoleIndex()).hasPermission(permission));
        }
    }
}
