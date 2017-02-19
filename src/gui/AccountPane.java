package gui;


import account.Account;
import account.UnregistredAccount;
import cathedra.Cathedra;
import connection.MessageBuilder;
import group.Group;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import mvc.Controller;



public class AccountPane extends ContentPane {
    //TODO: Добавить в класс Account поля имя,фамилия, пол, дата и исправить AccountPane
    public TableView<Account> tableView;
    public TableColumn<Account,String> nameColumn;
    public TableColumn<Account,Integer> cathedraColumn;
    public TableColumn<Account,Integer> groupColumn;

    public TextField nameField;
    public ComboBox<Cathedra> cathedraComboBox;
    public ComboBox<Group> groupComboBox;

    public Button addButton;
    public Button deleteButton;


    @Override
    public void load() {
        addButton.setOnMouseClicked(event -> {
            if (nameField.getLength()==0){
                //TODO: уведомление о необходимости ввести фамилию пользователя
                System.out.println("Напишите фамилию пользователя");
                return;
            }
            if (cathedraComboBox.getValue()==null){
                //TODO: уведомление о необходимости выбрать кафедру
                System.out.println("Напишите фамилию пользователя");
                return;
            }
            if (groupComboBox.getValue()==null){
                //TODO: уведомление о необходимости выбрать группу
                System.out.println("Выберите группу");
                return;
            }
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "ACCOUNT_ADD");
            Account newAccount= new UnregistredAccount(0);

            newAccount.setName(nameField.getText());
            newAccount.setCathedraIndex(cathedraComboBox.getValue().getIndex());
            newAccount.setGroupIndex(groupComboBox.getValue().getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });

        deleteButton.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1) {
                //TODO: уведомление о необходимости выбора удаляемой кафедры
                System.out.println("Выберите пользователя для удаления.");
                return;
            }
            Account account = tableView.getSelectionModel().getSelectedItems().get(0);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "ACCOUNT_REMOVE");
            messageBuilder.put("index", account.getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });
    }

    @Override
    public void update() {
        tableView.getItems().clear();
        tableView.setItems(FXCollections.observableList(MainForm.getMainForm().getAccountList()));
        cathedraComboBox.getItems().addAll(FXCollections.observableList(MainForm.getMainForm().getCathedraList()));
        cathedraComboBox.setCellFactory(new Callback<ListView<Cathedra>,ListCell<Cathedra>>(){

            @Override
            public ListCell<Cathedra> call(ListView<Cathedra> p) {

                final ListCell<Cathedra> cell = new ListCell<Cathedra>(){

                    @Override
                    protected void updateItem(Cathedra cathedra, boolean bln) {
                        super.updateItem(cathedra, bln);

                        if(cathedra != null){
                            setText(cathedra.getName());
                        }else{
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        groupComboBox.getItems().addAll(FXCollections.observableList(MainForm.getMainForm().getGroupList()));
        groupComboBox.setCellFactory(new Callback<ListView<Group>,ListCell<Group>>(){

            @Override
            public ListCell<Group> call(ListView<Group> p) {

                final ListCell<Group> cell = new ListCell<Group>(){

                    @Override
                    protected void updateItem(Group group, boolean bln) {
                        super.updateItem(group, bln);

                        if(group != null){
                            setText(Integer.toString(group.getNumber()));
                        }else{
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        nameColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getName()));
        //TODO: Вместо индекса кафедры выводить название
        cathedraColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getCathedraIndex()).asObject());
        //TODO: Вместо индекса группы выыводить номер
        groupColumn.setCellValueFactory(cellData->new SimpleIntegerProperty(cellData.getValue().getGroupIndex()).asObject());



}
}
