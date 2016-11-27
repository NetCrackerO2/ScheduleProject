/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faculty;

//import cathedra.Cathedra;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
//import java.util.stream.Collectors;


public class FacultyManager {
    private static FacultyManager instance;
    private List<Faculty> list = new ArrayList<Faculty>();

    private FacultyManager() {

    }

    /**
     * Singletone instance
     *
     * @return new CathedraManager if called before, buffered one otherwise
     */
    public static FacultyManager getInstance() {
        if (instance == null)
            instance = new FacultyManager();
        return instance;
    }

    /**
     * Factory method to get new faculty instance
     *
     * @param number
     * @return faculty instance
     */
    public Faculty getNewFaculty(int number) {
        if (list.stream().anyMatch(current -> current.getNumber() == number))
            throw new IllegalArgumentException("Faculty already exists");
        FacultyImpl newFaculty = new FacultyImpl(number);
        list.add(newFaculty);
        return newFaculty;
    }

    /**
     * Remove faculty from Manager
     *
     * @param faculty
     */
    public void removeFaculty(Faculty faculty) {
        if (!list.remove(faculty))
            throw new NoSuchElementException("Faculty not found");
    }
    
    /**
     * @param number Номер удаляемого факультета
     * @throws NoSuchElementException Если удаляемого факультета нет в списке
     */
    public void removeFaculty(int number) {
        Faculty removedFaculty;

        try {
            removedFaculty = list.stream().filter(
                    currentGroup -> currentGroup.getNumber() == number
            ).findFirst().get();
        } catch (NoSuchElementException exc) {
            throw exc;
        }

        list.remove(removedFaculty);
    }

    /**
     * Get faculty by number
     *
     * 
     * @param number
     * @return faculty instance
     */
    public Faculty getFaculty(int number) {
        for (Faculty current : list)
            if (current.getNumber() == number)
                return current;
        throw new NoSuchElementException("Faculty not found");
    }

    public boolean isExist(int number) {
        return list.stream().anyMatch((current) -> (current.getNumber() == number));
    }

    /**
     * Get all faculty instance
     *
     * @return list of faculty
     */
    public ArrayList<Faculty> getAllFaculty() {
        return new ArrayList<Faculty>(list);
    }
}