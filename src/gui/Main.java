package gui;


import connection.ClientAssistant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvc.Commands.*;
import mvc.Controller;
import mvc.Localization;

import java.io.IOException;


public class Main extends Application {
    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("forms/Main.fxml"));
        primaryStage.setTitle("Главная страница");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));


        Thread controllerThread = new Thread(() -> {
            Controller controller = new Controller();
            Controller.setController(controller);

            ClientAssistant clientAssistant = new ClientAssistant();
            controller.setConnectionAssistant(clientAssistant);
            try {
                clientAssistant.initialize();
            } catch (IOException e) {
                System.out.println(Localization.getInstance().getString("CLIENT_INITIALIZATION_ERROR"));
                return;
            }

            ///// Команды контролера /////
            controller.addCommand(new FacultyListCommand());
            controller.addCommand(new AccountListCommand());
            controller.addCommand(new CathedraListCommand());
            controller.addCommand(new GroupListCommand());
            controller.addCommand(new RoleListCommand());
            //////////////////////////////

            controller.start();
        }, "controllerThread");
        controllerThread.start();


        primaryStage.setOnCloseRequest(event -> {
            Controller.getController().getConnectionAssistant().stop();
            controllerThread.interrupt();
        });
        primaryStage.show();
        MainForm.getMainForm().updateAllData();
    }
}

