package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.plaf.basic.BasicButtonUI;
import java.util.ArrayList;

public class ScoreBoardController{
    public Button back;
    @FXML
    private VBox content;
    ArrayList<String> lines = Account.sortedAccounts();

    public void setScoreBoard()
    {
        content.getChildren().remove(back);
        Label mainLabel = new Label("Score Board");
        mainLabel.setFont(Font.font(30));
        mainLabel.setTextFill(Color.WHITE);
        mainLabel.setAlignment(Pos.CENTER);
        content.getChildren().add(mainLabel);
        ArrayList<String> lines = Account.sortedAccounts();
        for (String line : lines) {
            Label label = new Label(line);
            label.setTextFill(Color.GREEN);
            label.setFont(Font.font(20));
            label.setAlignment(Pos.CENTER);
            content.getChildren().add(label);
        }
        content.getChildren().add(back);
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
