package gui;

import java.util.stream.Collectors;

import account.Account;
import account.UnregistredAccount;
import cathedra.Cathedra;
import group.Group;
import javafx.scene.control.*;

public class AccountPane extends PaneManager<Account> {

    public AccountPane() {
        super("ACCOUNT");
    }

    public TableView<Account> tableView;
    public TextField nameField;
    public ComboBox<Cathedra> cathedraComboBox;
    public ComboBox<Group> groupComboBox;

    public Button addButton;
    public Button deleteButton;

    @Override
    public void load() {
        TableManager<Account> tableManager = new TableManager<Account>(tableView, NewRowStatus.ACTIVE,
                new UnregistredAccount(0));
        tableManager.addColumn("Фамилия", String.class, account -> account.getName());
        tableManager.addColumn("Кафедра", String.class, account -> {
            if (account.getCathedraIndex() < 0)
                return "Не задана";
            return MainForm.getMainForm().getCathedraList().stream()
                    .filter(object -> object.getIndex() == account.getCathedraIndex()).findFirst().get().toString();
        });
        tableManager.addColumn("Группа", String.class, account -> {
            if (account.getCathedraIndex() < 0)
                return "Не задана";
            return MainForm.getMainForm().getGroupList().stream()
                    .filter(object -> object.getIndex() == account.getGroupIndex()).findFirst().get().toString();
        });

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
            System.out.println("Напишите фамилию пользователя");
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
            if (object.getCathedraIndex() >= 0)
                cathedraComboBox.getItems().add(MainForm.getMainForm().getCathedraList().stream()
                        .filter(x -> x.getIndex() == object.getCathedraIndex()).findFirst().get());
            cathedraComboBox.getItems().addAll(MainForm.getMainForm().getCathedraList().stream()
                    .filter(x -> x.getIndex() != object.getCathedraIndex()).collect(Collectors.toList()));
            if (object.getCathedraIndex() >= 0)
                groupComboBox.getItems().add(MainForm.getMainForm().getGroupList().stream()
                        .filter(x -> x.getIndex() == object.getGroupIndex()).findFirst().get());
            groupComboBox.getItems().addAll(MainForm.getMainForm().getGroupList().stream()
                    .filter(x -> x.getIndex() != object.getGroupIndex()).collect(Collectors.toList()));
        }
    }
}
