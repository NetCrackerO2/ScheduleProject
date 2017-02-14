package subject;

import cathedra.CathedraManager;
import org.json.simple.JSONObject;

import java.util.Objects;

public class SubjectImpl implements Subject {
    private int index = -1;
    private int cathedraIndex = -1;
    private String name;

    SubjectImpl(int index) {
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
        jsonObject.put("cathedraIndex", cathedraIndex);

        return jsonObject;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (SubjectManager.getInstance().getAllObjects().stream()
                .anyMatch(subject -> Objects.equals(subject.getName(), name)))
            throw new IllegalArgumentException("Предмет с таким именем уже существует.");

        this.name = name;
    }
}
