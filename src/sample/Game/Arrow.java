package sample.Game;

import javafx.scene.image.ImageView;

public class Arrow {

    private ImageView arrowImage;
    private GameWindow gameWindow;

    public Arrow(GameWindow gameWindow, ImageView arrowImage)
    {
        this.arrowImage = arrowImage;
        this.gameWindow = gameWindow;
    }

    public ImageView getArrowImage() {
        return arrowImage;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public void shoot()
    {
        if (this.arrowImage.getLayoutY() < -10) {
            this.gameWindow.getPane().getChildren().remove(this.arrowImage);
            return;
        }
        this.arrowImage.setLayoutY(this.arrowImage.getLayoutY() - 10);
    }

    public void destroy()
    {
        this.gameWindow.getPane().getChildren().remove(this.arrowImage);
    }

}
