package gui;


import account.role.Permission;
import account.role.Role;
import account.role.UnregistredRole;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RolePane extends PaneManager<Role> {
    public TableView<Role> tableView;
    public TextField nameTextField;
    public ListView<Permission> permissionListBox;
    public Button acceptButton;
    public Button deleteButton;
    private Map<Permission, SimpleBooleanProperty> permissionMap;

    public RolePane() {
        super("ROLE");
    }

    @Override
    public void load() {
        permissionMap = new HashMap<>();
        Arrays.stream(Permission.values()).forEach(
                permission -> permissionMap.put(permission, new SimpleBooleanProperty(false))
        );

        permissionListBox.setCellFactory(CheckBoxListCell.forListView(
                item -> permissionMap.get(item))
        );
        permissionListBox.getItems().addAll(Permission.values());

        UnregistredRole newRole = new UnregistredRole(-1);
        newRole.setName("Новая");
        TableManager<Role> tableManager = new TableManager<>(tableView, NewRowStatus.ACTIVE, newRole);
        tableManager.addColumn("Название", String.class, Object::toString);
        tableManager.addColumn("Права", String.class, role -> {
            final String[] permissions = {""}; // магия

            role.getPermissions().forEach(permission -> permissions[0] += permission.name() + " | ");

            return permissions[0];
        });
        setTableManager(tableManager);
        setAcceptButton(acceptButton);
        setDeleteButton(deleteButton);
    }

    @Override
    public void update() {
        setSource(MainForm.getMainForm().getRoleList());
    }

    @Override
    protected Role onConfirm() {
        UnregistredRole role = new UnregistredRole(-1);

        role.setName(nameTextField.getText());
        permissionMap.keySet().forEach(
                permission -> {
                    if (permissionMap.get(permission).getValue())
                        role.addPermissions(permission);
                }
        );

        return role;
    }

    @Override
    protected void onShowDetails(Role object) {
        nameTextField.setText("");
        permissionMap.keySet().forEach(
                permission -> permissionMap.get(permission).setValue(false)
        );

        if (object != null) {
            nameTextField.setText(object.toString());
            permissionMap.keySet().forEach(
                    permission -> permissionMap.get(permission).setValue(object.hasPermission(permission))
            );
        }
    }
}
