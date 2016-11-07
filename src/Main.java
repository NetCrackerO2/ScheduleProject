import Faculty.Faculty;
import Faculty.FacultyImpl;
import Group.Group;
import Group.GroupsManager;


public class Main {
    public static void main(String[] args) {
        Faculty faculty1, faculty2;
        faculty1 = new FacultyImpl(1);
        faculty2 = new FacultyImpl(2);
        Group[] groups = new Group[10];
        GroupsManager groupsManager = GroupsManager.getInstance();

        for (int i = 0; i < 10; i++)
            groups[i] = groupsManager.getNewGroup(i % 2 == 0 ? faculty1 : faculty2, i + 1000);


        System.out.println("Группы из факультета №1");
        groupsManager.getGroupsInFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );

        System.out.println("Группы из факультета №2");
        groupsManager.getAllGroups().stream().filter(
                currentGroup -> currentGroup.getFaculty().getNumber() == 2
        ).forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );

        System.out.println("Удаляем группу из первого факультета...");
        groupsManager.removeGroup(1000);
        System.out.println("Группы из факультета №1");
        groupsManager.getGroupsInFaculty(faculty1).stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
    }
}
