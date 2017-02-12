package group;

import account.AccountManager;
import manager.GenericEntityManager;

public class GroupManager extends GenericEntityManager<Group> {
    private static volatile GroupManager instance;

    private GroupManager() {
        super();
    }

    public static GroupManager getInstance() {
        if (instance == null)
            synchronized (GroupManager.class) {
                if (instance == null)
                    instance = new GroupManager();
            }
        return instance;
    }

    @Override
    protected Group newObject(int index) {
        return new GroupImpl(index);
    }

    @Override
    public void removeObject(int index) {
        synchronized (AccountManager.getInstance()) {
            if (AccountManager.getInstance().getAllObjects().stream().anyMatch(x -> x.getGroupIndex() == index))
                throw new IllegalArgumentException("Объект не может быть удален.");
            super.removeObject(index);
        }
    }
}
