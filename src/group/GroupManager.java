package group;


import cathedra.Cathedra;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


public class GroupManager {
    private static GroupManager instance;
    private List<Group> groupsList;
    //-------------


    private GroupManager() {
        groupsList = new ArrayList<Group>();
    }
    //=============


    /**
     * Ленивая инициализация экземпляра
     *
     * @return Объект GroupsManager
     */
    public static GroupManager getInstance() {
        if (instance == null)
            instance = new GroupManager();

        return instance;
    }

    /**
     * @param number Номер новой группы
     * @return Объект новой группы
     * @throws IllegalArgumentException Если группа с номером number уже существует
     */
    public Group getNewGroup(Cathedra cathedra, int number) {
        if (groupsList.stream().anyMatch(currentGroup -> currentGroup.getNumber() == number))
            throw new IllegalArgumentException("Группа с таким номером уже существует!");

        Group newGroup = new GroupImpl(cathedra, number);
        groupsList.add(newGroup);
        return newGroup;
    }

    /**
     * @param group Объект удаляемой группы
     * @throws NoSuchElementException Если удаляемой группы нет в списке
     */
    public void removeGroup(Group group) {
        if (!groupsList.remove(group))
            throw new NoSuchElementException("Такой группы не существует!");
    }

    /**
     * @param number Номер удаляемой группы
     * @throws NoSuchElementException Если удаляемой группы нет в списке
     */
    public void removeGroup(int number) {
        Group removedGroup;

        try {
            removedGroup = groupsList.stream().filter(
                    currentGroup -> currentGroup.getNumber() == number
            ).findFirst().get();
        } catch (NoSuchElementException exc) {
            throw exc;
        }

        groupsList.remove(removedGroup);
    }

    /**
     * @param number Номер искомой группы
     * @return Группа с искомым номером
     * @throws NoSuchElementException Если группы с таким номером не существует
     */
    public Group getGroup(int number) {
        for (Group currentGroup : groupsList)
            if (currentGroup.getNumber() == number)
                return currentGroup;

        throw new NoSuchElementException("Группы с таким номером не существует!");
    }

    public Group getGroup(int number, Cathedra cathedra) {
        for (Group current : groupsList)
            if (current.getCathedra().equals(cathedra) && current.getNumber() == number)
                return current;
        throw new NoSuchElementException("Group not found");
    }

    public boolean isExist(int number) {
        return groupsList.stream().anyMatch((current) -> (current.getNumber() == number));
    }

    public boolean isExist(int number, Cathedra cathedra) {
        return groupsList.stream()
                .anyMatch((current) -> (current.getNumber() == number && current.getCathedra().equals(cathedra)));
    }

    /**
     * @return Список всех существующих групп
     */
    public List<Group> getAllGroups() {
        return new ArrayList<Group>(groupsList);
    }

    /**
     * @param cathedra Объект кафедры, для которой происходит поиск содержащихся в ней групп
     * @return Список групп из переданной кафедры
     */
    public List<Group> getGroupsByCathedra(Cathedra cathedra) {
        return groupsList.stream().filter(
                currentGroup -> currentGroup.getCathedra() == cathedra
        ).collect(Collectors.toList());
    }
}
