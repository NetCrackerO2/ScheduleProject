package gui;

import connection.MessageBuilder;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import manager.Entity;
import mvc.Controller;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class PaneManager<T extends Entity> extends ContentPane {
    private String commandTitle;
    private TableManager<T> tableManager;

    private Button acceptButton;
    private Button deleteButton;

    public PaneManager(String commandTitle) {
        this.commandTitle = commandTitle;
    }

    public void setTableManager(TableManager<T> tableManager) {
        this.tableManager = tableManager;
        this.tableManager.setOnSelectListener((a, oldValue, newValue) -> onShowDetails(newValue));
    }

    /**
     * Устанавливает обработчик изменения/добавления записи на кнопку
     *
     * @param acceptButton
     *            Объект кнопки "Применить"
     */
    public void setAcceptButton(Button acceptButton) {
        this.acceptButton = acceptButton;

        this.acceptButton.setOnMouseClicked(event -> {
            if (tableManager.getSelectedItem() == null) {
                // TODO: лог
                System.out.println("Не выделено объекта для добавления/редактирования.");
                return;
            }

            String commandName = commandTitle;
            T object = onConfirm();
            if (object == null)
                return;
            JSONObject jsonObject = object.getJSONObject();
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);

            if (tableManager.isNewRowSelected()) {
                commandName += "_ADD";
            } else {
                jsonObject.remove("index");
                jsonObject.put("index", tableManager.getSelectedItem().getIndex());
                commandName += "_EDIT";
            }

            messageBuilder.put("type", commandName);
            messageBuilder.put("data", jsonObject);
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });
    }

    /**
     * Устанавливает обработчик удаления записи на кнопку
     *
     * @param deleteButton
     *            Объект кнопки "Удалить"
     */
    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;

        this.deleteButton.setOnMouseClicked(event -> {
            if (!tableManager.isNewRowSelected() && tableManager.getSelectedItem() != null) {
                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.setConnectionIndex(0);
                messageBuilder.put("type", commandTitle + "_REMOVE");
                messageBuilder.put("index", tableManager.getSelectedItem().getIndex());
                Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
            } else
                // TODO: лог
                System.out.println("Не выделен объект для удаления.");
        });
    }

    /**
     * Создание объекта на основе значений полей формы
     *
     * @return Объект, проинициализированный значениями полей формы Индекс
     *         объекта не важен (лучше -1)
     */
    protected abstract T onConfirm();

    /**
     * Вывод информации об объекте в поля формы
     *
     * @param object
     *            Объект, информация о котором будет выведена в поля формы
     */
    protected abstract void onShowDetails(T object);

    /**
     * Устанавливает источник объектов для формы
     *
     * @param source
     */
    public void setSource(List<T> source) {
        final T prevSelected = tableManager.getSelectedItem();

        tableManager.setItems(source);

        Optional<T> selected = source.stream()
                .filter(x -> prevSelected != null && x.getIndex() == prevSelected.getIndex()).findAny();

        if (prevSelected != null && selected.isPresent()) {
            onShowDetails(selected.get());
            tableManager.setSelectedIndex(source.indexOf(selected.get()));
        } else
            onShowDetails(null);
    }

    /**
     * Заполняет комбобокс учитывая значение по умолчанию(не установленное)
     * 
     * @param comboBox
     * @param selectedIndex
     * @param defaultObject
     * @param data
     */
    protected <T extends Entity> void updateComboBox(ComboBox<T> comboBox, int selectedIndex, T defaultObject,
            List<T> data) {
        if (selectedIndex >= 0)
            comboBox.getItems().add(data.stream().filter(x -> x.getIndex() == selectedIndex).findFirst().get());
        comboBox.getItems().add(defaultObject);
        comboBox.getItems()
                .addAll(data.stream().filter(x -> x.getIndex() != selectedIndex).collect(Collectors.toList()));
        comboBox.setValue(comboBox.getItems().get(0));
    }

    /**
     * Выбор элемента если он существует в data, возвращает defaultObject в
     * противном случае
     * 
     * @param data
     * @param selectedIndex
     * @param defaultObject
     * @return
     */
    protected <T extends Entity> T selectOrDefault(List<T> data, int selectedIndex, T defaultObject) {
        if (selectedIndex == -1)
            return defaultObject;
        return data.stream().filter(object -> object.getIndex() == selectedIndex).findFirst().get();
    }
}