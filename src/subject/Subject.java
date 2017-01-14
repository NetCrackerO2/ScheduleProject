package subject;


import manager.Entity;


public interface Subject extends Entity {
    int getIndex();

    int getCathedraIndex();

    void setCathedraIndex(int cathedraIndex);

    String getName();

    void setName(String name);
}
