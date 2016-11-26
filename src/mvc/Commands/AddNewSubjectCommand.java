/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Commands;

import cathedra.Cathedra;
import cathedra.CathedraManager;
import java.util.NoSuchElementException;
import mvc.Command;
import mvc.Controller;
import subject.SubjectManager;

/**
 *
 * @author Dmi3
 */
public class AddNewSubjectCommand implements Command {

    @Override
    public void activate() {
        String cathedraName = Controller.getStringResponse("CATHEDRA");
        Cathedra cathedra = null;
        try {
            cathedra = CathedraManager.getInstance().getCathedra(cathedraName);
        } catch (NoSuchElementException e) {
            throw new ElementNotExistsException();
        }

        String str = Controller.getStringResponse("NEW_SUBJECT");

        SubjectManager.getInstance().getNewSubject(cathedra, str);
    }

    @Override
    public String getTitle() {
        return "CMD_SUBJECT_NEW";
    }

}
