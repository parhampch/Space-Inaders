package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Game.GameController;
import sample.Game.GameWindow;

public class Main extends Application {

    private static Stage stage;
    private static GameWindow gameWindow;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(new Scene(root, 600, 400));
        stage = primaryStage;
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            if (gameWindow != null)
            {
                GameWindow.setGameOn(false);
                gameWindow.endGame();
            }
        });
        GameWindow.setStage(stage);
        primaryStage.show();
     }

    public static Stage getStage() {
        return stage;
    }

    public static void setGameWindow(GameWindow gameWindow) {
        Main.gameWindow = gameWindow;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

