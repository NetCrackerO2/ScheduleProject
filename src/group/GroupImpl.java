package group;


import cathedra.CathedraManager;


public class GroupImpl implements Group {
    private int index;
    private int cathedraIndex;
    private int number, receiptYear, professionCode;


    GroupImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
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
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    @Override
    public void setCathedraIndex(int cathedraIndex) {
        if (!CathedraManager.getInstance().isExist(cathedraIndex))
            throw new IllegalArgumentException("Такой кафедры не существует.");

        this.cathedraIndex = cathedraIndex;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (GroupManager.getInstance().getAllObjects().stream().anyMatch(group -> group.getNumber() == number))
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
