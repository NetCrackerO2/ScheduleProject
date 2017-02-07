package mvc.Commands;


import account.role.Permission;
import cathedra.Cathedra;
import cathedra.CathedraImpl;
import connection.Message;
import gui.MainForm;
import mvc.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CathedraListCommand implements Command {
    @Override
    public void activate(Message message) {
        JSONArray jsonArray;
        jsonArray = (JSONArray) message.getValue("data");
        List<Cathedra> cathedraList = new ArrayList<>();
        for (Object object : jsonArray.toArray())
            cathedraList.add(CathedraImpl.fromJSONObject((JSONObject) object));
        MainForm.getMainForm().setCathedraList(cathedraList);
    }

    @Override
    public String getType() {
        return "CATHEDRA_LIST";
    }

    @Override
    public Permission[] getRequiredPermissions() {
        return new Permission[]{};
    }
}
