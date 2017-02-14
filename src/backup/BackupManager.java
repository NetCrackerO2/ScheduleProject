package backup;

import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import account.Account;
import account.AccountManager;
import account.UnregistredAccount;
import account.role.Permission;
import account.role.Role;
import account.role.RoleAssignment;
import account.role.RoleAssignmentManager;
import account.role.RoleManager;
import account.role.UnregistredRole;
import account.role.UnregistredRoleAssignment;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import cathedra.UnregistredCathedra;
import faculty.Faculty;
import faculty.FacultyManager;
import faculty.UnregistredFaculty;
import group.Group;
import group.GroupManager;
import group.UnregistredGroup;
import manager.GenericEntityManager;
import subject.UnregistredSubject;
import subject.Subject;
import subject.SubjectManager;

public class BackupManager {
    private static volatile BackupManager instance;

    public static BackupManager getInstance() {
        if (instance == null)
            synchronized (BackupManager.class) {
                if (instance == null)
                    instance = new BackupManager();
            }
        return instance;
    }

    private BackupManager() {
    }

    public JSONArray backup(GenericEntityManager<?> manager) {
        JSONArray result = new JSONArray();
        manager.getAllObjects().stream().forEach(x -> result.add(x.getJSONObject()));
        return result;
    }

    public JSONObject backup() {
        JSONObject obj = new JSONObject();
        obj.put("roles", backup(RoleManager.getInstance()));
        obj.put("faculties", backup(FacultyManager.getInstance()));
        obj.put("cathedras", backup(CathedraManager.getInstance()));
        obj.put("groups", backup(GroupManager.getInstance()));
        obj.put("accounts", backup(AccountManager.getInstance()));
        obj.put("subjects", backup(SubjectManager.getInstance()));
        obj.put("role_assignments", backup(RoleAssignmentManager.getInstance()));
        return obj;
    }

    public void restore(JSONObject data) {
        HashMap<Integer, Integer> roleMapping = new HashMap<>();
        HashMap<Integer, Integer> facultyMapping = new HashMap<>();
        HashMap<Integer, Integer> cathedraMapping = new HashMap<>();
        HashMap<Integer, Integer> groupMapping = new HashMap<>();
        HashMap<Integer, Integer> accountMapping = new HashMap<>();
        HashMap<Integer, Integer> subjectMapping = new HashMap<>();
        HashMap<Integer, Integer> roleAssignmentMapping = new HashMap<>();
        try {
            for (Object obj : (JSONArray) data.get("roles")) {
                Role backed = new UnregistredRole((JSONObject) obj);
                Role created = RoleManager.getInstance().createObject();
                roleMapping.put(backed.getIndex(), created.getIndex());
                created.setName(backed.getName());
                created.setPermissions(backed.getPermissions().toArray(new Permission[] {}));
            }
            for (Object obj : (JSONArray) data.get("faculties")) {
                Faculty backed = new UnregistredFaculty((JSONObject) obj);
                Faculty created = FacultyManager.getInstance().createObject();
                facultyMapping.put(backed.getIndex(), created.getIndex());
                created.setNumber(backed.getNumber());
            }
            for (Object obj : (JSONArray) data.get("cathedras")) {
                Cathedra backed = new UnregistredCathedra((JSONObject) obj);
                Cathedra created = CathedraManager.getInstance().createObject();
                cathedraMapping.put(backed.getIndex(), created.getIndex());
                created.setName(backed.getName());
                created.setFacultyIndex(facultyMapping.get(backed.getFacultyIndex()));
            }
            for (Object obj : (JSONArray) data.get("groups")) {
                Group backed = new UnregistredGroup((JSONObject) obj);
                Group created = GroupManager.getInstance().createObject();
                groupMapping.put(backed.getIndex(), created.getIndex());
                created.setNumber(backed.getNumber());
                created.setProfessionCode(backed.getProfessionCode());
                created.setReceiptYear(created.getReceiptYear());
                created.setCathedraIndex(cathedraMapping.get(backed.getCathedraIndex()));
            }
            for (Object obj : (JSONArray) data.get("accounts")) {
                Account backed = new UnregistredAccount((JSONObject) obj);
                Account created = AccountManager.getInstance().createObject();
                accountMapping.put(backed.getIndex(), created.getIndex());
                created.setName(backed.getName());
                created.setGroupIndex(groupMapping.get(backed.getGroupIndex()));
                created.setCathedraIndex(cathedraMapping.get(backed.getCathedraIndex()));
            }
            for (Object obj : (JSONArray) data.get("subjects")) {
                Subject backed = new UnregistredSubject((JSONObject) obj);
                Subject created = SubjectManager.getInstance().createObject();
                subjectMapping.put(backed.getIndex(), created.getIndex());
                created.setName(backed.getName());
                created.setCathedraIndex(cathedraMapping.get(backed.getCathedraIndex()));
            }
            for (Object obj : (JSONArray) data.get("role_assignments")) {
                RoleAssignment backed = new UnregistredRoleAssignment((JSONObject) obj);
                RoleAssignment created = RoleAssignmentManager.getInstance().createObject();
                roleAssignmentMapping.put(backed.getIndex(), created.getIndex());
                created.setAccountIndex(accountMapping.get(backed.getAccountIndex()));
                created.setRoleIndex(roleMapping.get(backed.getRoleIndex()));
            }

            for (Object obj : (JSONArray) data.get("faculties")) {
                Faculty backed = new UnregistredFaculty((JSONObject) obj);
                Faculty created = FacultyManager.getInstance().getObject(facultyMapping.get(backed.getIndex()));
                created.setDeanAccountIndex(accountMapping.get(backed.getDeanAccountIndex()));
            }
            for (Object obj : (JSONArray) data.get("cathedras")) {
                Cathedra backed = new UnregistredCathedra((JSONObject) obj);
                Cathedra created = CathedraManager.getInstance().getObject(cathedraMapping.get(backed.getIndex()));
                created.setHeadAccountIndex(accountMapping.get(backed.getHeadAccountIndex()));
            }
        } catch (Exception e) {
            facultyMapping.forEach((x, y) -> FacultyManager.getInstance().getObject(y).setDeanAccountIndex(-1));
            cathedraMapping.forEach((x, y) -> CathedraManager.getInstance().getObject(y).setHeadAccountIndex(-1));

            roleAssignmentMapping.forEach((x, y) -> RoleAssignmentManager.getInstance().removeObject(y));
            subjectMapping.forEach((x, y) -> SubjectManager.getInstance().removeObject(y));
            accountMapping.forEach((x, y) -> AccountManager.getInstance().removeObject(y));
            groupMapping.forEach((x, y) -> GroupManager.getInstance().removeObject(y));
            cathedraMapping.forEach((x, y) -> CathedraManager.getInstance().removeObject(y));
            facultyMapping.forEach((x, y) -> FacultyManager.getInstance().removeObject(y));
            roleMapping.forEach((x, y) -> RoleManager.getInstance().removeObject(y));
            
            throw e;
        }
    }
}
