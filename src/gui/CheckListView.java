package gui;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class CheckListView<T1, T2> {
    private ListView<T1> listView;
    private Map<T1, SimpleBooleanProperty> map;

    public CheckListView(ListView<T1> listView) {
        this.listView = listView;
        listView.setCellFactory(CheckBoxListCell.forListView(
                item -> map.get(item))
        );

        map = new HashMap<>();
    }

    public void setSource(List<T1> source) {
        source.forEach(item -> {
                    map.put(item, new SimpleBooleanProperty(false));
                    listView.getItems().add(item);
                }
        );
    }

    public List<T1> getCheckedItems() {
        ArrayList<T1> checkedItems = new ArrayList<>();

        map.keySet().forEach(key -> {
            if (map.get(key).getValue())
                checkedItems.add(key);
        });

        return checkedItems;
    }

    /**
     * Возвращает состояние key, анализируя object
     *
     * @param key
     * @param object
     * @return
     */
    public abstract boolean getState(T1 key, T2 object);

    public void onShowDetails(T2 object) {
        map.keySet().forEach(key -> map.get(key).setValue(getState(key, object)));
    }
}
