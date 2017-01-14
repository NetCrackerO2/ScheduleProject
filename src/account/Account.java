package account;


import manager.Entity;


public interface Account extends Entity {
    String getName();

    void setName(String name);

    int getGroupIndex();

    void setGroupIndex(int groupIndex);

    int getCathedraIndex();

    void setCathedraIndex(int cathedraIndex);
}
