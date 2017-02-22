package gui;

import account.Account;
import account.UnregistredAccount;
import cathedra.Cathedra;
import cathedra.UnregistredCathedra;
import group.Group;
import group.UnregistredGroup;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AccountPane extends PaneManager<Account> {

    public AccountPane() {
        super("ACCOUNT");
        defaultCathedra.setName("Не задано");
        defaultGroup.setNumber(-1);
    }

    public TableView<Account> tableView;
    public TextField nameField;
    public ComboBox<Cathedra> cathedraComboBox;
    public ComboBox<Group> groupComboBox;

    public Button addButton;
    public Button deleteButton;

    Cathedra defaultCathedra = new UnregistredCathedra(-1);
    Group defaultGroup = new UnregistredGroup(-1);

    @Override
    public void load() {
        TableManager<Account> tableManager = new TableManager<Account>(tableView, NewRowStatus.ACTIVE,
                new UnregistredAccount(-1));
        tableManager.addColumn("Фамилия", String.class, account -> account.getName());
        tableManager.addColumn("Кафедра", String.class, account -> {
            if (account.getCathedraIndex() < 0)
                return defaultCathedra.toString();
            return MainForm.getMainForm().getCathedraList().stream()
                    .filter(object -> object.getIndex() == account.getCathedraIndex()).findFirst().get().toString();
        });
        tableManager.addColumn("Группа", String.class,
                x -> selectOrDefault(MainForm.getMainForm().getGroupList(), x.getGroupIndex(), defaultGroup)
                        .toString());

        setTableManager(tableManager);
        setAcceptButton(addButton);
        setDeleteButton(deleteButton);
    }

    @Override
    public void update() {
        setSource(MainForm.getMainForm().getAccountList());
    }

    @Override
    protected Account onConfirm() {
        if (nameField.getLength() == 0) {
            // TODO: уведомление о необходимости ввести фамилию пользователя
            System.out.println("Напишите фамилию пользователя");
            return null;
        }
        if (cathedraComboBox.getValue() == null) {
            // TODO: уведомление о необходимости выбрать кафедру
            System.out.println("Выберите кафедру пользователя");
            return null;
        }
        if (groupComboBox.getValue() == null) {
            // TODO: уведомление о необходимости выбрать группу
            System.out.println("Выберите группу");
            return null;
        }
        Account newAccount = new UnregistredAccount(0);
        newAccount.setName(nameField.getText());
        newAccount.setCathedraIndex(cathedraComboBox.getValue().getIndex());
        newAccount.setGroupIndex(groupComboBox.getValue().getIndex());
        return newAccount;
    }

    @Override
    protected void onShowDetails(Account object) {
        nameField.setText("");
        cathedraComboBox.getItems().clear();
        groupComboBox.getItems().clear();
        if (object != null) {
            nameField.setText(object.getName());

            this.updateComboBox(cathedraComboBox, object.getCathedraIndex(), defaultCathedra,
                    MainForm.getMainForm().getCathedraList());
            this.updateComboBox(groupComboBox, object.getGroupIndex(), defaultGroup,
                    MainForm.getMainForm().getGroupList());
        }
    }
}
