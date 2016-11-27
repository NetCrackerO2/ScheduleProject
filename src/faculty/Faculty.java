package faculty;

import account.Account;


// Для теста
public interface Faculty {
    int getNumber();
    
    void setNumber(int number);
    
    Account getDean();
    
    void setDean(Account dean);
}
