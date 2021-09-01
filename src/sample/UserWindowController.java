package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class UserWindowController {

    public void openRegisterWindow(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RegisterWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }

    public void openLoginWindow(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }

    public void back(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }

    public void openChangeUsernameWindow(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChangeUserNameWindow.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }
}