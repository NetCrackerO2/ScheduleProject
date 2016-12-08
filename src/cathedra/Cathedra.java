package cathedra;


import account.Account;
import faculty.Faculty;


public interface Cathedra {

    String getName();

    Faculty getFaculty();

    void setName(String name);

    void setFaculty(Faculty faculty);

    Account getHead();

    void setHead(Account head);
}
