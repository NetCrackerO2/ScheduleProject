package mvc.Commands;


import account.role.Permission;
import account.role.RoleAssignment;
import account.role.UnregistredRoleAssignment;
import connection.Message;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RoleAssignmentListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<RoleAssignment> roleAssignmentList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            roleAssignmentList.add(new UnregistredRoleAssignment((JSONObject) object));
        MainForm.getMainForm().setRoleAssignmentList(roleAssignmentList);
    }

    @Override
    public String getType() {
        return "ROLE_ASSIGNMENT_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{};
    }
}
