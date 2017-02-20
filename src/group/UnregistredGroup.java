package group;

import org.json.simple.JSONObject;

public class UnregistredGroup implements Group {
    private int index = -1;
    private int cathedraIndex = -1;
    private int number = -1, receiptYear = -1, professionCode = -1;

    public UnregistredGroup(int index) {
        this.index = index;
    }

    public UnregistredGroup(JSONObject jsonObject) {
        index = (int) (long) (Long) jsonObject.get("index");
        number = (int) (long) (Long) jsonObject.get("number");
        cathedraIndex = (int) (long) (Long) jsonObject.get("cathedraIndex");
        receiptYear = (int) (long) (Long) jsonObject.get("receiptYear");
        professionCode = (int) (long) (Long) jsonObject.get("professionCode");
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("index", index);
        jsonObject.put("number", number);
        jsonObject.put("cathedraIndex", cathedraIndex);
        jsonObject.put("receiptYear", receiptYear);
        jsonObject.put("professionCode", professionCode);

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
        cathedraIndex = cathedraIndex;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        number = number;
    }

    @Override
    public int getProfessionCode() {
        return professionCode;
    }

    @Override
    public void setProfessionCode(int professionCode) {
        professionCode = professionCode;
    }

    @Override
    public int getReceiptYear() {
        return receiptYear;
    }

    @Override
    public void setReceiptYear(int year) {
        receiptYear = receiptYear;
    }

    @Override
    public String toString() {
        return getNumber() + "";
    }
}
