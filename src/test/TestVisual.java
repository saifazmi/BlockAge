package test;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sorts.SortVisualBar;

/**
 * Created by Caitlyn PLS on 19-Feb-16.
 */
public class TestVisual extends Application {

    public void start(Stage stage){

        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        canvas.setPrefSize(300,300);
        //make blocks XO
        for(double x=0;x<10;x++){
            SortVisualBar block = new SortVisualBar(50.0,10.0+(x*30.0), Color.LIGHTBLUE,(int)x); //DO NOT change this color or i will END you
            block.relocate(10 + (x*60),300-(10+(x*30))); //COMPLETE CORRECT COORDS
            canvas.getChildren().add(block);
        }
        Scene scene = new Scene(new Group(canvas));
        stage = new Stage();
        stage.setTitle("TEST");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
