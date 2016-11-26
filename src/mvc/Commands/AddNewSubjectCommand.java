/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.Commands;

import cathedra.Cathedra;
import cathedra.CathedraManager;
import faculty.Faculty;
import faculty.FacultyImpl;
import java.util.Scanner;
import mvc.Command;
import mvc.Controller;
import mvc.View;
import subject.Subject;
import subject.SubjectManager;

/**
 *
 * @author Dmi3
 */
public class AddNewSubjectCommand implements Command {

    @Override
    public void activate() {
        String str;
        int numberCath=0;
        int i=0;
        
        View.writeMessage("Выберите кафедру: \n");
        
        for(Cathedra cathedra: CathedraManager.getInstance().getAllCathedra())
            System.out.println((i++)+") "+cathedra.getName());
            System.out.println("Ваш выбор: ");
        try {
            numberCath = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            View.writeError("Некорректный ввод данных");
            return;
        }
       

        str = Controller.getStringResponse("NEW_SUBJECT");
        
        Cathedra cathedra=CathedraManager.getInstance().getAllCathedra().get(numberCath);
        SubjectManager.getInstance().getNewSubject(cathedra, str);
    }

    @Override
    public String getTitle() {
        return "Добавить новый предмет";
    }
    
}
