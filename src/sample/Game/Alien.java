package sample.Game;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Alien {

    private ImageView alienImage;
    private GameWindow gameWindow;
    public Alien(ImageView alienImage, GameWindow gameWindow) {
        this.alienImage = alienImage;
        this.gameWindow = gameWindow;
    }

    public ImageView getAlienImage() {
        return alienImage;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void shoot(GameWindow gameWindow)
    {
        try {
            File file = new File("images" + File.separator + "fireball.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(10);
            imageView.setFitWidth(10);
            imageView.setLayoutX(this.alienImage.getLayoutX() + 30);
            imageView.setLayoutY(this.alienImage.getLayoutY() + 30);
            Platform.runLater(() -> gameWindow.getPane().getChildren().add(imageView));
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(imageView.getLayoutY() > 620)
                    {
                        Platform.runLater(() -> gameWindow.getPane().getChildren().remove(imageView));
                        gameWindow.removeTimer(timer);
                        gameWindow.removeTimerTask(this);
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                        return;
                    }
                    else if(imageView.getBoundsInParent().intersects(gameWindow.getCannonImageView().getBoundsInParent()))
                    {
                        gameWindow.endGame();
                        return;
                    }
                    ArrayList<Shield> shields = gameWindow.getGameController().getAllShields();
                    for (Shield shield : shields) {
                        if (imageView.getBoundsInParent().intersects(shield.getRectangle().getBoundsInParent()))
                        {
                            Platform.runLater(() -> {
                                gameWindow.getPane().getChildren().remove(imageView);
                                shield.underShoot(gameWindow.getGameController());
                            });
                            gameWindow.removeTimer(timer);
                            gameWindow.removeTimerTask(this);
                            timer.cancel();
                            timer.purge();
                            this.cancel();
                            break;
                        }
                    }
                    Platform.runLater(() -> {
                        imageView.setLayoutY(imageView.getLayoutY() + 10);
                    });
                }
            };
            gameWindow.addTimer(timer);
            gameWindow.addTimerTask(timerTask);
            timer.scheduleAtFixedRate(timerTask, 0, 100);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
