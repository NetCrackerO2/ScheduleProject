
package account;


import cathedra.Cathedra;
import cathedra.CathedraManager;
import group.Group;
import group.GroupManager;


/**
 * @author Александр
 */
public class Account {
    private String name;
    private Group group;
    private Cathedra cathedra;

    protected Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (AccountManager.getInstance().isExist(name))
            throw new IllegalArgumentException("Аккаунт с таким именем уже существует.");

        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        if (!RoleManager.getInstance().hasPermission(this, Permission.InGroup))
            throw new RuntimeException("Данный аккаунт не может состоять в группе.");
        if (group != null && !GroupManager.getInstance().isExist(group.getNumber()))
            throw new IllegalArgumentException("Группы с таким номером не существует.");

        this.group = group;
    }

    public Cathedra getCathedra() {
        return cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        if (!RoleManager.getInstance().hasPermission(this, Permission.InCathedra))
            throw new RuntimeException("Данный аккаунт не может состоять в кафедре.");
        if (cathedra != null && !CathedraManager.getInstance().isExist(cathedra.getName()))
            throw new IllegalArgumentException("Кафедры с таким именем не существует.");

        this.cathedra = cathedra;
    }
}
