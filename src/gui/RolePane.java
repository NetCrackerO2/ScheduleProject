package gui;


import account.role.Role;
import javafx.scene.control.TextField;


public class RolePane extends PaneManager<Role> {
    public TextField nameTextField;

    public RolePane() {
        super("ROLE");
    }

    @Override
    public void load() {

    }

    @Override
    public void update() {

    }

    @Override
    protected Role onConfirm() {
        return null;
    }

    @Override
    protected void onShowDetails(Role object) {

    }
}
