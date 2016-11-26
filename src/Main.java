import account.Account;
import account.AccountManager;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyManager;
import group.Group;
import group.GroupsManager;
import mvc.Commands.*;
import mvc.Controller;
import subject.Subject;
import subject.SubjectManager;


public class Main {
    public static void main(String[] args) {
        FacultyManager facultyManager = FacultyManager.getInstance();
        CathedraManager cathedraManager = CathedraManager.getInstance();
        GroupsManager groupsManager = GroupsManager.getInstance();
        AccountManager accountManager = AccountManager.getInstance();
        SubjectManager subjectManager = SubjectManager.getInstance();

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
            groups[i] = groupsManager.getNewGroup(i % 2 == 0 ? cathedra2 : cathedra1, i + 1000);

        // Распределяем аккаунты по группам
        Account[] accounts = new Account[10];
        for (int i = 0; i < 10; i++)
            accounts[i] = accountManager.getNewAccount("" + i,
                    i < 5 ? groupsManager.getGroup(1003) : groupsManager.getGroup(1004));

        // Распределяем предметы по кафедрам
        Subject[] subjects = new Subject[10];
        for (int i = 0; i < 10; i++)
            subjects[i] = subjectManager.getNewSubject(i < 5 ? cathedra1 : cathedra2, "" + i);


        Controller controller = new Controller();


        controller.addCommand(new AddNewAccountCommand());
        controller.addCommand(new AddNewCathedraCommand());
        controller.addCommand(new AddNewFacultyCommand());
        controller.addCommand(new AddNewGroupCommand());
        controller.addCommand(new AddNewSubjectCommand());

        controller.addCommand(new AddAccountToGroupCommand());
        controller.addCommand(new EditCathedraCommand());
        controller.addCommand(new EditGroupCommand());

        controller.addCommand(new RemoveAccountFromGroupCommand());
        controller.addCommand(new RemoveCathedraCommand());
        controller.addCommand(new RemoveGroupCommand());

        controller.addCommand(new ViewListAccountsCommand());
        controller.addCommand(new ViewListCathedraCommand());
        controller.addCommand(new ViewListFacultiesCommand());
        controller.addCommand(new ViewListGroupsCommand());
        controller.addCommand(new ViewListSubjectsCommand());


        controller.start();
    }
}