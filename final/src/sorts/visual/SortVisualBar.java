package sorts.visual;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author : Evgeniy Kim; Contributors - Dominic Walters and Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Custom visual object extending Rectangle, which stores a value to help track
 *          what the object is in accordance to the sort, and has an additional x value
 *          specifically for updating logical location. (Normal javaFX x values are not adequate)
 * @date : 19/02/16
 */
public class SortVisualBar extends Rectangle {

    private int value;
    private double updateX;

    /**
     * Constructor for sort visual bars
     *
     * @param width  the width of the bar
     * @param height the height of the bar
     * @param fill   the colour of the bar
     * @param value  the value associated to the bar
     */
    public SortVisualBar(double width, double height, Paint fill, int value) {

        super(width, height, fill);
        this.value = value;
        this.setStroke(Color.BLACK);
    }

    // GETTER method

    /**
     * gets the updateX value
     *
     * @return the value
     */
    public double getUpdateX() {

        return this.updateX;
    }

    /**
     * gets the value value
     *
     * @return the value
     */
    public int getValue() {

        return this.value;
    }

    // SETTER methods

    /**
     * sets the updateX value
     *
     * @param updateX the value to set to
     */
    public void setUpdateX(double updateX) {

        this.updateX = updateX;
    }

    /**
     * sets the value value
     *
     * @param value the value to set to
     */
    public void setValue(int value) {

        this.value = value;
    }
}
