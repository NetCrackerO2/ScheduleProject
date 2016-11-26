package mvc.Commands;


import cathedra.Cathedra;
import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;
import subject.SubjectManager;


public class AddNewSubjectCommand implements Command {

    @Override
    public void activate() {
        String cathedraName = Controller.getStringResponse("CATHEDRA");
        if (!CathedraManager.getInstance().isExist(cathedraName))
            throw new ElementNotExistsException();

        String subjectName = Controller.getStringResponse("NEW_SUBJECT");
        if (SubjectManager.getInstance().isExist(subjectName))
            throw new ElementAlreadyExistsException();

        Cathedra cathedra = CathedraManager.getInstance().getCathedra(cathedraName);
        SubjectManager.getInstance().getNewSubject(cathedra, subjectName);
    }

    @Override
    public String getTitle() {
        return "CMD_SUBJECT_NEW";
    }

}
