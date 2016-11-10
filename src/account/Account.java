
package account;



import group.*;


/**
 * @author Александр
 */
public class Account {
    private String name;
    private Group group;

    public Account(String name, Group group) {
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

    public void setName(String name) {
        this.name = name;
    }
}