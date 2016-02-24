package test;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import sorts.SortVisualBar;

import java.util.ArrayList;

/**
 * Created by Caitlyn PLS on 19-Feb-16.
 */
public class TestVisual extends Application {
    public int HEIGHT = 600;
    public int WIDTH = 400;
    private static ArrayList<SortVisualBar> blocks= new ArrayList<SortVisualBar>();
    public void start(Stage stage){
        //store blocks somewhere
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        canvas.setPrefSize(WIDTH,HEIGHT);
        //make blocks XO
        for(double x=0;x<10;x++){
            SortVisualBar block = new SortVisualBar(50.0,(x*30.0), Color.LIGHTBLUE,(int)x); //DO NOT change this color or i will END you
            block.relocate(20 + (x*60),HEIGHT - (x*30)); //COMPLETE CORRECT COORDS
            canvas.getChildren().add(block);
            blocks.add(block);
        }
        Scene scene = new Scene(new Group(canvas));
        stage = new Stage();
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        // trying to move block
        swapFF();


    }
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void swapFF(){

        double fourX = blocks.get(3).getX();
        double fourY = blocks.get(3).getY();

        double fiveX = blocks.get(4).getX();
        double fiveY = blocks.get(4).getY();

        TranslateTransition tt = new TranslateTransition(Duration.seconds(2), blocks.get(3));

        tt.setFromY(fourY);
        tt.setToY(-100);
        tt.setCycleCount(1);

        TranslateTransition tz = new TranslateTransition(Duration.seconds(2), blocks.get(3));

        tz.setFromX(blocks.get(3).getX());
        tz.setToX(-100);
        tz.setCycleCount(1);


        TranslateTransition ty = new TranslateTransition(Duration.seconds(2), blocks.get(3));

        tz.setFromY(blocks.get(3).getY());
        tz.setToY(100);
        tz.setCycleCount(1);
        SequentialTransition seqT = new SequentialTransition (blocks.get(3), tz);
        seqT.play();
    }
}
