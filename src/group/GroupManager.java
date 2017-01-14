package group;


import manager.GenericEntityManager;


public class GroupManager extends GenericEntityManager<Group> {
    private static GroupManager instance;

    private GroupManager() {
        super();
    }

    public static GroupManager getInstance() {
        if (instance == null)
            instance = new GroupManager();
        return instance;
    }

    @Override
    protected Group newObject(int index) {
        return new GroupImpl(index);
    }
}
