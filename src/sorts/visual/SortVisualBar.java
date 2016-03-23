package sorts.visual;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author : Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * <p>
 * Custom visual object extending Rectangle, which stores a value to help track
 * what the object is in accordance to the sort, and has an additional x value
 * specifically for updating logical location. (Normal javaFX x values are not adequate)
 */
public class SortVisualBar extends Rectangle {

    private int value;
    private double updateX;

    //@TODO: every method needs doc
    // GETTER method

    /**
     *
     * @return
     */
    public double getUpdateX() {

        return this.updateX;
    }

    /**
     *
     * @return
     */
    public int getValue() {

        return this.value;
    }

    // SETTER methods

    /**
     *
     * @param updateX
     */
    public void setUpdateX(double updateX) {

        this.updateX = updateX;
    }

    /**
     *
     * @param value
     */
    public void setValue(int value) {

        this.value = value;
    }

    /**
     *
     * @param width
     * @param height
     * @param fill
     * @param value
     */
    public SortVisualBar(double width, double height, Paint fill, int value) {

        super(width, height, fill);
        this.value = value;
        this.setStroke(Color.BLACK);
    }
}
