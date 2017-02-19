package gui;


import cathedra.Cathedra;
import cathedra.UnregistredCathedra;
import connection.MessageBuilder;
import faculty.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import mvc.Controller;

public class CathedraPane extends ContentPane {

    public TableView<Cathedra> tableView;
    public TableColumn<Cathedra, String> cathedraNameColumn;
    public TableColumn<Cathedra, Integer> facultyNumberColumn;
    public ComboBox<Faculty> facultyComboBox;
    public TextField nameCathedraField;
    public Button addButton;
    public Button deleteButton;

    public CathedraPane() {
        super();
    }

    @Override
    public void update() {
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(MainForm.getMainForm().getCathedraList()));

        facultyComboBox.getItems().addAll(FXCollections.observableArrayList(MainForm.getMainForm().getFacultyList()));
        facultyComboBox.setCellFactory(new Callback<ListView<Faculty>, ListCell<Faculty>>() {
            @Override
            public ListCell<Faculty> call(ListView<Faculty> param) {

                final ListCell<Faculty> cell = new ListCell<Faculty>() {

                    @Override
                    protected void updateItem(Faculty faculty, boolean bln) {
                        super.updateItem(faculty, bln);

                        if (faculty != null) {
                            setText(Integer.toString(faculty.getNumber()));
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        cathedraNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        //TODO: Вместо индекса факультета выводить номер
        facultyNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFacultyIndex()).asObject());
    }

    @Override
    public void load() {
        addButton.setOnMouseClicked(event -> {
            if (nameCathedraField.getLength() == 0) {
                //TODO: уведомление о необходимости ввести название кафедры
                System.out.println("Напишите название новой кафедры");
                return;
            }

            if (facultyComboBox.getValue() == null) {
                //TODO: уведомление о необходимости выбрать факультет
                System.out.println("Выберите факультет");
                return;
            }

            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "CATHEDRA_ADD");
            Cathedra newCathedra = new UnregistredCathedra(0);
            newCathedra.setName(nameCathedraField.getText());
            newCathedra.setFacultyIndex(facultyComboBox.getValue().getIndex());
            messageBuilder.put("data", newCathedra.getJSONObject());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });


        deleteButton.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1) {
                //TODO: уведомление о необходимости выбора удаляемой кафедры
                System.out.println("Выберите кафедру для удаления.");
                return;
            }
            Cathedra cathedra = tableView.getSelectionModel().getSelectedItems().get(0);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "CATHEDRA_REMOVE");
            messageBuilder.put("index", cathedra.getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });

    }

}
