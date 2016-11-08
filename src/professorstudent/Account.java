/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package professorstudent;


import cathedra.Cathedra;
import group.*;


/**
 * @author Александр
 */
public class Account {
    private String name;
    private Cathedra cathedra;
    private Group group;
    private RoleAssigment roleAssigment;

    public Account(String name, Group group) {
        this.name = name;
        this.group = group;
    }

    public Account(String name, Cathedra cathedra) {
        this.name = name;
        this.cathedra = cathedra;
    }

    public Cathedra getCathedra() {
        return this.cathedra;
    }

    public void setCathedra(Cathedra cathedra) {
        this.cathedra = cathedra;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
