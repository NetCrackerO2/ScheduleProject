package account;


import account.role.Permission;
import account.role.RoleManager;
import cathedra.CathedraManager;
import group.GroupManager;


public class AccountImpl implements Account{
    private int index;
    private String name;
    private int groupIndex = -1;
    private int cathedraIndex = -1;

    AccountImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getGroupIndex() {
        return groupIndex;
    }

    @Override
    public void setGroupIndex(int groupIndex) {
        if (!RoleManager.getInstance().hasPermission(this, Permission.InGroup))
            throw new RuntimeException("Данный аккаунт не может состоять в группе.");
        if (!GroupManager.getInstance().isExist(groupIndex))
            throw new IllegalArgumentException("Группы с таким номером не существует.");

        this.groupIndex = groupIndex;
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    @Override
    public void setCathedraIndex(int cathedraIndex) {
        if (!RoleManager.getInstance().hasPermission(this, Permission.InCathedra))
            throw new RuntimeException("Данный аккаунт не может состоять в кафедре.");
        if (!CathedraManager.getInstance().isExist(cathedraIndex))
            throw new IllegalArgumentException("Кафедры с таким именем не существует.");

        this.cathedraIndex = cathedraIndex;
    }
}
