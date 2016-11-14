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
        Scanner scanner = new Scanner(System.in);
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
       

        View.writeMessage("Введите название нового предмета: \n");

        try {
            str = scanner.nextLine();
        } catch (Exception e) {
            View.writeError("Некорректное название предмета.");
            return;
        }
        
        Cathedra cathedra=CathedraManager.getInstance().getAllCathedra().get(numberCath);
        try {
            SubjectManager.getInstance().getNewSubject(cathedra, str);
        } catch (Exception e) {
            View.writeError(e.getMessage());
            return;
        }

        View.writeMessage("Предмет добавлен.\n");
    }

    @Override
    public String getTitle() {
        return "Добавить новый предмет";
    }
    
}
