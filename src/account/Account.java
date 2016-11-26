
package account;


import group.*;


/**
 * @author Александр
 */
public class Account {
    private String name;
    private Group group;

    protected Account(String name) {
        this(name, null);
    }

    protected Account(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        if (!GroupManager.getInstance().isExist(group.getNumber()))
            throw new IllegalArgumentException("Группы с таким номером не существует.");

        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (AccountManager.getInstance().isExist(name))
            throw new IllegalArgumentException("Аккаунт с таким именем уже существует.");

        this.name = name;
    }
}
