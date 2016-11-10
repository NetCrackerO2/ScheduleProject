package group;


import cathedra.Cathedra;
import faculty.Faculty;


/**
 * @author temon137
 */
public interface Group {
    Faculty getFaculty();

    void setFaculty(Faculty faculty);

    Cathedra getCathedra();

    void setCathedra(Cathedra cathedra);

    int getNumber();

    void setNumber(int number);
}