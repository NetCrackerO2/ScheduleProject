package gui;


import com.sun.istack.internal.NotNull;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import manager.Entity;

import java.util.List;


enum NewRowStatus {
    ACTIVE,
    NONACTIVE
}


class TableManager<T extends Entity> {
    private TableView<T> tableView;
    private boolean isNewRowActive;
    private boolean isNewRowSelected;
    private T newObject;
    private ChangeListener<? super T> changeListener; // Может пригодится


    /**
     * @param tableView    Объект таблицы, используемой для вывода
     * @param newRowStatus Статус наличия строки добавления нового объекта
     * @param newObject    Если isNewRowActive == true, то должен содержать объект типа T, который будет выводиться в строке добавления. Иначе может быть = null
     */
    TableManager(@NotNull TableView<T> tableView, NewRowStatus newRowStatus, T newObject) {
        this.tableView = tableView;

        this.isNewRowActive = newRowStatus == NewRowStatus.ACTIVE;
        this.newObject = isNewRowActive ? newObject : null;
        isNewRowSelected = false;

        setChangeListener((observable, oldValue, newValue) -> {
        });

        if (isNewRowActive)
            tableView.setRowFactory((TableView<T> param) -> new TableRow<T>() {
                @Override
                protected void updateItem(T row, boolean paramBoolean) {
                    if (getIndex() == tableView.getItems().size() - 1)
                        setStyle("-fx-background-color: green;");
                    else
                        setStyle("");

                    super.updateItem(row, paramBoolean);
                }
            });
    }

    /**
     * Добавляет в таблицу столбец с заданной фабникой значений
     *
     * @param title        Заголовок столбца
     * @param <B>          Тип выводимого значения
     * @param type         Класс выводимого значения
     * @param TtoBCallback Лямбда-выражение, преобразующее объект класса T в объект типа B
     */
    <B> void addColumn(String title,
                       Class<B> type,
                       Callback<T, Object> TtoBCallback) {
        TableColumn<T, B> column = new TableColumn<>(title);
        column.setCellValueFactory(
                cellData -> cellData.getValue() == null ? null : new SimpleObjectProperty<>((B) TtoBCallback.call(cellData.getValue())));
        tableView.getColumns().add(column);
    }

    /**
     * Устанавливает список выводимых в таблице объектов
     *
     * @param items Список выводимых объектов
     */
    void setItems(List<T> items) {
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableArrayList(items));
        if (isNewRowActive)
            tableView.getItems().add(newObject);
    }

    /**
     * Получение выделенного в таблице объекта
     */
    T getSelectedItem() {
        return tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
    }

    /**
     * Установка обработчика изменения выделенной строки
     */
    void setChangeListener(ChangeListener<? super T> changeListener) {
        this.changeListener = changeListener;
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            isNewRowSelected = (isNewRowActive && newValue == newObject);

            changeListener.changed(observable, oldValue, newValue);
        });
    }

    public boolean isNewRowSelected() {
        return isNewRowSelected;
    }
}
