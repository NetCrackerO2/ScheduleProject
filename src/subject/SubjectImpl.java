package subject;


import cathedra.CathedraManager;

import java.util.Objects;


public class SubjectImpl implements Subject {
    private int index;
    private int cathedraIndex;
    private String name;

    SubjectImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    @Override
    public void setCathedraIndex(int cathedraIndex) {
        if (!CathedraManager.getInstance().isExist(cathedraIndex))
            throw new IllegalArgumentException("Такой кафедры не существует.");

        this.cathedraIndex = cathedraIndex;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (SubjectManager.getInstance().getAllObjects().stream().anyMatch(subject -> Objects.equals(subject.getName(), name)))
            throw new IllegalArgumentException("Предмет с таким именем уже существует.");

        this.name = name;
    }
}
