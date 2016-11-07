package Group;


import Faculty.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * @author temon137
 */
public class GroupsManager {
    private static GroupsManager instance;
    private List<Group> groupsList;
    //-------------


    private GroupsManager() {
        groupsList = new ArrayList<Group>();
    }
    //=============


    /**
     * Ленивая инициализация экземпляра
     *
     * @return Объект GroupsManager
     */
    public static GroupsManager getInstance() {
        if (instance == null)
            instance = new GroupsManager();

        return instance;
    }

    /**
     * @param faculty Факулльтет, которому будет принадлежать новая группа
     * @param number  Номер новой группы
     * @return Объект новой группы
     * @throws IllegalArgumentException Если группа с номером number уже существует
     */
    public Group getNewGroup(Faculty faculty, int number) {
        if (groupsList.stream().anyMatch(currentGroup -> currentGroup.getNumber() == number))
            throw new IllegalArgumentException("Группа с таким номером уже существует!");

        Group newGroup = new GroupImpl(faculty, number);
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

    /**
     * @return Список всех существующих групп
     */
    public List<Group> getAllGroups() {
        return new ArrayList<Group>(groupsList);
    }

    /**
     * @param faculty Объект факультета, для которого происходит поиск содержащихся в нём групп
     * @return Список групп из переданного факультета
     */
    public List<Group> getGroupsInFaculty(Faculty faculty) {
        List<Group> groupsInFaculty = groupsList.stream().filter(currentGroup -> currentGroup.getFaculty() == faculty).collect(Collectors.toList());

        return groupsInFaculty;
    }
}
