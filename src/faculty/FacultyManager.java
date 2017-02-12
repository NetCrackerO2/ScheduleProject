package faculty;

import cathedra.CathedraManager;
import manager.GenericEntityManager;

public class FacultyManager extends GenericEntityManager<Faculty> {
    private static volatile FacultyManager instance;

    private FacultyManager() {
        super();
    }

    public static FacultyManager getInstance() {
        if (instance == null)
            synchronized (FacultyManager.class) {
                if (instance == null)
                    instance = new FacultyManager();
            }
        return instance;
    }

    @Override
    protected Faculty newObject(int index) {
        return new FacultyImpl(index);
    }

    @Override
    public void removeObject(int index) {
        synchronized (CathedraManager.getInstance()) {
            if (CathedraManager.getInstance().getAllObjects().stream().anyMatch(x -> x.getFacultyIndex() == index))
                throw new IllegalArgumentException("Объект не может быть удален.");
            super.removeObject(index);
        }
    }
}
