package gui;


import cathedra.Cathedra;
import group.Group;
import group.UnregistredGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.json.simple.JSONObject;

import java.util.Objects;


public class GroupPane extends PaneManager<Group> {
    public TableView<Group> tableView;

    public TextField numberTextField;
    public ComboBox<Cathedra> cathedraComboBox;
    public Button acceptButton;
    public Button deleteButton;

    public GroupPane() {
        super("GROUP");
    }

    @Override
    public void load() {
        TableManager<Group> tableManager = new TableManager<>(tableView, NewRowStatus.ACTIVE, new Group() {
            @Override
            public int getIndex() {
                return -1;
            }

            @Override
            public int getCathedraIndex() {
                return -1;
            }

            @Override
            public void setCathedraIndex(int cathedraIndex) {
            }

            @Override
            public int getNumber() {
                return -1;
            }

            @Override
            public void setNumber(int number) {
            }

            @Override
            public int getProfessionCode() {
                return -1;
            }

            @Override
            public void setProfessionCode(int professionCode) {
            }

            @Override
            public int getReceiptYear() {
                return -1;
            }

            @Override
            public void setReceiptYear(int year) {
            }

            @Override
            public JSONObject getJSONObject() {
                return null;
            }
        });
        tableManager.addColumn("Номер", Integer.class, group -> group.getNumber());
        tableManager.addColumn("Кафедра", String.class, group -> {
            int cathedraIndex = group.getCathedraIndex();

            if (cathedraIndex == -1)
                return "Не задана";
            return MainForm.getMainForm().getCathedraList().stream().filter(
                    cathedra -> cathedra.getIndex() == cathedraIndex
            ).findFirst().get().getName();
        });
        tableManager.addColumn("Код профессии", Integer.class, group -> group.getProfessionCode());
        tableManager.addColumn("Год создания", Integer.class, group -> group.getReceiptYear());

        setTableManager(tableManager);
        setAcceptButton(acceptButton);
        setDeleteButton(deleteButton);

        cathedraComboBox.setConverter(new StringConverter<Cathedra>() {
            @Override
            public String toString(Cathedra object) {
                return object.getName();
            }

            @Override
            public Cathedra fromString(String string) {
                return cathedraComboBox.getItems().stream().filter(cathedra -> cathedra.getName() == string).findFirst().get();
            }
        });
    }

    @Override
    public void update() {
        setSource(MainForm.getMainForm().getGroupList());
        cathedraComboBox.getItems().setAll(MainForm.getMainForm().getCathedraList());
    }

    @Override
    protected Group onConfirm() {
        UnregistredGroup group = new UnregistredGroup(-1);

        String numberString = numberTextField.getText();
        int number = Objects.equals(numberString, "") ? 0 : Integer.parseInt(numberString);
        group.setNumber(number);

        group.setCathedraIndex(cathedraComboBox.getSelectionModel().getSelectedItem().getIndex());

        return group;
    }

    @Override
    protected void onShowDetails(Group object) {
        numberTextField.setText("");
        cathedraComboBox.getSelectionModel().select(-1);

        if (object != null) {
            numberTextField.setText(Integer.toString(object.getNumber()));
            if (object.getCathedraIndex() == -1)
                cathedraComboBox.getSelectionModel().select(-1);
            else
                cathedraComboBox.getSelectionModel().select(cathedraComboBox.getItems().stream().filter(
                        cathedra -> cathedra.getIndex() == object.getCathedraIndex()
                ).findFirst().get());
        }
    }
}
