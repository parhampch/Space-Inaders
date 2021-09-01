package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginWindowController {

    public TextField userName;
    public TextField password;
    public Label logged;

    public void login(MouseEvent mouseEvent) {
        boolean checkLogin = Account.checkLogin(userName.getText(), password.getText());
        if (checkLogin)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done!");
            alert.setHeaderText("Done!");
            alert.setContentText("You logged in successfully");

            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong Username or Password!");
            alert.setContentText("Please enter correct information");

            alert.showAndWait();
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }
}
