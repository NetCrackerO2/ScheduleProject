package faculty;

import account.role.Permission;
import account.role.RoleAssignmentManager;
import org.json.simple.JSONObject;

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
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", index);
        jsonObject.put("number", number);
        jsonObject.put("deanAccountIndex", deanAccountIndex);

        return jsonObject;
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

    /**
     * WARNING: locks RoleAssignmentManager
     */
    @Override
    public void setDeanAccountIndex(int deanAccountIndex) {
        if (deanAccountIndex < 0) {
            this.deanAccountIndex = -1;
            return;
        }
        synchronized (RoleAssignmentManager.getInstance()) {
            if (!RoleAssignmentManager.getInstance().hasPermission(deanAccountIndex, Permission.InFaculty))
                throw new RuntimeException("Данный аккаунт не может быть деканом факультета.");

            this.deanAccountIndex = deanAccountIndex;
        }
    }
}
