package group;

import cathedra.CathedraManager;
import org.json.simple.JSONObject;

public class GroupImpl implements Group {
    private int index;
    private int cathedraIndex;
    private int number, receiptYear, professionCode;

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
    

    public static Group fromJSONObject(JSONObject jsonObject) {
        int index = (int)(Integer) jsonObject.get("index");
        int number = (int)(Integer) jsonObject.get("number");
        int cathedra = (int)(Integer) jsonObject.get("cathedraIndex");
        int year = (int)(Integer) jsonObject.get("receiptYear");
        int prof = (int)(Integer) jsonObject.get("professionCode");

        return new Group() {
            @Override
            public int getIndex() {
                return index;
            }

            @Override
            public JSONObject getJSONObject() {
                return jsonObject;
            }

            @Override
            public int getCathedraIndex() {
                return cathedra;
            }

            @Override
            public void setCathedraIndex(int cathedraIndex) {
                throw new RuntimeException("Immutable object");
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
            public int getProfessionCode() {
                return prof;
            }

            @Override
            public void setProfessionCode(int professionCode) {
                throw new RuntimeException("Immutable object");
            }

            @Override
            public int getReceiptYear() {
                return year;
            }

            @Override
            public void setReceiptYear(int year) {
                throw new RuntimeException("Immutable object");
            }

        };
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
        if (GroupManager.getInstance().getAllObjects().stream().anyMatch(group -> group.getNumber() == number))
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
