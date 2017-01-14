package cathedra;


import account.AccountManager;
import account.role.Permission;
import account.role.RoleManager;
import faculty.FacultyManager;

import java.util.Objects;


public class CathedraImpl implements Cathedra {
    private int index;
    private String name;
    private int facultyIndex = -1;
    private int headAccountIndex = -1;

    CathedraImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getFacultyIndex() {
        return this.facultyIndex;
    }

    @Override
    public void setName(String name) {
        if (CathedraManager.getInstance().getAllObjects().stream().anyMatch(cathedra -> Objects.equals(cathedra.getName(), name)))
            throw new IllegalArgumentException("Кафедра с таким именем уже существует.");

        this.name = name;
    }

    @Override
    public void setFacultyIndex(int facultyIndex) {
        if (!FacultyManager.getInstance().isExist(facultyIndex))
            throw new IllegalArgumentException("Такого факультета не существует.");

        this.facultyIndex = facultyIndex;
    }

    @Override
    public int getHeadAccountIndex() {
        return this.headAccountIndex;
    }

    @Override
    public void setHeadAccountIndex(int headAccountIndex) {
        if (!RoleManager.getInstance().hasPermission(AccountManager.getInstance().getObject(headAccountIndex), Permission.InCathedra))
            throw new RuntimeException("Данный аккаунт не может быть заведующим кафедры.");

        this.headAccountIndex = headAccountIndex;
    }
}
