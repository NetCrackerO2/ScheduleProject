package account.role;

import manager.GenericEntityManager;

//TODO: реорганизовать класс для использования индекса аккаунта вместо ссылки на аккаунт.
public class RoleManager extends GenericEntityManager<Role> {
    private static volatile RoleManager instance;

    private RoleManager() {
        super();
    }

    public static RoleManager getInstance() {
        if (instance == null)
            synchronized (RoleManager.class) {
                if (instance == null)
                    instance = new RoleManager();
            }
        return instance;
    }

    @Override
    protected Role newObject(int roleIndex) {
        return new RoleImpl(roleIndex);
    }
    
    @Override
    public void removeObject(int index) {
        synchronized(RoleAssignmentManager.getInstance()){
            if(RoleAssignmentManager.getInstance().getAllObjects().stream().anyMatch(x -> x.getRoleIndex() == index))
                throw new IllegalArgumentException("Объект не может быть удален.");
            super.removeObject(index);
        }
    }
}
