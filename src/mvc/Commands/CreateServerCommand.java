package mvc.Commands;


import connection.ServerAssistant;
import mvc.Command;


public class CreateServerCommand implements Command {
    @Override
    public void activate() {
        ServerAssistant serverAssistant = new ServerAssistant();
        serverAssistant.initialize();

        while(true){
            System.out.println(serverAssistant.getNextMessage().getValue("type"));
        }


        //serverAssistant.stop();
    }

    @Override
    public String getTitle() {
        return "CreateServer";
    }
}
