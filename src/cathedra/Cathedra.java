package cathedra;


import faculty.Faculty;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmi3
 */
public class Cathedra {
    private String name;
    private Faculty faculty;
    
    
    public Cathedra(Faculty faculty,String name) {
        this.name=name;
        this.faculty=faculty;
    }
      
    public String getName() {
        return this.name;
    }
    
    public Faculty getFaculty() {
        return this.faculty;
    }
    
}

