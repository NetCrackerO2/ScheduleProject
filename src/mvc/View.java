package mvc;


import group.GroupsManager;

import java.util.List;


public class View {
    public static void writeAllGroups() {
        System.out.println("Список всех групп:");
        GroupsManager.getInstance().getAllGroups().stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
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
