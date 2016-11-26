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

    public void setNumber(int number) {
        if (FacultyManager.getInstance().isExist(number))
            throw new IllegalArgumentException("Факультет с таким номером уже существует.");

        this.number = number;
    }
    
    public Account getDean(){
        return this.dean;
    }
    
    public void setDean(Account dean){
        this.dean = dean;
    }
}
