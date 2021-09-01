package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.Game.GameWindow;

public class GameWinWindowController {
    public void startGame(MouseEvent mouseEvent) {
        GameWindow.startGame();
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
}
