package gui;

import account.Account;
import account.UnregistredAccount;
import cathedra.Cathedra;
import cathedra.UnregistredCathedra;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import javafx.scene.control.*;

public class CathedraPane extends PaneManager<Cathedra> {

    public TableView<Cathedra> tableView;
    public TableColumn<Cathedra, String> cathedraNameColumn;
    public TableColumn<Cathedra, Integer> facultyNumberColumn;
    public ComboBox<Faculty> facultyComboBox;
    public ComboBox<Account> headComboBox;
    public TextField nameCathedraField;
    public Button addButton;
    public Button deleteButton;
    private Faculty defaultFaculty = new UnregistredFaculty(-1);
    private Account defaultHead = new UnregistredAccount(-1);

    public CathedraPane() {
        super("CATHEDRA");
        defaultFaculty.setNumber(-1);
        defaultHead.setName("Не задано");
    }

    @Override
    public void update() {
        this.setSource(MainForm.getMainForm().getCathedraList());
    }

    @Override
    public void load() {
        TableManager<Cathedra> tableManager = new TableManager<Cathedra>(tableView, NewRowStatus.ACTIVE,
                new UnregistredCathedra(-1));
        tableManager.addColumn("Название", String.class, x -> x.getName());
        tableManager.addColumn("Факультет", String.class,
                x -> selectOrDefault(MainForm.getMainForm().getFacultyList(), x.getFacultyIndex(), defaultFaculty)
                        .toString());
        tableManager.addColumn("Заведущий", String.class,
                x -> selectOrDefault(MainForm.getMainForm().getAccountList(), x.getHeadAccountIndex(), defaultHead)
                        .toString());

        setTableManager(tableManager);
        this.setAcceptButton(addButton);
        this.setDeleteButton(deleteButton);
    }

    @Override
    protected Cathedra onConfirm() {
        if (nameCathedraField.getLength() == 0) {
            System.out.println("Напишите название новой кафедры");
            return null;
        }
        if (facultyComboBox.getValue() == null) {
            System.out.println("Выберите факультет");
            return null;
        }
        if (headComboBox.getValue() == null) {
            System.out.println("Выберите заведущего");
            return null;
        }
        UnregistredCathedra cathedra = new UnregistredCathedra(-1);
        cathedra.setName(nameCathedraField.getText());
        cathedra.setFacultyIndex(facultyComboBox.getValue().getIndex());
        cathedra.setHeadAccountIndex(headComboBox.getValue().getIndex());
        return cathedra;
    }

    @Override
    protected void onShowDetails(Cathedra object) {
        headComboBox.getItems().clear();
        facultyComboBox.getItems().clear();
        nameCathedraField.setText("");

        if (object != null) {
            nameCathedraField.setText(object.getName());
            updateComboBox(facultyComboBox, object.getFacultyIndex(), defaultFaculty,
                    MainForm.getMainForm().getFacultyList());
            updateComboBox(headComboBox, object.getHeadAccountIndex(), defaultHead,
                    MainForm.getMainForm().getAccountList());
        }
    }

}
