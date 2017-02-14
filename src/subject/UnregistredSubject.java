package subject;

import org.json.simple.JSONObject;

public class UnregistredSubject implements Subject {
    private int index = -1;
    private int cathedraIndex;
    private String name;

    public UnregistredSubject(int index) {
        this.index = index;
    }

    public UnregistredSubject(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        name = (String) jsonObject.get("name");
        cathedraIndex = (int) (long) (Long) jsonObject.get("cathedraIndex");
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", index);
        jsonObject.put("name", name);
        jsonObject.put("cathedraIndex", cathedraIndex);

        return jsonObject;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    @Override
    public void setCathedraIndex(int cathedraIndex) {
        this.cathedraIndex = cathedraIndex;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
