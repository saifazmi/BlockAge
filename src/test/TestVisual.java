package test;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sorts.visual.SortVisualBar;

import java.util.ArrayList;


/**
 * Created by Caitlyn PLS on 19-Feb-16.
 */
public class TestVisual extends Application {
    public int HEIGHT = 300;
    public int WIDTH = 400;
    private static ArrayList<SortVisualBar> blocks = new ArrayList<SortVisualBar>();

    public void start(Stage stage) {
        //store blocks somewhere
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        canvas.setPrefSize(WIDTH, HEIGHT);
//        //make blocks XO
//        int counterx = 0;
//        int countery = 0;
        for (double x = 0; x < 11; x++) {           //w     //h

            SortVisualBar block = new SortVisualBar(25.0, (x * 15.0), Color.LIGHTBLUE, (int) x); //DO NOT change this color or i will END you

            //x                y
            block.relocate(10 + (x * 30), HEIGHT - (x * 15) - 50); //COMPLETE CORRECT COORDS
//            counterx += 20+(x*60);
//            countery += HEIGHT-(x*30);
            canvas.getChildren().add(block);
            block.setUpdateX(block.getLayoutX());
            blocks.add(block);
        }
        //System.out.println(counterx);
        //System.out.println(countery);
        Scene scene = new Scene(new Group(canvas));
        stage = new Stage();
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        // trying to move block
        swapFF(3, 10);


    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * General Pattern:
     * Make transition, add to constructor of SequentialTransition
     */
    public void swapFF(int block1, int block2) {

        SortVisualBar b1 = blocks.get(block1);
        SortVisualBar b2 = blocks.get(block2);

        b1.relocate(b1.getUpdateX(), b1.getLayoutY());
        b2.relocate(b2.getUpdateX(), b2.getLayoutY());

        double oldX = b1.getLayoutX();
        double oldSecondX = b2.getLayoutX();

        System.out.println(block1 + " is at : " + b1.getLayoutX());
        System.out.println(block2 + " is at : " + b2.getLayoutX());
        // first block , 3 transitions
        TranslateTransition tty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        tty.setFromY(0);
        tty.setToY(-100);

        TranslateTransition ttx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        ttx.setFromX(0);
        ttx.setToX(-oldX);//was -200

        TranslateTransition txx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        txx.setFromY(-100);
        txx.setToY(0);

        TranslateTransition ty = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        ty.setFromY(0);
        ty.setToY(-200);

        TranslateTransition tx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        tx.setFromX(0);
        tx.setToX(-(oldSecondX - (oldX)));

        TranslateTransition txt = new TranslateTransition(Duration.seconds(0.25), blocks.get(block2));
        txt.setFromY(-200);
        txt.setToY(0);

        //last 3
        TranslateTransition gy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gy.setFromY(0);
        gy.setToY(-200);

        TranslateTransition gx = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gx.setFromX(-oldX);
        gx.setToX(oldSecondX - oldX);//old distance-  width - gap

        TranslateTransition gyy = new TranslateTransition(Duration.seconds(0.25), blocks.get(block1));
        gyy.setFromY(-200); //this is how it works...dont ask
        gyy.setToY(0);
        gyy.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //if(block1!=10){


                //b1.relocate(oldSecondX,b1.getLayoutY());
                //b2.relocate(oldX,b2.getLayoutY());
                b1.setUpdateX(oldSecondX);
                b2.setUpdateX(oldX);
                System.out.println(block1 + " changed to : " + b1.getLayoutX());
                System.out.println(block2 + " changed to : " + b2.getLayoutX());
                swapFF(7, 6);
                //}
            }
        });

        SequentialTransition seq = new SequentialTransition(blocks.get(block2), tty, ttx, txx, ty, tx, txt, gy, gx, gyy);
        seq.play();

    }
}