package sorts;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 */
public class SortVisualBar extends Rectangle {
    private int value;
    private double newX;
    private double newY;

    public double getNewX() {
        return newX;
    }

    public void setNewX(double newX) {
        this.newX = newX;
    }

    public double getNewY() {
        return newY;
    }

    public void setNewY(double newY) {
        this.newY = newY;
    }


    public SortVisualBar(double width, double height, Paint fill, int value) {
        super(width, height, fill);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
