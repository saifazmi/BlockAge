package sorts;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Created by Caitlyn PLS on 19-Feb-16.
 */
public class SortVisualBar extends Rectangle{
    private int value;

    public SortVisualBar(double width, double height, Paint fill, int value){
        super(width,height,fill);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
