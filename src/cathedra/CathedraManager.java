package cathedra;


import manager.GenericEntityManager;


public class CathedraManager extends GenericEntityManager<Cathedra> {
    private static CathedraManager instance;

    private CathedraManager() {
        super();
    }

    public static CathedraManager getInstance() {
        if (instance == null)
            instance = new CathedraManager();
        return instance;
    }

    @Override
    protected Cathedra newObject(int index) {
        return new CathedraImpl(index);
    }
}
