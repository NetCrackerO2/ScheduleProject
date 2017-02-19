package group;

import cathedra.CathedraManager;
import org.json.simple.JSONObject;

public class GroupImpl implements Group {
    private int index = -1;
    private int cathedraIndex = -1;
    private int number = -1, receiptYear = -1, professionCode = -1;

    GroupImpl(int index) {
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
        jsonObject.put("cathedraIndex", cathedraIndex);
        jsonObject.put("receiptYear", receiptYear);
        jsonObject.put("professionCode", professionCode);

        return jsonObject;
    }

    @Override
    public int getReceiptYear() {
        return this.receiptYear;
    }

    @Override
    public void setReceiptYear(int year) {
        this.receiptYear = year;
    }

    @Override
    public int getCathedraIndex() {
        return cathedraIndex;
    }

    /**
     * WARNING: locks CathedraManager
     */
    @Override
    public void setCathedraIndex(int cathedraIndex) {
        if (cathedraIndex < 0) {
            this.cathedraIndex = -1;
            return;
        }
        synchronized (CathedraManager.getInstance()) {
            if (!CathedraManager.getInstance().isExist(cathedraIndex))
                throw new IllegalArgumentException("Такой кафедры не существует.");

            this.cathedraIndex = cathedraIndex;
        }
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (GroupManager.getInstance().getAllObjects().stream().anyMatch(group -> group.getNumber() == number && group != this))
            throw new IllegalArgumentException("Группа с таким номером уже существует!");

        this.number = number;
    }

    @Override
    public int getProfessionCode() {
        return professionCode;
    }

    @Override
    public void setProfessionCode(int professionCode) {
        this.professionCode = professionCode;
    }
}
