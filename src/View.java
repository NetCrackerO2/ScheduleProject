import group.GroupsManager;


public class View {
    static void writeAllGroups(){
        System.out.println("Список всех групп:");
        GroupsManager.getInstance().getAllGroups().stream().forEach(
                (needGroup) -> System.out.println(needGroup.getNumber())
        );
        System.out.println("");
    }
}
