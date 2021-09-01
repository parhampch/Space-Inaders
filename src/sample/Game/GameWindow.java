package sample.Game;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Account;
import sample.Main;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameWindow {

    private GameController gameController;
    private  Pane pane = new Pane();
    private  Scene scene = new Scene(pane, 1000, 600);
    private ImageView cannonImageView;
    private int rowsOfAliens;
    private static boolean gameOn;
    private boolean canShoot;
    private int score;
    private Label showScore;
    private static Stage stage;
    private ArrayList<Timer> allTimers = new ArrayList<>();
    private ArrayList<TimerTask> allTimerTasks = new ArrayList<>();
    private MediaPlayer mainMusic;


    public static void getReady()
    {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Scene primaryScene = new Scene(vBox, 600, 400);
        TextField minTime = new TextField();
        minTime.setPromptText("min time in millisecond");
        TextField maxTime = new TextField();
        maxTime.setPromptText("max time in millisecond");
        Button startGame = new Button("Start");
        startGame.setOnAction(e ->{GameWindow.startGame();});
        Button back = new Button("back");
        back.setOnAction(e -> {        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameWindow.class.getResource("sample.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Main.getStage().setScene(new Scene(root));
        }
        catch (Exception ex)
        {
            System.out.println("problem");
        }});
        vBox.getChildren().addAll(minTime, maxTime, startGame, back);
        Main.getStage().setScene(primaryScene);
    }

    public static void startGame()
    {
        GameWindow gameWindow = new GameWindow();
        Main.setGameWindow(gameWindow);
        GameWindow.setGameOn(true);
        gameWindow.setCanShoot(true);
        gameWindow.setGameController(new GameController(gameWindow));
        gameWindow.getGameController().horizonMoving();
        gameWindow.getGameController().verticalMoving();
        gameWindow.alienShoot();
        gameWindow.setScene();
        gameWindow.getMainMusic();
        Main.getStage().setScene(gameWindow.scene);
    }


    public void addTimer(Timer timer)
    {
        allTimers.add(timer);
    }

    public void removeTimer(Timer timer)
    {
        allTimers.remove(timer);
    }

    public void addTimerTask(TimerTask timerTask)
    {
        allTimerTasks.add(timerTask);
    }

    public void removeTimerTask(TimerTask timerTask)
    {
        allTimerTasks.remove(timerTask);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GameWindow.stage = stage;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public static void setGameOn(boolean gameOn) {
        GameWindow.gameOn = gameOn;
    }

    public static boolean isGameOn() {
        return GameWindow.gameOn;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public ImageView getCannonImageView() {
        return cannonImageView;
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return pane;
    }

    private void setScene()
    {
        this.pane.setStyle("-fx-background-color: Black;-fx-border-color: Green;");
        this.addCannonImage();
        this.addShields();
        this.addKeys();
        this.setRowsOfAliens();
        this.showScore(0);
    }

    private void showScore(int score)
    {
        showScore = new Label("Score : " + score);
        showScore.setFont(Font.font(25));
        showScore.setTextFill(Color.RED);
        this.getPane().getChildren().add(showScore);
    }

    private void addCannonImage()
    {
        try {
            File file = new File("images" + File.separator + "cannon.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(90);
            imageView.setFitWidth(110);
            imageView.setLayoutX(500);
            imageView.setLayoutY(500);
            this.cannonImageView = imageView;
            this.pane.getChildren().add(imageView);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void addKeys()
    {
        GameWindow gameWindow = this;
        this.scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.RIGHT && this.cannonImageView.getLayoutX() <= 900)
            {
                this.getCannonImageView().setLayoutX(this.getCannonImageView().getLayoutX() + 10);
            }
            else if(e.getCode() == KeyCode.LEFT && this.cannonImageView.getLayoutX() >= -10)
            {
                this.getCannonImageView().setLayoutX(this.getCannonImageView().getLayoutX() - 10);
            }
            else if (e.getCode() == KeyCode.SPACE)
            {
                this.shootArrow();
            }
            else if (e.getCode() == KeyCode.ESCAPE)
            {
                this.endGame();
            }
        });
    }

    private void shootArrow()
    {
        if (!this.canShoot)
        {
            return;
        }
        this.setCanShoot(false);
        try {
            File file = new File("images" + File.separator + "shoot.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(15);
            imageView.setFitWidth(5);
            imageView.setLayoutX(this.getCannonImageView().getLayoutX() + 55) ;
            imageView.setLayoutY(this.getCannonImageView().getLayoutY() - 10);
            this.pane.getChildren().add(imageView);
            Media media = new Media(new File("images/shoot.wav").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            Arrow arrow = new Arrow(this, imageView);
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(arrow.getArrowImage().getLayoutY() < -10)
                    {
                        arrow.getGameWindow().removeTimer(timer);
                        arrow.getGameWindow().removeTimerTask(this);
                        arrow.getGameWindow().setCanShoot(true);
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                        return;
                    }
                    arrow.shoot();
                    ArrayList<ImageView> temp = arrow.getGameWindow().getGameController().checkDestroy(arrow);
                    if(!temp.isEmpty())
                    {
                        Platform.runLater(() -> {
                            arrow.getGameWindow().getPane().getChildren().removeAll(temp);
                            arrow.getGameWindow().setCanShoot(true);
                            score++;
                            showScore.setText("Score : " + score);
                            arrow.destroy();
                            if(arrow.getGameWindow().getGameController().isWin())
                            {
                                arrow.getGameWindow().endGameWithWin();
                            }
                            arrow.getGameWindow().removeTimer(timer);
                            arrow.getGameWindow().removeTimerTask(this);
                            timer.cancel();
                            timer.purge();
                            this.cancel();
                        });
                        return;
                    }
                    ArrayList<Shield> shields = arrow.getGameWindow().getGameController().getAllShields();
                    for (Shield shield : shields) {
                        if (imageView.getBoundsInParent().intersects(shield.getRectangle().getBoundsInParent()))
                        {
                            Platform.runLater(() -> {
                                arrow.getGameWindow().getPane().getChildren().remove(imageView);
                                shield.underShoot(arrow.getGameWindow().getGameController());
                                arrow.getGameWindow().setCanShoot(true);
                            });
                            arrow.getGameWindow().removeTimer(timer);
                            arrow.getGameWindow().removeTimerTask(this);
                            timer.cancel();
                            timer.purge();
                            this.cancel();
                            break;
                        }
                    }
                }
            };
            this.addTimer(timer);
            this.addTimerTask(timerTask);
            timer.scheduleAtFixedRate(timerTask, 0, 50);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private ImageView getAlien1()
    {
        try {
            File file = new File("images" + File.separator + "alien1.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return imageView;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ImageView getAlien2()
    {
        try {
            File file = new File("images" + File.separator + "alien2.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            imageView.setTranslateY(0);
            imageView.setTranslateX(0);
            return imageView;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ImageView getAlien3()
    {
        try {
            File file = new File("images" + File.separator + "alien3.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return imageView;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ImageView getAlien4()
    {
        try {
            File file = new File("images" + File.separator + "alien4.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return imageView;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private ImageView getAlien5()
    {
        try {
            File file = new File("images" + File.separator + "alien5.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(30);
            imageView.setFitWidth(30);
            return imageView;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void setRowsOfAliens()
    {
        int layoutX;
        int layoutY;
        ImageView imageView;
        layoutX = 150;
        layoutY = 10;
        for (int i = 0; i < 20; i++)
        {
            imageView = this.getAlien1();
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            this.getGameController().addToAllAliens(new Alien(imageView, this));
            this.pane.getChildren().add(imageView);
            layoutX += 35;
        }
        layoutX = 150;
        layoutY = 40;
        for (int i = 0; i < 20; i++)
        {
            imageView = this.getAlien2();
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            this.getGameController().addToAllAliens(new Alien(imageView, this));
            this.pane.getChildren().add(imageView);
            layoutX += 35;
        }
        layoutX = 150;
        layoutY = 70;
        for (int i = 0; i < 20; i++)
        {
            imageView = this.getAlien3();
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            this.getGameController().addToAllAliens(new Alien(imageView, this));
            this.pane.getChildren().add(imageView);
            layoutX += 35;
        }
        layoutX = 150;
        layoutY = 100;
        for (int i = 0; i < 20; i++)
        {
            imageView = this.getAlien4();
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            this.getGameController().addToAllAliens(new Alien(imageView, this));
            this.pane.getChildren().add(imageView);
            layoutX += 35;
        }
        layoutX = 150;
        layoutY = 130;
        for (int i = 0; i < 20; i++)
        {
            imageView = this.getAlien5();
            imageView.setLayoutX(layoutX);
            imageView.setLayoutY(layoutY);
            this.getGameController().addToAllAliens(new Alien(imageView, this));
            this.pane.getChildren().add(imageView);
            layoutX += 35;
        }
        rowsOfAliens += 5;
    }

    private ArrayList<ImageView> checkDestroy(Arrow arrow)
    {
        return this.getGameController().checkDestroy(arrow);
    }


    private void setAliens()
    {
        ArrayList<Alien> allAliens = this.getGameController().getAllAliens();
        for (Alien alien : allAliens) {
            this.pane.getChildren().add(alien.getAlienImage());
        }
    }

    private void alienShoot()
    {
        try {
            GameController gameController = this.getGameController();
            Random random = new Random();
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!GameWindow.isGameOn())
                    {
                        gameController.getGameWindow().removeTimer(timer);
                        gameController.getGameWindow().removeTimerTask(this);
                        timer.cancel();
                        timer.purge();
                        this.cancel();
                        return;
                    }
                    for (int i = 0; i < 5; i++)
                    {
                        int rand = random.nextInt(gameController.getAllAliens().size());
                        gameController.getAllAliens().get(rand).shoot(gameController.getGameWindow());
                    }
                }
            };
            this.addTimer(timer);
            this.addTimerTask(timerTask);
            timer.scheduleAtFixedRate(timerTask, 5000, 20000);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addRowOfAliens()
    {
        if(rowsOfAliens % 5 == 0)
        {
            Platform.runLater(() -> {
                int layoutX;
                int layoutY;
                layoutX = 150;
                layoutY = 50;
                ImageView imageView = imageView = this.getAlien3();
                for (int i = 0; i < 20; i++)
                {
                    imageView.setTranslateX(layoutX);
                    imageView.setTranslateY(layoutY);
                    this.getGameController().addToAllAliens(new Alien(imageView, this));
                    this.pane.getChildren().add(imageView);
                    layoutX += 35;
                }
            });
        }
    }

    public void endGame()
    {
        GameWindow.setGameOn(false);
        if (Account.getLoggedInAccount() != null && this.score > Account.getLoggedInAccount().getHighScore())
        {
            Account.getLoggedInAccount().setHighScore(score);
        }
        try {
            mainMusic.pause();
            for (Timer timer : allTimers) {
                timer.cancel();
                timer.purge();
            }
            for (TimerTask timerTask : allTimerTasks) {
                timerTask.cancel();
            }
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GameOverWindow.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Main.getStage().setScene(new Scene(root));
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addShields()
    {
        Shield shield1 = new Shield(50, 100, 20, 400);
        shield1.getRectangle().setFill(Color.GREEN);
        this.gameController.addShield(shield1);
        this.pane.getChildren().add(shield1.getRectangle());

        Shield shield2 = new Shield(50, 100, 300, 350);
        shield2.getRectangle().setFill(Color.GREEN);
        this.gameController.addShield(shield2);
        this.pane.getChildren().add(shield2.getRectangle());

        Shield shield3 = new Shield(50, 100, 600, 400);
        shield3.getRectangle().setFill(Color.GREEN);
        this.gameController.addShield(shield3);
        this.pane.getChildren().add(shield3.getRectangle());

        Shield shield4 = new Shield(50, 100, 850, 350);
        shield4.getRectangle().setFill(Color.GREEN);
        this.gameController.addShield(shield4);
        this.pane.getChildren().add(shield4.getRectangle());
    }

    private void getMainMusic()
    {
        Media media = new Media(new File("images/mainmusic.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mainMusic = mediaPlayer;
        mediaPlayer.setAutoPlay(true);
    }

    private void endGameWithWin()
    {
        GameWindow.setGameOn(false);
        if (Account.getLoggedInAccount() != null && this.score > Account.getLoggedInAccount().getHighScore())
        {
            Account.getLoggedInAccount().setHighScore(score);
        }
        try {
            mainMusic.pause();
            for (Timer timer : allTimers) {
                timer.cancel();
                timer.purge();
            }
            for (TimerTask timerTask : allTimerTasks) {
                timerTask.cancel();
            }
            Platform.runLater(() -> {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../GameWinWindow.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    Main.getStage().setScene(new Scene(root));
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            });
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}