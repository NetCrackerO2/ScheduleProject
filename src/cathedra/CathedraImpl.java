package cathedra;


import faculty.Faculty;
import faculty.FacultyManager;


/**
 * @author Dmi3
 */
public class CathedraImpl implements Cathedra {
    private String name;
    private Faculty faculty;


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

}
