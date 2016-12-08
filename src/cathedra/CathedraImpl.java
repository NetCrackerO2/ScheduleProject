package cathedra;


import account.Account;
import account.Permission;
import account.RoleManager;
import faculty.Faculty;
import faculty.FacultyManager;


/**
 * @author Dmi3
 */
public class CathedraImpl implements Cathedra {
    private String name;
    private Faculty faculty;
    private Account head;

    protected CathedraImpl(Faculty faculty, String name) {
        this.name = name;
        this.faculty = faculty;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Faculty getFaculty() {
        return this.faculty;
    }

    @Override
    public void setName(String name) {
        if (CathedraManager.getInstance().isExist(name))
            throw new IllegalArgumentException("Кафедра с таким именем уже существует.");

        this.name = name;
    }

    @Override
    public void setFaculty(Faculty faculty) {
        if (!FacultyManager.getInstance().isExist(faculty.getNumber()))
            throw new IllegalArgumentException("Такого факультета не существует.");

        this.faculty = faculty;
    }

    @Override
    public Account getHead() {
        return this.head;
    }

    @Override
    public void setHead(Account head) {
        if (!RoleManager.getInstance().hasPermission(head, Permission.InCathedra))
            throw new RuntimeException("Данный аккаунт не может быть заведующим кафедры.");

        this.head = head;
    }

}
