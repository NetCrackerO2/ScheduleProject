package account;

import account.role.Permission;
import account.role.RoleAssignmentManager;
import cathedra.CathedraManager;
import group.GroupManager;
import org.json.simple.JSONObject;

public class AccountImpl implements Account {
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
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", index);
        jsonObject.put("name", name);
        jsonObject.put("groupIndex", groupIndex);
        jsonObject.put("cathedraIndex", cathedraIndex);

        return jsonObject;
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

    /**
     * WARNING: locks RoleAssignmentManager -> GroupManager
     */
    @Override
    public void setGroupIndex(int groupIndex) {
        if (groupIndex < 0) {
            this.groupIndex = -1;
            return;
        }
        synchronized (RoleAssignmentManager.getInstance()) {
            synchronized (GroupManager.getInstance()) {
                if (!RoleAssignmentManager.getInstance().hasPermission(index, Permission.InGroup))
                    throw new RuntimeException("Данный аккаунт не может состоять в группе.");
                if (!GroupManager.getInstance().isExist(groupIndex))
                    throw new IllegalArgumentException("Группы с таким номером не существует.");

                this.groupIndex = groupIndex;
            }
        }
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    /**
     * WARNING: locks RoleAssignmentManager -> CathedraManager
     */
    @Override
    public void setCathedraIndex(int cathedraIndex) {
        if (cathedraIndex < 0) {
            this.cathedraIndex = -1;
            return;
        }
        synchronized (RoleAssignmentManager.getInstance()) {
            synchronized (CathedraManager.getInstance()) {
                if (!RoleAssignmentManager.getInstance().hasPermission(index, Permission.InCathedra))
                    throw new RuntimeException("Данный аккаунт не может состоять в кафедре.");
                if (!CathedraManager.getInstance().isExist(cathedraIndex))
                    throw new IllegalArgumentException("Кафедры с таким именем не существует.");

                this.cathedraIndex = cathedraIndex;
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
