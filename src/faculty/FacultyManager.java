package faculty;


import manager.GenericEntityManager;


public class FacultyManager extends GenericEntityManager<Faculty> {
    private static volatile FacultyManager instance;

    private FacultyManager() {
        super();
    }

    public static FacultyManager getInstance() {
        if (instance == null)
            synchronized(FacultyManager.class){
                if (instance == null)
                    instance = new FacultyManager();
            }
        return instance;
    }

    @Override
    protected Faculty newObject(int index) {
        return new FacultyImpl(index);
    }
}
