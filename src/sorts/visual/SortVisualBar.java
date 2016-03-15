package sorts.visual;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 */
public class SortVisualBar extends Rectangle {
    private int value;
    private double updateX;

    public double getUpdateX() {
        return updateX;
    }

    public void setUpdateX(double updateX) {
        this.updateX = updateX;
    }

    public int getLogicalPosition() {
        return logicalPosition;
    }

    public void setLogicalPosition(int logicalPosition) {
        this.logicalPosition = logicalPosition;
    }

    private int logicalPosition;

    public SortVisualBar(double width, double height, Paint fill, int value) {
        super(width, height, fill);
        this.value = value;
        this.setStroke(Color.BLACK);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
