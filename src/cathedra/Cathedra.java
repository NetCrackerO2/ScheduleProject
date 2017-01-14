package cathedra;


import manager.Entity;


public interface Cathedra extends Entity {
    int getIndex();

    String getName();

    int getFacultyIndex();

    void setName(String name);

    void setFacultyIndex(int facultyIndex);

    int getHeadAccountIndex();

    void setHeadAccountIndex(int headAccountIndex);
}
