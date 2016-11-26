package group;


import cathedra.Cathedra;


public interface Group {
    Cathedra getCathedra();

    void setCathedra(Cathedra cathedra);

    int getNumber();

    void setNumber(int number);

    int getProfessionCode();

    void setProfessionCode(int professionCode);

    int getReceiptYear();

    void setReceiptYear(int year);
}
