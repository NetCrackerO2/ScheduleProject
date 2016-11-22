
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
        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public boolean setName(String name) {
        if(AccountManager.getInstance().isExist(name))
            return false;

        this.name = name;
        return true;
    }
}
