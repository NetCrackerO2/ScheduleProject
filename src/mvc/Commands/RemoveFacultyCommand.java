/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Commands;

import faculty.FacultyManager;
import mvc.Command;
import mvc.Controller;

/**
 *
 * @author Александр
 */
public class RemoveFacultyCommand implements Command{
    @Override
    public void activate(){
        String facultyName = Controller.getStringResponse("CATHEDRA");
        if (!FacultyManager.getInstance().isExist(facultyName))
            throw new ElementNotExistsException();

        try {
            FacultyManager.getInstance().removeFaculty(facultyName);
        } catch (Exception ex) {
            throw new ElementNotRemovedException();
        }
    }
    
    @Override
    public String getTitle() {
        return "CMD_FACULTY_REMOVE";
    }
}
