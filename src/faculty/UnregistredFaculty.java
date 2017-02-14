package faculty;

import org.json.simple.JSONObject;

public class UnregistredFaculty implements Faculty {
    private int index = -1;
    private int number = -1;
    private int deanAccountIndex = -1;

    UnregistredFaculty(int index) {
        this.index = index;
    }

    UnregistredFaculty(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        number = (int) (long) (Long) jsonObject.get("number");
        deanAccountIndex = (int) (long) (Long) jsonObject.get("deanAccountIndex");
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
    public int getIndex() {
        return index;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int getDeanAccountIndex() {
        return deanAccountIndex;
    }

    @Override
    public void setDeanAccountIndex(int deanAccountIndex) {
        this.deanAccountIndex = deanAccountIndex;
    }

}
