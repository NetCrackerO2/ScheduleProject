package gui;


import connection.MessageBuilder;
import javafx.scene.control.Button;
import javafx.util.Callback;
import manager.Entity;
import mvc.Controller;
import org.json.simple.JSONObject;

import java.util.List;


public class PaneManager<T extends Entity> {
    private String commandTitle;
    private List<T> source;

    private TableManager<T> tableManager;
    private Button acceptButton;
    private Button deleteButton;

    private T oldSelectedEntity;
    private T enteredEntity;
    private Callback<Void, T> makeObjectAction;
    private Callback<T, Void> makeOutObjectAction;


    public PaneManager(TableManager<T> tableManager, String commandTitle) {
        this.tableManager = tableManager;
        this.commandTitle = commandTitle;
    }

    public void setAcceptButton(Button acceptButton) {
        this.acceptButton = acceptButton;

        this.acceptButton.setOnMouseClicked(event -> {
            if (tableManager.getSelectedItem() == null) {
                //TODO: лог
                System.out.println("Не выделено объекта для добавления/редактирования.");
                return;
            }

            String commandName = commandTitle;
            JSONObject object = makeObjectAction.call(null).getJSONObject();
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);

            if (tableManager.isNewRowSelected()) {
                commandName += "_ADD";
            } else {
                object.remove("index");
                object.put("index", tableManager.getSelectedItem().getIndex());
                commandName += "_EDIT";
            }

            messageBuilder.put("type", commandName);
            messageBuilder.put("data", object);
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });
    }

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
                //TODO: лог
                System.out.println("Не выделен объект для удаления.");
        });
    }

    private void saveCondition() {
        oldSelectedEntity = tableManager.getSelectedItem();
        enteredEntity = makeObjectAction.call(null);
    }

    private void loadCondition() {
        if (oldSelectedEntity == null)
            return;
        if (source.stream().noneMatch((entity) -> entity.getIndex() == oldSelectedEntity.getIndex()))
            return;

        Entity selectedEntity = source.stream().filter(
                (entity) -> entity.getIndex() == oldSelectedEntity.getIndex()
        ).findFirst().get();

        tableManager.setSelectedIndex(source.indexOf(selectedEntity));

        makeOutObjectAction.call(enteredEntity);
    }

    public void setBlaBlaObjectAction(Callback<Void, T> makeObjectAction,
                                      Callback<T, Void> makeOutObjectAction) {
        this.makeObjectAction = makeObjectAction;
        this.makeOutObjectAction = makeOutObjectAction;
    }

    public void setSource(List<T> source) {
        saveCondition();

        this.source = source;
        tableManager.setItems(source);

        loadCondition();
    }
}
