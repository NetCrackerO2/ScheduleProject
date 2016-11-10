package cathedra;


import faculty.Faculty;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmi3
 */
public class CathedraManager {
    private static CathedraManager instance;
    private List<Cathedra> list=new ArrayList<Cathedra>();
    
    private CathedraManager() {
        
    }
    
    /**
     * Singletone instance
     * 
     * @return new CathedraManager if called before, buffered one otherwise
     */
    public static CathedraManager getInstance() {
        if (instance == null)
            instance=new CathedraManager();
        return instance;
    }
    
    /**
     * Factory method to get new subject instance
     * 
     * @param faculty
     * @param name
     * 
     * @return cathedra instance
     */
    public Cathedra addNewCathedra(Faculty faculty, String name) {
        if (list.stream().anyMatch(current->current.getFaculty().equals(faculty) && current.getName().equals(name)))
            throw new IllegalArgumentException("Cathedra already exist");
        Cathedra newCathedra=new Cathedra(faculty,name);
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
    
    /**
     * Get cathedra by faculty and name
     * 
     * @param faculty
     * @param name
     * 
     * @return cathedra instance
     */
    public Cathedra getCathedra(Faculty faculty,String name) {
        for(Cathedra current:list)
            if(current.getFaculty().equals(faculty) && current.getName().equals(name))
                return current;
        throw new NoSuchElementException("Cathedra not found");
    }
    
    /**
     * Get cathedra list by faculty
     * 
     * @param faculty
     * @return list of cathedra
     */
    public List<Cathedra> getListCathedra(Faculty faculty) {
        return list.stream().filter(current->current.getFaculty().equals(faculty)).collect(Collectors.toList());
    }
    
    /**
     * Get all cathedra instance
     * 
     * @return list of cathedra
     */
    public ArrayList<Cathedra> getAllCathedra() {
        return new ArrayList<Cathedra>(list);
    }
}
