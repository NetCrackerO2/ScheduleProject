package mvc.Commands;

import account.role.Permission;
import account.role.Role;
import account.role.UnregistredRole;
import connection.Message;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoleListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<Role> roleList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            roleList.add(new UnregistredRole((JSONObject) object));
        MainForm.getMainForm().setRoleList(roleList);
    }

    @Override
    public String getType() {
        return "ROLE_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[] {};
    }
}
