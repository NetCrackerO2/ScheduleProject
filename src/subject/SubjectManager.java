package subject;


import manager.GenericEntityManager;


public class SubjectManager extends GenericEntityManager<Subject> {
    private static SubjectManager instance;

    private SubjectManager() {
        super();
    }

    public static SubjectManager getInstance() {
        if (instance == null)
            synchronized(SubjectManager.class){
                if (instance == null)
                    instance = new SubjectManager();
            }
        return instance;
    }

    @Override
    protected Subject newObject(int index) {
        return new SubjectImpl(index);
    }
}
