package faculty;


// Для теста
public class FacultyImpl implements Faculty {
    private int number;

    public FacultyImpl(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (FacultyManager.getInstance().isExist(number))
            throw new IllegalArgumentException("Факультет с таким номером уже существует.");

        this.number = number;
    }
}
