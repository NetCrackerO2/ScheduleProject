package gui;


import account.Account;
import account.role.Role;
import account.role.RoleAssignment;
import cathedra.Cathedra;
import connection.MessageBuilder;
import faculty.Faculty;
import group.Group;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private List<RoleAssignment> roleAssignmentList;
    private List<Cathedra> cathedraList;
    private List<Faculty> facultyList;
    private List<Group> groupList;

    private boolean accountsUpdated;
    private boolean rolesUpdated;
    private boolean roleAssignmentsUpdated;
    private boolean cathedrasUpdated;
    private boolean facultyesUpdated;
    private boolean groupsUpdated;

    private ContentPane currentContentPane;

    public MainForm() {
        mainForm = this;

        accountList = new ArrayList<>();
        roleList = new ArrayList<>();
        roleAssignmentList = new ArrayList<>();
        cathedraList = new ArrayList<>();
        facultyList = new ArrayList<>();
        groupList = new ArrayList<>();
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
        rolesUpdated = true;
        updateContentPane();
    }

    public List<RoleAssignment> getRoleAssignmentList() {
        return roleAssignmentList;
    }

    public void setRoleAssignmentList(List<RoleAssignment> roleAssignmentList) {
        this.roleAssignmentList = roleAssignmentList;
        roleAssignmentsUpdated = true;
        updateContentPane();
    }

    public List<Cathedra> getCathedraList() {
        return cathedraList;
    }

    public void setCathedraList(List<Cathedra> cathedraList) {
        this.cathedraList = cathedraList;
        cathedrasUpdated = true;
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
        groupsUpdated = true;
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
        clearSearch();
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
        rolesUpdated = false;
        roleAssignmentsUpdated = false;
        cathedrasUpdated = false;
        facultyesUpdated = false;
        groupsUpdated = false;

        MessageBuilder messageBuilder = new MessageBuilder();

        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "FACULTY_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());

        // messageBuilder.initialize();
        // messageBuilder.setConnectionIndex(0);
        // messageBuilder.put("type", "ACCOUNT_LIST");
        // Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
        refreshSearch();

        messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "CATHEDRA_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());

        messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "GROUP_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());

        /*messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "ROLE_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());*/

        messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "ROLE_ASSIGNMENT_LIST");
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    private void updateContentPane() {
        if (currentContentPane == null)
            return;

        if (accountsUpdated
                // && rolesUpdated
                && roleAssignmentsUpdated && cathedrasUpdated && facultyesUpdated && groupsUpdated)
            if (!Platform.isFxApplicationThread())
                Platform.runLater(() -> currentContentPane.update());
            else
                currentContentPane.update();
    }

    private String searchNameQuery = "";
    private String searchCathedraQuery = "";
    private String searchGroupQuery = "";

    public void refreshSearch() {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.initialize();
        messageBuilder.setConnectionIndex(0);
        messageBuilder.put("type", "ACCOUNT_LIST");
        if (searchNameQuery.length() > 0)
            messageBuilder.put("name", searchNameQuery);
        if (searchCathedraQuery.length() > 0)
            messageBuilder.put("cathedra", searchCathedraQuery);
        if (searchGroupQuery.length() > 0)
            messageBuilder.put("group", searchGroupQuery);
        Controller.getController().getConnectionAssistant().sendMessage(messageBuilder.toMessage());
    }

    public void clearSearch() {
        setSearchNameQuery("");
        setSearchCathedraQuery("");
        setSearchGroupQuery("");
    }

    public void setSearchNameQuery(String query) {
        searchNameQuery = query;
    }

    public void setSearchCathedraQuery(String query) {
        searchCathedraQuery = query;
    }

    public void setSearchGroupQuery(String query) {
        searchGroupQuery = query;
    }
}
