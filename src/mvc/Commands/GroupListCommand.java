package mvc.Commands;


import account.role.Permission;
import connection.Message;
import group.Group;
import group.GroupImpl;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GroupListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<Group> groupList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            groupList.add(GroupImpl.fromJSONObject((JSONObject) object));
        MainForm.getMainForm().setGroupList(groupList);
    }

    @Override
    public String getType() {
        return "GROUP_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{};
    }
}
