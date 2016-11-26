package group;


import cathedra.Cathedra;
import cathedra.CathedraManager;


/**
 * @author temon137
 */
public class GroupImpl implements Group {
    private Cathedra cathedra;
    private int number, receiptYear, professionCode;


    protected GroupImpl(Cathedra cathedra, int number) {
        this.cathedra = cathedra;
        this.number = number;
    }


    @Override
    public int getReceiptYear() {
        return this.receiptYear;
    }

    @Override
    public void setReceiptYear(int year) {
        this.receiptYear = year;
    }

    @Override
    public Cathedra getCathedra() {
        return cathedra;
    }

    @Override
    public void setCathedra(Cathedra cathedra) {
        if (!CathedraManager.getInstance().isExist(cathedra.getName()))
            throw new IllegalArgumentException("Такой кафедры не существует.");

        this.cathedra = cathedra;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (GroupManager.getInstance().isExist(number))
            throw new IllegalArgumentException("Группа с таким номером уже существует!");

        this.number = number;
    }

    @Override
    public int getProfessionCode() {
        return professionCode;
    }

    @Override
    public void setProfessionCode(int professionCode) {
        this.professionCode = professionCode;
    }
}
