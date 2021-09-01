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

import java.io.IOException;

public class RegisterWindowController {

    public TextField userName;
    public TextField password;
    public Label registering;


    public void register(MouseEvent mouseEvent) {
        if(Account.isAccountWithName(userName.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("There is another account with this username");

            alert.showAndWait();
            return;
        }
        new Account(userName.getText(), password.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done!");
        alert.setHeaderText("Done!");
        alert.setContentText("You registered successfully");

        alert.showAndWait();
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
