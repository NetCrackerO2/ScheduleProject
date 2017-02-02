package gui;


import connection.MessageBuilder;
import faculty.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mvc.Controller;


public class FacultyPane extends ContentPane {

    public TableView<Faculty> tableView;
    public TableColumn<Faculty, Integer> numberColumn;
    public TableColumn<Faculty, Integer> deanColumn;
    public TextField test;

    public FacultyPane() {
        super();
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "FACULTY_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    @Override
    public void update() {
        tableView.getItems().clear();

        tableView.setItems(FXCollections.observableArrayList(MainForm.getMainForm().getFacultyList()));

        numberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber()).asObject());
        deanColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDeanAccountIndex()).asObject());
    }
}
