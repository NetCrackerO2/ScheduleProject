package Group;


import Faculty.Faculty;


/**
 * @author temon137
 */
public class GroupImpl implements Group {
    private Faculty faculty;
    private int number;
    //------------


    protected GroupImpl(Faculty faculty, int number) {
        this.faculty = faculty;
        this.number = number;
    }

    @Override
    public Faculty getFaculty() {
        return faculty;
    }

    @Override
    public int getNumber() {
        return number;
    }
    //============


}
