package mvc;


import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.FacultyManager;
import group.GroupsManager;
import subject.SubjectManager;
import account.AccountManager;
import java.util.List;


public class View {
    public static void writeAllGroups() {
        System.out.println("Список всех групп:");
        GroupsManager.getInstance().getAllGroups().stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");
    }
    public static void writeAllSubjects() {
        System.out.println("Список всех предметов:");
        for(Cathedra cathedra : CathedraManager.getInstance().getAllCathedra()) {
            System.out.println(cathedra.getName()+": ");
            SubjectManager.getInstance().getSubjectsByCathedra(cathedra).stream().forEach(
                    subj->System.out.println("   "+subj.getName()));
        }
        System.out.println("");
    }
    public static void writeAllCathedra(){
        System.out.println("Список всех кафедр: ");
        CathedraManager.getInstance().getAllCathedra().stream().forEach((needCathedra) -> System.out.println(needCathedra.getName()));
        System.out.println("");
    }
    public static void writeAllFaculties(){
        System.out.println("Список всех факультетов: ");
        FacultyManager.getInstance().getAllFaculty().stream().forEach((faculty) -> System.out.println(faculty.getNumber()));
        System.out.println("");
    }

    public static void writeAllAccounts(){
        System.out.println("Список всех аккаунтов: ");
        AccountManager.getInstance().getAllAccounts().stream().forEach((account) -> System.out.println(account.getName()));
        System.out.println("");
    }

    public static void writeAllCommands(List<Command> commandsList) {
        int i = 0;

        for(Command command : commandsList) {
            System.out.print(i++);
            System.out.print(") ");
            System.out.println(command.getTitle());
        }
        System.out.println(i + ") Выход.");
        System.out.print("Ваш выбор: ");
    }

    public static void writeError(String errorMessage) {
        System.out.println(errorMessage);
    }

    // костыль
    public static void writeMessage(String message){
        System.out.print(message);
    }
}
