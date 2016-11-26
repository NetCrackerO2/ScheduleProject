package mvc.Commands;


import cathedra.CathedraManager;
import mvc.Command;
import mvc.Controller;


public class RemoveCathedraCommand implements Command {
    @Override
    public void activate() {
        String cathedraName = Controller.getStringResponse("CATHEDRA");
        if (!CathedraManager.getInstance().isExist(cathedraName))
            throw new ElementNotExistsException();

        try {
            CathedraManager.getInstance().removeCathedra(cathedraName);
        } catch (Exception ex) {
            throw new ElementNotRemovedException();
        }
    }

    @Override
    public String getTitle() {
        return "CMD_CATHEDRA_REMOVE";
    }
}
