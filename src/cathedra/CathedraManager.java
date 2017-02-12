package cathedra;

import account.AccountManager;
import manager.GenericEntityManager;
import subject.SubjectManager;

public class CathedraManager extends GenericEntityManager<Cathedra> {
    private static volatile CathedraManager instance;

    private CathedraManager() {
        super();
    }

    public static CathedraManager getInstance() {
        if (instance == null)
            synchronized (CathedraManager.class) {
                if (instance == null)
                    instance = new CathedraManager();
            }
        return instance;
    }

    @Override
    protected Cathedra newObject(int index) {
        return new CathedraImpl(index);
    }

    @Override
    public void removeObject(int index) {
        synchronized (AccountManager.getInstance()) {
            synchronized (SubjectManager.getInstance()) {
                if (AccountManager.getInstance().getAllObjects().stream().anyMatch(x -> x.getCathedraIndex() == index))
                    throw new IllegalArgumentException("Объект не может быть удален.");
                if (SubjectManager.getInstance().getAllObjects().stream().anyMatch(x -> x.getCathedraIndex() == index))
                    throw new IllegalArgumentException("Объект не может быть удален.");
                super.removeObject(index);
            }
        }
    }

}
