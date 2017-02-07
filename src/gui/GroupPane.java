package gui;


import cathedra.Cathedra;
import connection.MessageBuilder;
import faculty.Faculty;
import group.Group;
import group.GroupManager;
import gui.ContentPane;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import mvc.Controller;

public class GroupPane extends ContentPane {
    public TableView<Group> tableView;
    public TableColumn<Group, Integer> numberGroupColumn;
    public TableColumn<Group, Integer> cathedraNameColumn;

    public TextField groupNumberField;
    public ComboBox<Cathedra> cathedraComboBox;

    public Button addButton;
    public Button deleteButton;

    @Override
    public void load() {
        addButton.setOnMouseClicked(event -> {
            if (groupNumberField.getLength() == 0) {
                //TODO: уведомление о необходимости ввести номер группы
                System.out.println("Напишите номер новой группы");
                return;
            }

            if (cathedraComboBox.getValue() == null) {
                //TODO: уведомление о необходимости выбрать кафедру
                System.out.println("Выберите кафедру");
                return;
            }

            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "CATHEDRA_ADD");
            Group newGroup = GroupManager.getInstance().createObject();
            newGroup.setNumber(Integer.parseInt(groupNumberField.getText()));
            newGroup.setCathedraIndex(cathedraComboBox.getValue().getIndex());
            messageBuilder.put("data", newGroup.getJSONObject());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });


        deleteButton.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1) {
                //TODO: уведомление о необходимости выбора удаляемой кафедры
                System.out.println("Выберите группу для удаления.");
                return;
            }
            Group group = tableView.getSelectionModel().getSelectedItems().get(0);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "GROUP_REMOVE");
            messageBuilder.put("index", group.getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });
    }

    @Override
    public void update() {
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(MainForm.getMainForm().getGroupList()));

        numberGroupColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getNumber()).asObject());
        //TODO: Вместо индекса кафедры выводить название
        cathedraNameColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getCathedraIndex()).asObject());

        cathedraComboBox.getItems().addAll(FXCollections.observableList(MainForm.getMainForm().getCathedraList()));
        cathedraComboBox.setCellFactory(new Callback<ListView<Cathedra>, ListCell<Cathedra>>() {

            @Override
            public ListCell<Cathedra> call(ListView<Cathedra> p) {

                final ListCell<Cathedra> cell = new ListCell<Cathedra>() {

                    @Override
                    protected void updateItem(Cathedra cathedra, boolean bln) {
                        super.updateItem(cathedra, bln);

                        if (cathedra != null) {
                            setText(cathedra.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }
}
