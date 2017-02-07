package gui;


import account.Account;
import account.role.Role;
import cathedra.Cathedra;
import connection.MessageBuilder;
import faculty.Faculty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import mvc.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainForm {
    private static MainForm mainForm;

    @FXML
    Pane framePane;

    private List<Account> accountList;
    private List<Role> roleList;
    private List<Cathedra> cathedraList;
    private List<Faculty> facultyList;
    private List<Group> groupList;

    private boolean accountsUpdated;
    //private boolean rolesUpdated;
    //private boolean cathedrasUpdated;
    private boolean facultyesUpdated;
    //private boolean groupsUpdated;

    private ContentPane currentContentPane;


    public MainForm() {
        mainForm = this;

        accountList = new ArrayList<>();
        //roleList = new ArrayList<>();
        //cathedraList = new ArrayList<>();
        facultyList = new ArrayList<>();
        //groupList = new ArrayList<>();
    }

    public static MainForm getMainForm() {
        return mainForm;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
        accountsUpdated = true;
        updateContentPane();
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
        updateContentPane();
    }

    public List<Cathedra> getCathedraList() {
        return cathedraList;
    }

    public void setCathedraList(List<Cathedra> cathedraList) {
        this.cathedraList = cathedraList;
        updateContentPane();
    }

    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
        facultyesUpdated = true;
        updateContentPane();
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
        updateContentPane();
    }


    public void setCurrentContentPane(ContentPane currentContentPane) {
        this.currentContentPane = currentContentPane;
    }


    public void showFaculty() {
        showPane("Faculty");
    }

    public void showCathedra() {
        showPane("Cathedra");
    }

    public void showGroup() {
        showPane("Group");
    }

    public void showAccount() {
        showPane("Account");
    }

    public void showRole() {
        showPane("Role");
    }


    private void showPane(String string) {
        try {
            Node node = FXMLLoader.load(this.getClass().getResource("forms/" + string + ".fxml"));
            this.framePane.getChildren().setAll(node);
            currentContentPane.load();
            updateContentPane();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateAllData() {
        accountsUpdated = false;
        //rolesUpdated = false;
        //cathedrasUpdated = false;
        facultyesUpdated = false;
        //groupsUpdated = false;

        MessageBuilder messageBuilder = new MessageBuilder();

        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "FACULTY_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());

        messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "ACCOUNT_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }


    private void updateContentPane() {
        if (currentContentPane == null)
            return;

        if (accountsUpdated
                //&& rolesUpdated
                //&& cathedrasUpdated
                && facultyesUpdated
            //&& groupsUpdated
                )
            currentContentPane.update();
    }
}
