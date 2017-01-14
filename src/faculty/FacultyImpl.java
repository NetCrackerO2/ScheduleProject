package faculty;


import account.AccountManager;
import account.role.Permission;
import account.role.RoleManager;


public class FacultyImpl implements Faculty {
    private int index;
    private int number = -1;
    private int deanAccountIndex = -1;

    FacultyImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (FacultyManager.getInstance().getAllObjects().stream().anyMatch(faculty -> faculty.getNumber() == number))
            throw new IllegalArgumentException("Факультет с таким номером уже существует.");

        this.number = number;
    }

    @Override
    public int getDeanAccountIndex() {
        return this.deanAccountIndex;
    }

    @Override
    public void setDeanAccountIndex(int deanAccountIndex) {
        if (!RoleManager.getInstance().hasPermission(AccountManager.getInstance().getObject(deanAccountIndex), Permission.InFaculty))
            throw new RuntimeException("Данный аккаунт не может быть деканом факультета.");

        this.deanAccountIndex = deanAccountIndex;
    }
}
