package subject;


import cathedra.Cathedra;
import cathedra.CathedraManager;


/**
 * @author AsGreyWolf
 */
public class SubjectImpl implements Subject {
    private Cathedra cathedra;
    private String name;

    protected SubjectImpl(Cathedra cathedra, String name) {
        this.cathedra = cathedra;
        this.name = name;
    }

    @Override
    public Cathedra getCathedra() {
        return cathedra;
    }

    @Override
    public void setCathedra(Cathedra cathedra) {
        if (!CathedraManager.getInstance().isExist(cathedra.getName()))
            throw new IllegalArgumentException("Такой кафедры не существует.");

        this.cathedra = cathedra;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (SubjectManager.getInstance().isExist(name))
            throw new IllegalArgumentException("Предмет с таким именем уже существует.");

        this.name = name;
    }
}
