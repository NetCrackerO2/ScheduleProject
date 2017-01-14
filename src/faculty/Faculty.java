package faculty;


import manager.Entity;


public interface Faculty extends Entity {
    int getIndex();

    int getNumber();

    void setNumber(int number);

    int getDeanAccountIndex();

    void setDeanAccountIndex(int deanAccountIndex);
}
