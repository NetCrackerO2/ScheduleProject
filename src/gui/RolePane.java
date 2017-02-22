package gui;


import account.role.Permission;
import account.role.Role;
import account.role.UnregistredRole;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.stream.Collectors;


public class RolePane extends PaneManager<Role> {
    public TableView<Role> tableView;
    public TextField nameTextField;
    public ListView<Permission> permissionListView;
    public Button acceptButton;
    public Button deleteButton;
    private CheckListView<Permission, Role> checkListView;

    public RolePane() {
        super("ROLE");
    }

    @Override
    public void load() {
        checkListView = new CheckListView<Permission, Role>(permissionListView) {
            @Override
            public boolean getState(Permission key, Role object) {
                return object != null && object.hasPermission(key);
            }
        };
        checkListView.setSource(Arrays.stream(Permission.values()).collect(Collectors.toList()));

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
        role.addPermissions(checkListView.getCheckedItems().toArray(new Permission[]{}));

        return role;
    }

    @Override
    protected void onShowDetails(Role object) {
        nameTextField.setText("");
        if (object != null) {
            nameTextField.setText(object.toString());
        }

        checkListView.onShowDetails(object);
    }
}
