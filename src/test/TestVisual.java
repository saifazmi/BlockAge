package test;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private static ArrayList<SortVisualBar> blocks = new ArrayList<SortVisualBar>();

    public void start(Stage stage) {
        //store blocks somewhere
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        canvas.setPrefSize(WIDTH, HEIGHT);
        //make blocks XO
        for (double x = 0; x < 10; x++) {
            SortVisualBar block = new SortVisualBar(50.0, (x * 30.0), Color.LIGHTBLUE, (int) x); //DO NOT change this color or i will END you
            block.relocate(20 + (x * 60), HEIGHT - (x * 30)); //COMPLETE CORRECT COORDS
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

    /**
     * General Pattern:
     * Make transition, add to constructor of SequentialTransition
     */
    public void swapFF() {

        // first block , 3 transitions
        TranslateTransition tty = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        tty.setFromY(blocks.get(3).getY());
        tty.setToY(-100);
        tty.setCycleCount(1);

        TranslateTransition ttx = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        ttx.setFromX(blocks.get(3).getX());
        ttx.setToX(-200);
        ttx.setCycleCount(1);

        TranslateTransition txx = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        txx.setFromY(blocks.get(3).getY() - 100);
        txx.setToY(0);
        txx.setCycleCount(1);
        // second block, take note of values on the last transitions of each one
        //you can change the dimensions as you wish, easier to navigate if you have some sort of class-wide var for sizes
        TranslateTransition tx = new TranslateTransition(Duration.seconds(0.25), blocks.get(4));
        tx.setFromX(blocks.get(4).getY());
        tx.setToX(-60);
        tx.setCycleCount(1);

        TranslateTransition ty = new TranslateTransition(Duration.seconds(0.25), blocks.get(4));
        ty.setFromY(blocks.get(4).getY());
        ty.setToY(-200);
        ty.setCycleCount(1);

        TranslateTransition txt = new TranslateTransition(Duration.seconds(0.25), blocks.get(4));
        txt.setFromY(blocks.get(4).getY() - 200);
        txt.setToY(0);
        txt.setCycleCount(1);

        //last 3
        TranslateTransition gy = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        gy.setFromY(blocks.get(3).getY());
        gy.setToY(-200);
        gy.setCycleCount(1);

        TranslateTransition gx = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        gx.setFromX(-200);
        gx.setToX(60);
        gx.setCycleCount(1);

        TranslateTransition gyy = new TranslateTransition(Duration.seconds(0.25), blocks.get(3));
        gyy.setFromY(blocks.get(3).getY() - 200); //this is how it works...dont ask
        gyy.setToY(0);
        gyy.setCycleCount(1);

        SequentialTransition seq = new SequentialTransition(blocks.get(4), tty, ttx, txx, ty, tx, txt, gy, gx, gyy);
        seq.play();

    }
}
