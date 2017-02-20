package gui;


import account.Account;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.util.Objects;


public class FacultyPane extends ContentPane {
    public TableView<Faculty> tableView;
    public TextField test;
    public Button deleteButton;
    public ComboBox deanComboBox;
    public Button acceptButton;

    private PaneManager<Faculty> paneManager;
    private TableManager<Faculty> tableManager;

    public FacultyPane() {
        super();
    }

    @Override
    public void load() {
        tableManager = new TableManager<>(
                tableView,
                NewRowStatus.ACTIVE,
                new Faculty() {
                    @Override
                    public int getIndex() {
                        return -1;
                    }

                    @Override
                    public int getNumber() {
                        return -1;
                    }

                    @Override
                    public void setNumber(int number) {
                    }

                    @Override
                    public int getDeanAccountIndex() {
                        return -1;
                    }

                    @Override
                    public void setDeanAccountIndex(int deanAccountIndex) {
                    }

                    @Override
                    public JSONObject getJSONObject() {
                        return null;
                    }
                }
        );
        tableManager.addColumn(
                "Номер",
                Integer.class,
                faculty -> faculty.getNumber()
        );
        tableManager.addColumn(
                "Декан",
                String.class,
                faculty -> {
                    String deanName;
                    Account deanAccount;
                    int deanAccountIndex = faculty.getDeanAccountIndex();

                    if (deanAccountIndex == -1)
                        deanName = "Не задан";
                    else {
                        deanAccount = MainForm.getMainForm().getAccountList().stream().filter(
                                object -> object.getIndex() == deanAccountIndex
                        ).findFirst().get();
                        deanName = deanAccount.getName();
                    }

                    return deanName;
                }
        );
        tableManager.setOnSelectListener(
                (observable, oldValue, newValue) -> showFacultyDetails(newValue)
        );


        paneManager = new PaneManager<>(tableManager, "FACULTY");
        paneManager.setAcceptButton(acceptButton);
        paneManager.setDeleteButton(deleteButton);

        paneManager.setBlaBlaObjectAction((nothing) -> {
            UnregistredFaculty faculty = new UnregistredFaculty(-1);

            String numberString = test.getText();
            int number = Objects.equals(numberString, "") ? 0 : Integer.parseInt(numberString);
            faculty.setNumber(number);
            //TODO: ДОДЕЛАТЬ!!
            faculty.setDeanAccountIndex(7);

            return faculty;
        }, (faculty) -> {
            test.setText(Integer.toString(faculty.getNumber()));
            //TODO: ДОДЕЛАТЬ!!
            deanComboBox.getItems().add(7);

            return null;
        });
    }

    private void showFacultyDetails(Faculty newValue) {
        deanComboBox.getItems().clear();
        test.setText("");
        if (newValue != null) {
            test.setText("" + newValue.getNumber());
            deanComboBox.getItems().add(newValue.getDeanAccountIndex());
        }
    }

    @Override
    public void update() {
        paneManager.setSource(MainForm.getMainForm().getFacultyList());
    }
}