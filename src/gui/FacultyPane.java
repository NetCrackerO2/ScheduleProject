package gui;


import account.Account;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Objects;


public class FacultyPane extends PaneManager<Faculty> {
    public TableView<Faculty> tableView;
    public TextField test;
    public Button deleteButton;
    public ComboBox deanComboBox;
    public Button acceptButton;

    public FacultyPane() {
        super("FACULTY");
    }

    @Override
    public void load() {
        TableManager<Faculty> tableManager = new TableManager<Faculty>(tableView, NewRowStatus.ACTIVE, new UnregistredFaculty(0));
        tableManager.addColumn("Номер", Integer.class, faculty -> faculty.getNumber());
        tableManager.addColumn("Декан", String.class, faculty -> {
            String deanName;
            Account deanAccount;
            int deanAccountIndex = faculty.getDeanAccountIndex();

            if (deanAccountIndex == -1)
                deanName = "Не задан";
            else {
                deanAccount = MainForm.getMainForm().getAccountList().stream()
                        .filter(object -> object.getIndex() == deanAccountIndex).findFirst().get();
                deanName = deanAccount.getName();
            }

            return deanName;
        });
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

        String numberString = test.getText();
        int number = Objects.equals(numberString, "") ? 0 : Integer.parseInt(numberString);
        faculty.setNumber(number);

        // TODO: ДОДЕЛАТЬ!!
        faculty.setDeanAccountIndex(7);

        return faculty;
    }

    @Override
    protected void onShowDetails(Faculty object) {
        deanComboBox.getItems().clear();
        test.setText("");

        if (object != null) {
            test.setText("" + object.getNumber());
            deanComboBox.getItems().add(object.getDeanAccountIndex());
        }
    }
}