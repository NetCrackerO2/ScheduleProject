package cathedra;

import account.role.Permission;
import account.role.RoleAssignmentManager;
import faculty.FacultyManager;
import org.json.simple.JSONObject;

import java.util.Objects;

public class CathedraImpl implements Cathedra {
    private int index;
    private String name;
    private int facultyIndex = -1;
    private int headAccountIndex = -1;

    CathedraImpl(int index) {
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
        jsonObject.put("facultyIndex", facultyIndex);
        jsonObject.put("headAccountIndex", headAccountIndex);

        return jsonObject;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getFacultyIndex() {
        return this.facultyIndex;
    }

    @Override
    public void setName(String name) {
        if (CathedraManager.getInstance().getAllObjects().stream()
                .anyMatch(cathedra -> Objects.equals(cathedra.getName(), name)))
            throw new IllegalArgumentException("Кафедра с таким именем уже существует.");

        this.name = name;
    }

    /**
     * WARNING: locks FacultyManager
     */
    @Override
    public void setFacultyIndex(int facultyIndex) {
        if (facultyIndex < 0) {
            this.facultyIndex = -1;
            return;
        }
        synchronized (FacultyManager.getInstance()) {
            if (!FacultyManager.getInstance().isExist(facultyIndex))
                throw new IllegalArgumentException("Такого факультета не существует.");

            this.facultyIndex = facultyIndex;
        }
    }

    @Override
    public int getHeadAccountIndex() {
        return this.headAccountIndex;
    }

    /**
     * WARNING: locks RoleAssignmentManager
     */
    @Override
    public void setHeadAccountIndex(int headAccountIndex) {
        if (headAccountIndex < 0) {
            this.headAccountIndex = -1;
            return;
        }
        synchronized (RoleAssignmentManager.getInstance()) {
            if (!RoleAssignmentManager.getInstance().hasPermission(headAccountIndex, Permission.InCathedra))
                throw new RuntimeException("Данный аккаунт не может быть заведующим кафедры.");

            this.headAccountIndex = headAccountIndex;
        }
    }
}
