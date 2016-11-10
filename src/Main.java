import account.Account;
import cathedra.Cathedra;
import faculty.Faculty;
import faculty.FacultyImpl;
import group.Group;
import group.GroupsManager;


public class Main {
    public static void main(String[] args) {
        Faculty faculty1, faculty2;
        faculty1 = new FacultyImpl(1);
        faculty2 = new FacultyImpl(2);

        Cathedra cathedra1, cathedra2;
        cathedra1 = new Cathedra("Phisics");
        cathedra2 = new Cathedra("IT");

        Group[] groups = new Group[10];
        GroupsManager groupsManager = GroupsManager.getInstance();


        // Распределяем группы по факультетам и кафедрам
        for (int i = 0; i < 10; i++)
            groups[i] = groupsManager.getNewGroup(
                    i % 2 == 0 ? faculty1 : faculty2,
                    i % 2 == 0 ? cathedra2 : cathedra1,
                    i + 1000
            );


        // Тестируем группы ///////////////////////////////////////////////
        System.out.println("Группы из факультета №1:");
        groupsManager.getGroupsByFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Группы из факультета №2:");
        groupsManager.getAllGroups().stream().filter(
                currentGroup -> currentGroup.getFaculty().getNumber() == 2
        ).forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Группы из кафедры \"" + cathedra1.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Группы из кафедры \"" + cathedra2.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra2).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Удаляем группу \"1000\"...");
        groupsManager.removeGroup(1000);
        System.out.println("Группы из факультета №1");
        groupsManager.getGroupsByFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");

        System.out.println("Удаляем группу \"1003\"...");
        groupsManager.removeGroup(1003);
        System.out.println("Группы из кафедры \"" + cathedra1.getName() + "\":");
        groupsManager.getGroupsByCathedra(cathedra1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");
        //////////////////////////////////////////////////////////////////////////////

        // Тестируем аккаунты
        Account[] accounts = new Account[10];
        for (int i = 0; i < 10; i++)
            accounts[i] = new Account("" + i, groupsManager.getGroup(1004));

        //...

    }
}
