package sorts;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 */
public class SortVisual {

    public SortVisual(int size) {
        Pane canvas = new Pane();
        canvas.setStyle("-fx-background-color: white;");
        //canvas.setPrefSize();
        //make blocks XO
        for (double x = 0; x < size; x++) {              //width  //height
            SortVisualBar block = new SortVisualBar(10.0, 10.0 + (x * 10.0), Color.LIGHTBLUE, (int) x); //DO NOT change this color or i will END you
            //x val    //y val
            block.relocate(10 + (x * 20), 20); //COMPLETE CORRECT COORDS
            canvas.getChildren().add(block);
        }

    }
}
