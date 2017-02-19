package gui;


import account.Account;
import connection.MessageBuilder;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mvc.Controller;
import org.json.simple.JSONObject;


public class FacultyPane extends ContentPane {
    public TableView<Faculty> tableView;
    public TextField test;
    public Button deleteButton;
    public ComboBox deanComboBox;
    public Button acceptButton;
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
        tableManager.setChangeListener(
                (observable, oldValue, newValue) -> showFacultyDetails(newValue)
        );


        deleteButton.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedIndex() == -1) {
                //TODO: уведомление о необходимости выбора удаляемого факультета
                System.out.println("Выберите факультет для удаления.");
                return;
            }

            Faculty faculty = tableView.getSelectionModel().getSelectedItems().get(0);
            MessageBuilder messageBuilder = new MessageBuilder();
            messageBuilder.setConnectionIndex(0);
            messageBuilder.put("type", "FACULTY_REMOVE");
            messageBuilder.put("index", faculty.getIndex());
            Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        });


        acceptButton.setOnMouseClicked(event -> {
            if (tableManager.isNewRowSelected()) {
                Faculty faculty = new UnregistredFaculty(0);
                faculty.setNumber(Integer.parseInt(test.getText()));
                faculty.setDeanAccountIndex(7);

                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.setConnectionIndex(0);
                messageBuilder.put("type", "FACULTY_ADD");
                messageBuilder.put("data", faculty.getJSONObject());
                Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
            } else {
                Faculty selectedFaculty = tableManager.getSelectedItem();
                Faculty faculty = new UnregistredFaculty(selectedFaculty.getIndex());
                faculty.setNumber(Integer.parseInt(test.getText()));
                faculty.setDeanAccountIndex(7);

                MessageBuilder messageBuilder = new MessageBuilder();
                messageBuilder.setConnectionIndex(0);
                messageBuilder.put("type", "FACULTY_EDIT");
                messageBuilder.put("data", faculty.getJSONObject());
                Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
            }
        });
    }

    private void showFacultyDetails(Faculty newValue) {
        if (newValue != null) {
            test.setText("" + newValue.getNumber());
            deanComboBox.getItems().clear();
            deanComboBox.getItems().add(newValue.getDeanAccountIndex());
        }
    }

    @Override
    public void update() {
        tableManager.setItems(MainForm.getMainForm().getFacultyList());
    }
}