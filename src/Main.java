import account.*;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyManager;
import group.Group;
import group.GroupManager;
import mvc.Commands.*;
import mvc.Controller;
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

        Faculty faculty1, faculty2;
        faculty1 = facultyManager.getNewFaculty(1);
        faculty2 = facultyManager.getNewFaculty(2);

        // Распределяем кафедры по факультетам
        Cathedra cathedra1, cathedra2, cathedra3;
        cathedra1 = cathedraManager.addNewCathedra(faculty1, "Phisics");
        cathedra2 = cathedraManager.addNewCathedra(faculty2, "IT");
        cathedra3 = cathedraManager.addNewCathedra(faculty2, "Garbage");

        // Распределяем группы по факультетам и кафедрам
        Group[] groups = new Group[10];
        for (int i = 0; i < 10; i++)
            groups[i] = groupManager.getNewGroup(i % 2 == 0 ? cathedra2 : cathedra1, i + 1000);

        // Распределяем аккаунты по группам
        Account[] accounts = new Account[10];
        for (int i = 0; i < 10; i++)
            accounts[i] = accountManager.getNewAccount("" + i);

        // Распределяем предметы по кафедрам
        Subject[] subjects = new Subject[10];
        for (int i = 0; i < 10; i++)
            subjects[i] = subjectManager.getNewSubject(i < 5 ? cathedra1 : cathedra2, "" + i);

        // Создаём и заполняем правами роли
        roleManager.createRole("Student").addPermissions(
                Permission.InGroup
        );
        roleManager.createRole("Head").addPermissions(
                Permission.InGroup,
                Permission.EditAccount
        );
        roleManager.createRole("Lecturer").addPermissions(
                Permission.InCathedra,
                Permission.AddOrRemoveAccount,
                Permission.EditAccount,
                Permission.AddOrRemoveGroup,
                Permission.EditGroup
        );
        roleManager.createRole("Dean").addPermissions(
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


        roleManager.addRole(accounts[0], roleManager.getRole("Student"));
        roleManager.addRole(accounts[1], roleManager.getRole("Student"));
        roleManager.addRole(accounts[2], roleManager.getRole("Student"));
        roleManager.addRole(accounts[3], roleManager.getRole("Student"));
        roleManager.addRole(accounts[4], roleManager.getRole("Student"));
        roleManager.addRole(accounts[5], roleManager.getRole("Head"));
        roleManager.addRole(accounts[6], roleManager.getRole("Lecturer"));
        roleManager.addRole(accounts[7], roleManager.getRole("Dean"));
        roleManager.addRole(accounts[8], roleManager.getRole("Student"));
        roleManager.addRole(accounts[9], roleManager.getRole("Student"));

        /////////////////////////////////////////////////////////////////////////
        Role currentRole = roleManager.getRole("Dean");
        /////////////////////////////////////////////////////////////////////////


        Controller controller = new Controller();
        controller.addCommand(new CreateServerCommand());
        controller.addCommand(new CreateClientCommand());
        controller.start();
    }
}