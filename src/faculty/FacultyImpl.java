package faculty;


import account.role.Permission;
import account.role.RoleManager;
import org.json.simple.JSONObject;

public class FacultyImpl implements Faculty {
    private int index;
    private int number = -1;
    private int deanAccountIndex = -1;

    FacultyImpl(int index) {
        this.index = index;
    }

    public static Faculty fromJSONObject(JSONObject jsonObject) {
        int index = (int) (long) (Long) jsonObject.get("index");
        int number = (int) (long) (Long) jsonObject.get("number");
        int dean = (int) (long) (Long) jsonObject.get("deanAccountIndex");

        return new Faculty() {
            @Override
            public int getIndex() {
                return index;
            }

            @Override
            public JSONObject getJSONObject() {
                return jsonObject;
            }

            @Override
            public int getNumber() {
                return number;
            }

            @Override
            public void setNumber(int number) {
                throw new RuntimeException("Immutable object");
            }

            @Override
            public int getDeanAccountIndex() {
                return dean;
            }

            @Override
            public void setDeanAccountIndex(int deanAccountIndex) {
                throw new RuntimeException("Immutable object");
            }
        };
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
     * WARNING: locks RoleManager
     */
    @Override
    public void setDeanAccountIndex(int deanAccountIndex) {
        if (deanAccountIndex < 0) {
            this.deanAccountIndex = -1;
            return;
        }
        synchronized (RoleManager.getInstance()) {
            if (!RoleManager.getInstance().hasPermission(deanAccountIndex, Permission.InFaculty))
                throw new RuntimeException("Данный аккаунт не может быть деканом факультета.");

            this.deanAccountIndex = deanAccountIndex;
        }
    }
}
