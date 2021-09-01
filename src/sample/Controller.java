package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sample.Game.GameWindow;

public class Controller {


    public void showScoreBoard(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScoreBoard.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ScoreBoardController scoreBoardController = fxmlLoader.getController();
            scoreBoardController.setScoreBoard();

            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println("problem");
        }
    }

    public void startGame(MouseEvent mouseEvent) {
        GameWindow.startGame();
    }

    public void openUserWindow(MouseEvent mouseEvent) {
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

    public void exit(MouseEvent mouseEvent) {
        Main.getStage().close();
    }
}
