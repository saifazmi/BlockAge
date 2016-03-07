package test;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public int HEIGHT = 300;
    public int WIDTH = 400;
    private static ArrayList<SortVisualBar> blocks = new ArrayList<SortVisualBar>();
    SequentialTransition seq = new SequentialTransition();

    public double[] locations = new double[10];
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
            blocks.add(block);
        }
        //hardcoding positions
        for(int x = 0 ; x<10;x++){
            locations[x]=40+(x*30);
        }
        calibratePositions();
        Scene scene = new Scene(new Group(canvas));
        stage = new Stage();
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        // trying to move block
        swapFF(1,10);
        //swapFF(1,10);
        seq.play();

    }
    //sets each block's logical position according to it's coordinate on Layout. should still be 40 70..+30s
    public void calibratePositions(){
        for(int x=0 ; x<blocks.size();x++){
            blocks.get(x).setLogicalPosition(findLogicalLocation(blocks.get(x).getLayoutX()));
        }
    }
    //returns logical pos of coordinate
    public int findLogicalLocation(double s){
        for(int x = 0 ; x<10;x++){
            if(locations[x]==s)return x;
        }
        return -1;
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
        //newX confirmed works for first case always
        int b1LogPos = b1.getLogicalPosition();
        int b2LogPos = b2.getLogicalPosition();
        System.out.println("ten logical position "+ b2LogPos);

        //new way
        //each sortvisualbar is connected to it's correct number, when swapping
        //assing a logical position to direct correct placement
        Timeline b1Up = new Timeline();
        b1Up.setCycleCount(1);
        final KeyValue kv = new KeyValue(b1.yProperty(), -200);
        final KeyFrame kf = new KeyFrame(Duration.millis(250), kv);
        b1Up.getKeyFrames().add(kf);

        Timeline b1L = new Timeline();
        b1L.setCycleCount(1);
        final KeyValue kvL = new KeyValue(b1.xProperty(), -locations[b1LogPos]);
        final KeyFrame kfL = new KeyFrame(Duration.millis(250), kvL);
        b1L.getKeyFrames().add(kfL);

        Timeline b2Up = new Timeline();
        b2Up.setCycleCount(1);
        final KeyValue kv2 = new KeyValue(b2.yProperty(), -200);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(250), kv2);
        b2Up.getKeyFrames().add(kf2);

        Timeline b2R = new Timeline();
        b2R.setCycleCount(1);
        final KeyValue kv2R = new KeyValue(b2.xProperty(), -(locations[b2LogPos] - locations[b1LogPos]));
        final KeyFrame kf2R = new KeyFrame(Duration.millis(250), kv2R);
        b2R.getKeyFrames().add(kf2R);

        Timeline b2Down = new Timeline();
        b2Down.setCycleCount(1);
        final KeyValue kv2D = new KeyValue(b2.yProperty(), 0);
        final KeyFrame kf2D = new KeyFrame(Duration.millis(250), kv2D);
        b2Down.getKeyFrames().add(kf2D);


        Timeline b1R = new Timeline();
        b1R.setCycleCount(1);
        final KeyValue kvR = new KeyValue(b1.xProperty(), locations[b2LogPos]-40);
        final KeyFrame kfR = new KeyFrame(Duration.millis(250), kvR);
        b1R.getKeyFrames().add(kfR);

        Timeline b1Down = new Timeline();
        b1Down.setCycleCount(1);
        final KeyValue kvD = new KeyValue(b1.yProperty(), 0);
        final KeyFrame kfD = new KeyFrame(Duration.millis(250), kvD);
        b1Down.getKeyFrames().add(kfD);

        b1.setLogicalPosition(b2LogPos);
        b2.setLogicalPosition(b1LogPos);
        b1Down.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seq = new SequentialTransition();

                if(block1!=3){
                    swapFF(3,10);
                    b1.setX(locations[b2LogPos]);
                    b2.setX(locations[b1LogPos]);
                    seq.play();
                }
            }
        });
        //sync problem, real X doesnt change for next transition prepared.

        seq.getChildren().add(b1Up);
        seq.getChildren().add(b1L);
        seq.getChildren().add(b2Up);
        seq.getChildren().add(b2R);
        seq.getChildren().add(b2Down);
        seq.getChildren().add(b1R);
        seq.getChildren().add(b1Down);

    }
}