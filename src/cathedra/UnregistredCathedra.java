package cathedra;

import org.json.simple.JSONObject;

public class UnregistredCathedra implements Cathedra {
    private int index = -1;
    private String name;
    private int facultyIndex = -1;
    private int headAccountIndex = -1;

    public UnregistredCathedra(int index) {
        this.index = index;
    }

    public UnregistredCathedra(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        name = (String) jsonObject.get("name");
        facultyIndex = (int) (long) (Long) jsonObject.get("facultyIndex");
        headAccountIndex = (int) (long) (Long) jsonObject.get("headAccountIndex");
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
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getFacultyIndex() {
        return facultyIndex;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setFacultyIndex(int facultyIndex) {
        this.facultyIndex = facultyIndex;
    }

    @Override
    public int getHeadAccountIndex() {
        return headAccountIndex;
    }

    @Override
    public void setHeadAccountIndex(int headAccountIndex) {
        this.headAccountIndex = headAccountIndex;
    }

}
