package mvc.Commands;


import account.role.Permission;
import connection.Message;
import faculty.Faculty;
import faculty.UnregistredFaculty;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FacultyListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<Faculty> facultyList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            facultyList.add(new UnregistredFaculty((JSONObject) object));
        MainForm.getMainForm().setFacultyList(facultyList);
    }

    @Override
    public String getType() {
        return "FACULTY_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{};
    }
}
