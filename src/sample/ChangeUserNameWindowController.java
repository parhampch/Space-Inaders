package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Account;

public class ChangeUserNameWindowController {

    public TextField userName;

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

    public void change(MouseEvent mouseEvent) {
        Account account = Account.getLoggedInAccount();
        if(account == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login First");
            alert.setContentText("You are not logged in");

            alert.showAndWait();
            return;
        }
        else if(Account.isAccountWithName(userName.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("There is another account with this username");

            alert.showAndWait();
            return;
        }
        account.setUserName(userName.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done!");
        alert.setHeaderText("Done!");
        alert.setContentText("Username changed successfully");

        alert.showAndWait();
    }
}
