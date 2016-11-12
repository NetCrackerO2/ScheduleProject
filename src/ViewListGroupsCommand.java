public class ViewListGroupsCommand extends Command {

    public ViewListGroupsCommand() {
    }

    @Override
    public void activate() {
        View.writeAllGroups();
    }

    @Override
    public String getTitle() {
        return "Вывод всех групп";
    }
}
