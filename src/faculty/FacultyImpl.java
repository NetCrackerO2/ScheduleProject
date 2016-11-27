package faculty;

import account.Account;


// Для теста
public class FacultyImpl implements Faculty {
    private int number;
    private Account dean;

    public FacultyImpl(int number) {
        this.number = number;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (FacultyManager.getInstance().isExist(number))
            throw new IllegalArgumentException("Факультет с таким номером уже существует.");

        this.number = number;
    }
    
    @Override
    public Account getDean(){
        return this.dean;
    }
    
    @Override
    public void setDean(Account dean){
        this.dean = dean;
    }
}
