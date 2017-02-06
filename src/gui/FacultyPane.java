package gui;


import account.Account;
import faculty.Faculty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class FacultyPane extends ContentPane {

    public TableView<Faculty> tableView;
    public TableColumn<Faculty, Integer> numberColumn;
    public TableColumn<Faculty, String> deanColumn;
    public TextField test;

    public FacultyPane() {
        super();
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
