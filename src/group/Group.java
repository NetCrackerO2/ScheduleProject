package group;


import manager.Entity;


public interface Group extends Entity {
    int getIndex();

    int getCathedraIndex();

    void setCathedraIndex(int cathedraIndex);

    int getNumber();

    void setNumber(int number);

    int getProfessionCode();

    void setProfessionCode(int professionCode);

    int getReceiptYear();

    void setReceiptYear(int year);
}
