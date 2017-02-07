package gui;


import cathedra.Cathedra;
import cathedra.CathedraImpl;
import cathedra.CathedraManager;
import connection.MessageBuilder;
import faculty.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mvc.Controller;

import java.util.stream.Collectors;

public class CathedraPane extends ContentPane {

    public TableView<Cathedra> tableView;
    public TableColumn<Cathedra, String> cathedraNameColumn;
    public TableColumn<Cathedra, Integer> facultyNumberColumn;
    public ComboBox<Integer> facultyComboBox;
    public TextField nameCathedraBox;

    public CathedraPane() {super();}

    @Override
    public void update() {
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(MainForm.getMainForm().getCathedraList()));
        facultyComboBox.getItems().addAll(FXCollections.observableArrayList(MainForm.getMainForm().getFacultyList().stream()
        .map(s->s.getNumber()).collect(Collectors.toList())));

        cathedraNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        facultyNumberColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getFacultyIndex()).asObject());
    }

    public void addCathedra(){
        MessageBuilder messageBuilder = new MessageBuilder();

        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "CATHEDRA_ADD");
        Cathedra newCathedra=CathedraManager.getInstance().createObject();
        newCathedra.setName(nameCathedraBox.getText());
        newCathedra.setFacultyIndex(facultyComboBox.getValue().intValue());
        messageBuilder.put("data", newCathedra.getJSONObject() );
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        update();

    }

}
