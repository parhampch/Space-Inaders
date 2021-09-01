package sample.Game;


import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Shield {

    private Rectangle rectangle = new Rectangle();
    private int life;

    public Shield(double height, double width, double layoutX, double layoutY) {
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setLayoutX(layoutX);
        rectangle.setLayoutY(layoutY);
        life = 15;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void underShoot(GameController gameController)
    {
        life--;
        Platform.runLater(() -> {
            if(life == 10)
            {
                rectangle.setHeight(35);
            }
            else if(life == 5)
            {
                rectangle.setHeight(20);
            }
            else if (life == 0)
            {
                gameController.getGameWindow().getPane().getChildren().remove(this.rectangle);
                gameController.removeShield(this);
            }
        });
    }



}
