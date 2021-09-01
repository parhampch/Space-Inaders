package sample.Game;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    private GameWindow gameWindow;
    private ArrayList<Alien> allAliens = new ArrayList<>();
    private ArrayList<Shield> allShields = new ArrayList<>();

    public GameWindow getGameWindow() {
        return gameWindow;
    }

    public GameController(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public ArrayList<Alien> getAllAliens() {
        return allAliens;
    }

    public ArrayList<Shield> getAllShields() {
        return allShields;
    }

    public void addShield(Shield shield)
    {
        allShields.add(shield);
    }

    public void removeShield(Shield shield)
    {
        allShields.remove(shield);
    }

    public void addToAllAliens(Alien alien)
    {
        allAliens.add(alien);
    }

    public ArrayList<ImageView> checkDestroy(Arrow arrow)
    {
        ArrayList<Alien> temp = (ArrayList<Alien>) allAliens.clone();
        ArrayList<ImageView> removed = new ArrayList<>();
        for (Alien alien : temp) {
            if (alien.getAlienImage().getBoundsInParent().intersects(arrow.getArrowImage().getBoundsInParent()))
            {
                removed.add(alien.getAlienImage());
                allAliens.remove(alien);
            }

        }
        return removed;
    }

    public boolean canMoveRight()
    {
        for (Alien alien : allAliens) {
            if(alien.getAlienImage().getLayoutX() > 950)
            {
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft()
    {
        for (Alien alien : allAliens) {
            if(alien.getAlienImage().getLayoutX() < 15)
            {
                return false;
            }
        }
        return true;
    }

    public void moveToRight()
    {
        Platform.runLater(() -> {
            for (Alien alien : allAliens) {
                alien.getAlienImage().setLayoutX(alien.getAlienImage().getLayoutX() + 10);
            }
        });
    }

    public void moveToLeft()
    {
        Platform.runLater(() -> {
            for (Alien alien : allAliens) {
                alien.getAlienImage().setLayoutX(alien.getAlienImage().getLayoutX() - 10);
            }
        });
    }

    public void horizonMoving()
    {
        GameController gameController = this;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            boolean lastMove = true;
            @Override
            public void run() {
                synchronized (allAliens)
                {
                    if(!GameWindow.isGameOn() || !GameWindow.getStage().isShowing())
                    {
                        gameController.getGameWindow().removeTimer(timer);
                        gameController.getGameWindow().removeTimerTask(this);
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                    }
                    else if (!gameController.canMoveRight())
                    {
                        gameController.moveToLeft();
                        lastMove = false;
                    }
                    else if (!gameController.canMoveLeft())
                    {
                        gameController.moveToRight();
                        lastMove = true;
                    }
                    else if (gameController.canMoveRight() && lastMove)
                    {
                        moveToRight();
                    }
                    else if(gameController.canMoveLeft() && !lastMove)
                    {
                        moveToLeft();
                    }
                }
            }
        };
        gameController.getGameWindow().addTimer(timer);
        gameController.getGameWindow().addTimerTask(timerTask);
        timer.scheduleAtFixedRate(timerTask, 0, 500);
    }

    private boolean canGoDown()
    {
        for (Alien alien : allAliens) {
            if (alien.getAlienImage().getLayoutY() > 450)
            {
                return false;
            }
        }
        return true;
    }

    public void verticalMoving()
    {
        GameController gameController = this;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (allAliens)
                {
                    if(!GameWindow.isGameOn() || !GameWindow.getStage().isShowing())
                    {
                        gameController.getGameWindow().removeTimer(timer);
                        gameController.getGameWindow().removeTimerTask(this);
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                        return;
                    }
                    else if (!gameController.canGoDown())
                    {
                        gameController.getGameWindow().endGame();
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                        return;
                    }
                    Platform.runLater(() -> {
                        for (Alien alien : allAliens) {
                            alien.getAlienImage().setLayoutY(alien.getAlienImage().getLayoutY() + 30);
                        }
                    });
                }
            }
        };
        gameController.getGameWindow().addTimer(timer);
        gameController.getGameWindow().addTimerTask(timerTask);
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }

    public boolean isWin()
    {
        return allAliens.isEmpty();
    }


}
