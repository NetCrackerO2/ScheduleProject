package account;

import org.json.simple.JSONObject;

public class UnregistredAccount implements Account {
    private int index = -1;
    private String name;
    private int groupIndex = -1;
    private int cathedraIndex = -1;

    public UnregistredAccount(int index) {
        this.index = index;
    }

    public UnregistredAccount(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        name = (String) jsonObject.get("name");
        groupIndex = (int) (long) (Long) jsonObject.get("groupIndex");
        cathedraIndex = (int) (long) (Long) jsonObject.get("cathedraIndex");
    }

    @Override
    public int getIndex() {
        return -1;
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", -1);
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

    @Override
    public void setGroupIndex(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    @Override
    public void setCathedraIndex(int cathedraIndex) {
        this.cathedraIndex = cathedraIndex;
    }

}
