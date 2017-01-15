package mvc;


import account.AccountManager;
import account.role.Role;
import account.role.RoleManager;
import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.FacultyManager;
import group.GroupManager;
import subject.SubjectManager;

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
        GroupManager.getInstance().getAllObjects()
                .forEach((needGroup) -> System.out.println(needGroup.getNumber()));
        println("");
    }

    private static void writeAllSubjects() {
        println("SUBJECT_LIST");
        for (Cathedra cathedra : CathedraManager.getInstance().getAllObjects()) {
            System.out.println(cathedra.getName() + ": ");
            SubjectManager.getInstance().getAllObjects().stream().filter(subject -> subject.getCathedraIndex() == cathedra.getIndex())
                    .forEach(subject -> System.out.println("   " + subject.getName()));
        }
        println("");
    }

    private static void writeAllCathedra() {
        println("CATHEDRA_LIST");
        CathedraManager.getInstance().getAllObjects()
                .forEach((needCathedra) -> System.out.println(needCathedra.getName()));
        println("");
    }

    private static void writeAllFaculties() {
        println("FACULTY_LIST");
        FacultyManager.getInstance().getAllObjects()
                .forEach((faculty) -> System.out.println(faculty.getNumber()));
        println("");
    }

    private static void writeAllAccounts() {
        println("ACCOUNT_LIST");
        AccountManager.getInstance().getAllObjects().forEach((account) -> {
            print("ACCOUNT_LIST_NAME");
            System.out.println(account.getName());
            print("ACCOUNT_LIST_GROUP");
            System.out.println(account.getGroupIndex() >= 0 ? GroupManager.getInstance().getObject(account.getGroupIndex()).getNumber() : "null");
            print("ACCOUNT_LIST_CATHEDRA");
            System.out.println(account.getCathedraIndex() >= 0 ? CathedraManager.getInstance().getObject(account.getCathedraIndex()).getName() : "null");
            print("ACCOUNT_LIST_ROLE");
            for (Role role : RoleManager.getInstance().getRoles(account.getIndex()))
                System.out.print(role.getName() + " ");
            println("\n");
        });
        println("");
    }

    static void writeAllCommands(List<Command> commandsList) { // TODO:
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
                break;
            case "GROUP":
                writeAllGroups();
                break;
            case "CATHEDRA":
                writeAllCathedra();
                break;
            case "FACULTY":
                writeAllFaculties();
                break;
            case "SUBJECT":
                writeAllSubjects();
                break;

            case "ACCOUNT_LIST":
                writeAllAccounts();
                return;
            case "GROUP_LIST":
                writeAllGroups();
                return;
            case "CATHEDRA_LIST":
                writeAllCathedra();
                return;
            case "FACULTY_LIST":
                writeAllFaculties();
                return;
            case "SUBJECT_LIST":
                writeAllSubjects();
                return;
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
