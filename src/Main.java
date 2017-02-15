import account.Account;
import account.AccountManager;
import account.role.Permission;
import account.role.Role;
import account.role.RoleManager;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyManager;
import group.Group;
import group.GroupManager;
import subject.Subject;
import subject.SubjectManager;


public class Main {
    public static void main(String[] args) {
        FacultyManager facultyManager = FacultyManager.getInstance();
        CathedraManager cathedraManager = CathedraManager.getInstance();
        GroupManager groupManager = GroupManager.getInstance();
        AccountManager accountManager = AccountManager.getInstance();
        SubjectManager subjectManager = SubjectManager.getInstance();
        RoleManager roleManager = RoleManager.getInstance();

        Faculty faculty1, faculty2, faculty3;
        faculty1 = facultyManager.createObject();
        faculty2 = facultyManager.createObject();
        faculty3 = facultyManager.createObject();
        faculty1.setNumber(1);
        faculty2.setNumber(2);
        faculty3.setNumber(3);

        // Распределяем кафедры по факультетам
        Cathedra cathedra1, cathedra2, cathedra3;
        cathedra1 = cathedraManager.createObject();
        cathedra2 = cathedraManager.createObject();
        cathedra3 = cathedraManager.createObject();
        cathedra1.setFacultyIndex(faculty1.getIndex());
        cathedra2.setFacultyIndex(faculty2.getIndex());
        cathedra3.setFacultyIndex(faculty2.getIndex());
        cathedra1.setName("Phisics");
        cathedra2.setName("IT");
        cathedra3.setName("Garbage");

        // Распределяем группы по факультетам и кафедрам
        Group[] groups = new Group[10];
        for (int i = 0; i < 10; i++) {
            groups[i] = groupManager.createObject();
            groups[i].setNumber(i + 1000);
            groups[i].setCathedraIndex(i % 2 == 0 ? cathedra2.getIndex() : cathedra1.getIndex());
        }

        // Распределяем аккаунты по группам
        Account[] accounts = new Account[10];
        for (int i = 0; i < 10; i++) {
            accounts[i] = accountManager.createObject();
            accounts[i].setName("Аккаунт №" + i);
        }

        // Распределяем предметы по кафедрам
        Subject[] subjects = new Subject[10];
        for (int i = 0; i < 10; i++) {
            subjects[i] = subjectManager.createObject();
            subjects[i].setName("" + i);
            subjects[i].setCathedraIndex(i < 5 ? cathedra1.getIndex() : cathedra2.getIndex());
        }

        // Создаём и заполняем правами роли
        Role newRole;
        newRole = roleManager.createObject();
        newRole.setName("Student");
        newRole.addPermissions(
                Permission.InGroup
        );
        newRole = roleManager.createObject();
        newRole.setName("Head");
        newRole.addPermissions(
                Permission.InGroup,
                Permission.EditAccount
        );
        newRole = roleManager.createObject();
        newRole.setName("Lecturer");
        newRole.addPermissions(
                Permission.InCathedra,
                Permission.AddOrRemoveAccount,
                Permission.EditAccount,
                Permission.AddOrRemoveGroup,
                Permission.EditGroup
        );
        newRole = roleManager.createObject();
        newRole.setName("Dean");
        newRole.addPermissions(
                Permission.InFaculty,
                Permission.AddOrRemoveAccount,
                Permission.EditAccount,
                Permission.AddOrRemoveGroup,
                Permission.EditGroup,
                Permission.AddOrRemoveCathedra,
                Permission.EditCathedra,
                Permission.AddOrRemoveSubject,
                Permission.EditSubject,
                Permission.AddOrRemoveFaculty,
                Permission.EditFaculty
        );


        roleManager.addRole(accounts[0].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[1].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[2].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[3].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[4].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[5].getIndex(), roleManager.getObject(1));
        roleManager.addRole(accounts[6].getIndex(), roleManager.getObject(2));
        roleManager.addRole(accounts[7].getIndex(), roleManager.getObject(3));
        roleManager.addRole(accounts[8].getIndex(), roleManager.getObject(0));
        roleManager.addRole(accounts[9].getIndex(), roleManager.getObject(0));

        faculty2.setDeanAccountIndex(accounts[7].getIndex());

        /////////////////////////////////////////////////////////////////////////


        /*Controller controller   = new Controller();
        Controller.setController(controller);

        controller.addCommand(new AccountAddCommand());
        controller.addCommand(new AccountEditCommand());
        controller.addCommand(new AccountListCommand());
        controller.addCommand(new AccountRemoveCommand());
        controller.addCommand(new CathedraAddCommand());
        controller.addCommand(new CathedraEditCommand());
        controller.addCommand(new CathedraListCommand());
        controller.addCommand(new CathedraRemoveCommand());
        controller.addCommand(new FacultyAddCommand());
        controller.addCommand(new FacultyEditCommand());
        controller.addCommand(new FacultyListCommand());
        controller.addCommand(new FacultyRemoveCommand());
        controller.addCommand(new GroupAddCommand());
        controller.addCommand(new GroupEditCommand());
        controller.addCommand(new GroupListCommand());
        controller.addCommand(new GroupRemoveCommand());
        controller.addCommand(new SubjectAddCommand());
        controller.addCommand(new SubjectEditCommand());
        controller.addCommand(new SubjectListCommand());
        controller.addCommand(new SubjectRemoveCommand());


        ServerAssistant serverAssistant = new ServerAssistant();
        controller.setConnectionAssistant(serverAssistant);
        try {
            serverAssistant.initialize();
        } catch (IOException e) {
            System.out.println(Localization.getInstance().getString("SERVER_INITIALIZATION_ERROR"));
            return;
        }

        controller.start();*/
    }
}