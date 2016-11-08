package Subject;


import Cathedra.Cathedra;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


/**
 * @author AsGreyWolf
 */
public class SubjectManager {
    private static SubjectManager instance;
    private List<Subject> list = new ArrayList<Subject>();
    private SubjectManager() {}
    
    /**
     * Singletone instance
     * @return new SubjectManager if called before, buffered one otherwise
     */
    public static SubjectManager getInstance() {
        if (instance == null)
            instance = new SubjectManager();
        return instance;
    }
    /**
     * Factory method to get new Subject instance
     * @param cathedra
     * @param name
     * @return Subject instance
     */
    public Subject getNewSubject(Cathedra cathedra, String name) {
        if (list.stream().anyMatch(current -> current.getName().equals(name) && current.getCathedra().equals(cathedra)))
            throw new IllegalArgumentException("Subject already exists");
        Subject result = new SubjectImpl(cathedra, name);
        list.add(result);
        return result;
    }
    /**
     * Remove Subject from Manager
     * @param group
     */
    public void removeSubject(Subject group) {
        if (!list.remove(group))
            throw new NoSuchElementException("Subject not found!");
    }
    /**
     * Get already generated subject by cathedra and name
     * @param cathedra
     * @param name
     * @return Subject instance
     */
    public Subject getSubject(Cathedra cathedra, String name) {
    	for (Subject current : list)
            if (current.getCathedra().equals(cathedra) && current.getName().equals(name))
            	return current;
        throw new NoSuchElementException("Subject not found!");
    }
    /**
     * Get subject list by cathedra
     * @param cathedra
     * @return list of Subject
     */
    public List<Subject> getSubjectsByCathedra(Cathedra cathedra) {
    	return list.stream().filter(current -> current.getCathedra().equals(cathedra)).collect(Collectors.toList());
    }
    /**
     * Get all Subject instances
     * @return list of Subject
     */
    public List<Subject> getAllSubjects() {
        return new ArrayList<Subject>(list);
    }
}
