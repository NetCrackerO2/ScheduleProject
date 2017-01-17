package gui; /**
 * Created by Dmi3 on 17.01.2017.
 */
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Controller {
    @FXML
    Pane framePane;

    public Controller() {
    }

    public void showFaculty() {
        try {
            Node e = FXMLLoader.load(this.getClass().getResource("forms/Faculty.fxml"));
            this.framePane.getChildren().setAll(new Node[]{e});
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void showCathedra() {
        try {
            Node e = FXMLLoader.load(this.getClass().getResource("forms/Cathedra.fxml"));
            this.framePane.getChildren().setAll(new Node[]{e});
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void showGroup() {
        try {
            Node e = FXMLLoader.load(this.getClass().getResource("forms/Group.fxml"));
            this.framePane.getChildren().setAll(new Node[]{e});
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void showAccount() {
        try {
            Node e = FXMLLoader.load(this.getClass().getResource("forms/Account.fxml"));
            this.framePane.getChildren().setAll(new Node[]{e});
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void showRole() {
        try {
            Node e = FXMLLoader.load(this.getClass().getResource("forms/Role.fxml"));
            this.framePane.getChildren().setAll(new Node[]{e});
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}
