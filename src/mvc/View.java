package mvc;

import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.FacultyManager;
import group.GroupsManager;
import subject.SubjectManager;
import account.AccountManager;

import java.util.List;

public class View {
    private static void print(String key) {
        System.out.print(Localization.getInstance().getString(key));
    }

    private static void println(String key) {
        System.out.println(Localization.getInstance().getString(key));
    }

    private static void writeAllGroups() {
        println("GROUP_LIST");
        GroupsManager.getInstance().getAllGroups().stream()
                .forEach((needGroup) -> System.out.println(needGroup.getNumber()));
        println("");
    }

    private static void writeAllSubjects() {
        println("SUBJECT_LIST");
        for (Cathedra cathedra : CathedraManager.getInstance().getAllCathedra()) {
            System.out.println(cathedra.getName() + ": ");
            SubjectManager.getInstance().getSubjectsByCathedra(cathedra).stream()
                    .forEach(subj -> System.out.println("   " + subj.getName()));
        }
        println("");
    }

    private static void writeAllCathedra() {
        println("CATHEDRA_LIST");
        CathedraManager.getInstance().getAllCathedra().stream()
                .forEach((needCathedra) -> System.out.println(needCathedra.getName()));
        println("");
    }

    private static void writeAllFaculties() {
        println("FACULTY_LIST");
        FacultyManager.getInstance().getAllFaculty().stream()
                .forEach((faculty) -> System.out.println(faculty.getNumber()));
        println("");
    }

    private static void writeAllAccounts() {
        println("ACCOUNT_LIST");
        AccountManager.getInstance().getAllAccounts().stream().forEach((account) -> {
            print("ACCOUNT_LIST_NAME");
            System.out.println(account.getName());
            print("ACCOUNT_LIST_GROUP");
            System.out.println((account.getGroup() != null ? account.getGroup().getNumber() : "null") + "\n");
        });
        println("");
    }

    public static void writeAllCommands(List<Command> commandsList) { // TODO:
                                                                      // private
        int i = 0;

        println("");
        for (Command command : commandsList) {
            println((i++) + ") " + command.getTitle());
        }
        println(i + ") CMD_QUIT");
    }

    public static void request(String key) {
        switch (key) {
        case "ACCOUNT":
            writeAllAccounts();
        case "GROUP":
            writeAllGroups();
        case "CATHEDRA":
            writeAllCathedra();
        case "FACULTY":
            writeAllFaculties();
        case "SUBJECT":
            writeAllSubjects();

        }
        print(key + "_REQUEST");
    }

    public static void setStatus(Exception e) {
        if (e != null)
            println("STATUS_ERROR: " + e.getMessage());
        else
            println("STATUS_SUCCESS");
    }
}
