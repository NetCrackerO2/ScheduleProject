package gui;


import account.Account;
import connection.MessageBuilder;
import faculty.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import mvc.Controller;
import org.json.simple.JSONObject;


public class FacultyPane extends ContentPane {

    public TableView<Faculty> tableView;
    public TableColumn<Faculty, Integer> numberColumn;
    public TableColumn<Faculty, String> deanColumn;
    public TextField test;
    public Button deleteButton;
    public ComboBox deanComboBox;
    public Button acceptButton;

    public FacultyPane() {
        super();
    }

    @Override
    public void load() {
        // Обработчик изменения выделения в таблице
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showFacultyDetails(newValue));

        deleteButton.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1) {
                //TODO: уведомление о необходимости выбора удаляемого факультета
                System.out.println("Выберите факультет для удаления.");
                return;
            }

            Faculty faculty = tableView.getSelectionModel().getSelectedItems().get(0);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "FACULTY_REMOVE");
            messageBuilder.put("index", faculty.getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });

        acceptButton.setOnMouseClicked(event -> {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("index", 0);
            jsonObject.put("number", Integer.parseInt(test.getText()));
            jsonObject.put("deanAccountIndex", 7);

            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "FACULTY_ADD");
            messageBuilder.put("data", jsonObject);
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });
    }

    private void showFacultyDetails(Faculty newValue) {
        if (newValue != null) {
            test.setText("" + newValue.getNumber());
            deanComboBox.getItems().clear();
            deanComboBox.getItems().add(deanColumn.getCellData(newValue));
        }
    }

    @Override
    public void update() {
        tableView.getItems().clear();

        tableView.setItems(FXCollections.observableArrayList(MainForm.getMainForm().getFacultyList()));

        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()).asObject());
        deanColumn.setCellValueFactory(cellData -> {
            String deanName;
            Account deanAccount;
            int deanAccountIndex = cellData.getValue().getDeanAccountIndex();

            if (deanAccountIndex == -1)
                deanName = "Не задан";
            else {
                deanAccount = MainForm.getMainForm().getAccountList().stream().filter(
                        object -> object.getIndex() == deanAccountIndex
                ).findFirst().get();
                deanName = deanAccount.getName();
            }

            return new SimpleStringProperty(deanName);
        });
    }
}