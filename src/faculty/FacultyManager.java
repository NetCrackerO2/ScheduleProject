package faculty;


import manager.GenericEntityManager;


public class FacultyManager extends GenericEntityManager<Faculty> {
    private static FacultyManager instance;

    private FacultyManager() {
        super();
    }

    public static FacultyManager getInstance() {
        if (instance == null)
            instance = new FacultyManager();
        return instance;
    }

    @Override
    protected Faculty newObject(int index) {
        return new FacultyImpl(index);
    }
}
