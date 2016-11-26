package cathedra;


import faculty.Faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * @author Dmi3
 */
public class CathedraManager {
    private static CathedraManager instance;
    private List<Cathedra> list = new ArrayList<>();

    private CathedraManager() {

    }

    /**
     * Singletone instance
     *
     * @return new CathedraManager if called before, buffered one otherwise
     */
    public static CathedraManager getInstance() {
        if (instance == null)
            instance = new CathedraManager();
        return instance;
    }

    /**
     * Factory method to get new cathedra instance
     *
     * @param faculty
     * @param name
     * @return cathedra instance
     */
    public Cathedra addNewCathedra(Faculty faculty, String name) {
        if (list.stream().anyMatch(current -> current.getName().equals(name)))
            throw new IllegalArgumentException("Cathedra already exist");
        Cathedra newCathedra = new CathedraImpl(faculty, name);
        list.add(newCathedra);
        return newCathedra;
    }

    /**
     * Remove cathedra from Manager
     *
     * @param cathedra
     */
    public void removeCathedra(Cathedra cathedra) {
        if (!list.remove(cathedra))
            throw new NoSuchElementException("Cathedra not found");
    }

    public void removeCathedra(String name) {
        Cathedra removedCathedra;

        try {
            removedCathedra = list.stream().filter(
                    currentCathedra -> currentCathedra.getName() == name
            ).findFirst().get();
        } catch (NoSuchElementException exc) {
            throw exc;
        }

        list.remove(removedCathedra);
    }

    /**
     * Get cathedra by faculty and name
     *
     * @param name
     * @return cathedra instance
     */
    public Cathedra getCathedra(String name) {
        for (Cathedra current : list)
            if (current.getName().equals(name))
                return current;
        throw new NoSuchElementException("Cathedra not found");
    }

    public Cathedra getCathedra(String name, Faculty faculty) {
        for (Cathedra current : list)
            if (current.getFaculty().equals(faculty) && current.getName().equals(name))
                return current;
        throw new NoSuchElementException("Cathedra not found");
    }

    public boolean isExist(String name) {
        return list.stream().anyMatch((current) -> (current.getName().equals(name)));
    }

    public boolean isExist(String name, Faculty faculty) {
        return list.stream().anyMatch((current) -> (current.getName().equals(name) && current.getFaculty().equals(faculty)));
    }

    /**
     * Get all cathedra instance
     *
     * @return list of cathedra
     */
    public ArrayList<Cathedra> getAllCathedra() {
        return new ArrayList<>(list);
    }

    public void setCathedra(String oldNameCathedra, String newNameCathedra, Faculty faculty) {
        if (list.stream().anyMatch(s -> s.getName().equals(newNameCathedra)))
            throw new IllegalArgumentException("Cathedra already exist");
        Cathedra temp = getCathedra(oldNameCathedra);
        temp.setName(newNameCathedra);
        temp.setFaculty(faculty);
    }

    public List<Cathedra> getCathedraByFaculty(Faculty faculty) {
        return list.stream().filter(current -> current.getFaculty() == faculty).collect(Collectors.toList());
    }
}
