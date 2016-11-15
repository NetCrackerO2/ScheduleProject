package mvc.Commands;

import cathedra.CathedraManager;
import mvc.Command;
import mvc.View;
import faculty.FacultyImpl;
import java.util.Scanner;

public class AddNewCathedraCommand implements Command{
    @Override
    public void activate(){
        Scanner scanner = new Scanner(System.in);
        String name;
        View.writeMessage("Введите название кафедры: ");
        try{
            name = scanner.nextLine();
        }
        catch (Exception e){
            View.writeError("Какое-то странное название для кафедры");
            return;
        }
        
        FacultyImpl faculty = new FacultyImpl(11);
        
        try{
            CathedraManager.getInstance().addNewCathedra(faculty, name);            
        }
        catch (Exception e) {
            View.writeError(e.getMessage());
            return;
        }
        View.writeMessage("Кафедра добавлена. ");
    }
    
    @Override
    public String getTitle() {
        return "Добавить новую кафедру";
    }
}
