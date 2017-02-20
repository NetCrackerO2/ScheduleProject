package gui;

import cathedra.Cathedra;
import cathedra.UnregistredCathedra;
import group.Group;
import group.UnregistredGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Objects;

public class GroupPane extends PaneManager<Group> {
    public TableView<Group> tableView;

    public TextField numberTextField;
    public TextField professionCodeTextField;
    public TextField receiptYearTextField;
    public ComboBox<Cathedra> cathedraComboBox;
    public Button acceptButton;
    public Button deleteButton;
    public Cathedra defaultCathedra = new UnregistredCathedra(-1);

    public GroupPane() {
        super("GROUP");
        defaultCathedra.setName("Не задана");
    }

    @Override
    public void load() {
        TableManager<Group> tableManager = new TableManager<>(tableView, NewRowStatus.ACTIVE, new UnregistredGroup(-1));
        tableManager.addColumn("Номер", Integer.class, group -> group.getNumber());
        tableManager.addColumn("Кафедра", String.class, group -> {
            int cathedraIndex = group.getCathedraIndex();

            if (cathedraIndex == -1)
                return defaultCathedra.toString();
            return MainForm.getMainForm().getCathedraList().stream()
                    .filter(cathedra -> cathedra.getIndex() == cathedraIndex).findFirst().get().toString();
        });
        tableManager.addColumn("Код профессии", Integer.class, group -> group.getProfessionCode());
        tableManager.addColumn("Год создания", Integer.class, group -> group.getReceiptYear());

        setTableManager(tableManager);
        setAcceptButton(acceptButton);
        setDeleteButton(deleteButton);
    }

    @Override
    public void update() {
        setSource(MainForm.getMainForm().getGroupList());
    }

    @Override
    protected Group onConfirm() {
        UnregistredGroup group = new UnregistredGroup(-1);

        String numberString = numberTextField.getText();
        int number = Objects.equals(numberString, "") ? -1 : Integer.parseInt(numberString);
        group.setNumber(number);

        group.setCathedraIndex(cathedraComboBox.getValue().getIndex());

        String professionCodeString = professionCodeTextField.getText();
        int professionCode = Objects.equals(professionCodeString, "") ? -1 : Integer.parseInt(professionCodeString);
        group.setProfessionCode(professionCode);

        String receiptYearString = receiptYearTextField.getText();
        int receiptYear = Objects.equals(receiptYearString, "") ? -1 : Integer.parseInt(receiptYearString);
        group.setReceiptYear(receiptYear);

        return group;
    }

    @Override
    protected void onShowDetails(Group object) {
        numberTextField.setText("");
        cathedraComboBox.getItems().clear();

        if (object != null) {
            numberTextField.setText(Integer.toString(object.getNumber()));

            this.updateComboBox(cathedraComboBox, object.getCathedraIndex(), defaultCathedra,
                    MainForm.getMainForm().getCathedraList());

            professionCodeTextField.setText(Integer.toString(object.getProfessionCode()));

            receiptYearTextField.setText(Integer.toString(object.getReceiptYear()));
        }
    }
}
