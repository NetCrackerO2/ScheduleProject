package gui;

import account.Account;
import account.UnregistredAccount;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Objects;

public class FacultyPane extends PaneManager<Faculty> {
    public TableView<Faculty> tableView;
    public TextField numberTextField;
    public Button acceptButton;
    public Button deleteButton;
    public ComboBox<Account> deanComboBox;
    private Account defaultDean = new UnregistredAccount(-1);

    public FacultyPane() {
        super("FACULTY");
        defaultDean.setName("Не задан");
    }

    @Override
    public void load() {
        TableManager<Faculty> tableManager = new TableManager<Faculty>(tableView, NewRowStatus.ACTIVE,
                new UnregistredFaculty(-1));
        tableManager.addColumn("Номер", Integer.class, faculty -> faculty.getNumber());
        tableManager.addColumn("Декан", String.class,
                x -> selectOrDefault(MainForm.getMainForm().getAccountList(), x.getDeanAccountIndex(), defaultDean)
                        .toString());
        setTableManager(tableManager);
        setAcceptButton(acceptButton);
        setDeleteButton(deleteButton);
    }

    @Override
    public void update() {
        setSource(MainForm.getMainForm().getFacultyList());
    }

    @Override
    protected Faculty onConfirm() {
        UnregistredFaculty faculty = new UnregistredFaculty(-1);

        String numberString = numberTextField.getText();
        int number = Objects.equals(numberString, "") ? 0 : Integer.parseInt(numberString);
        faculty.setNumber(number);
        faculty.setDeanAccountIndex(deanComboBox.getValue().getIndex());

        return faculty;
    }

    @Override
    protected void onShowDetails(Faculty object) {
        deanComboBox.getItems().clear();
        numberTextField.setText("");

        if (object != null) {
            numberTextField.setText("" + object.getNumber());
            updateComboBox(deanComboBox, object.getDeanAccountIndex(), defaultDean,
                    MainForm.getMainForm().getAccountList());
        }
    }
}