package faculty;

// Для теста
public class FacultyImpl implements Faculty{
    private int number;

    public FacultyImpl(int number){
        this.number = number;
    }

    public int getNumber(){
        return number;
    }    
}
