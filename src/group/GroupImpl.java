package group;


import cathedra.Cathedra;
import faculty.Faculty;


/**
 * @author temon137
 */
public class GroupImpl implements Group {
    private Faculty faculty;
    private Cathedra cathedra;
    private int number;
    //------------


    protected GroupImpl(Faculty faculty, Cathedra cathedra, int number) {
        this.faculty = faculty;
        this.cathedra = cathedra;
        this.number = number;
    }

    @Override
    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public Cathedra getCathedra() {
        return cathedra;
    }

    @Override
    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }
    //============


}
