package sceneElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import maps.MapEditorInterface;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 * <p>
 * This class makes the setting of the button properties more easily
 */
public class ButtonProperties {

    /**
     * Sets the button properties
     *
     * @param button       the button to be set
     * @param posX         the x coord of the button
     * @param posY         the y coord of the button
     * @param eventHandler the event that will happen
     * @param imgView      the image set on the button
     */
    public void setButtonProperties(Button button, double posX, double posY, EventHandler<ActionEvent> eventHandler, ImageView imgView) {

        button.setLayoutX(posX);
        button.setLayoutY(posY);
        button.setOnAction(eventHandler);
        button.setGraphic(imgView);
        button.setBackground(null);
    }

    /**
     * Adds an on mouse entered effect
     *
     * @param button        the button that will be hovered
     * @param hoverImageIn  the image that will be changed into when mouse entered
     * @param hoverImageOut the image that will be change into when mouse exited
     */
    public void addHoverEffect(Button button, Image hoverImageIn, Image hoverImageOut, double posX, double posY) {

        button.setOnMousePressed(event -> button.getScene().setCursor(Cursor.DEFAULT));

        button.setOnMouseEntered(event -> {

            setButtonProperties(
                    button,

                    posX,
                    posY,
                    ElementsHandler::handle,
                    new ImageView(hoverImageIn)
            );

            button.getScene().setCursor(Cursor.HAND);
            button.setScaleX(1.1);
            button.setScaleY(1.1);
        });

        button.setOnMouseExited(event -> {

            setButtonProperties(
                    button,
                    posX,
                    posY,
                    ElementsHandler::handle,
                    new ImageView(hoverImageOut)
            );

            button.getScene().setCursor(Cursor.DEFAULT);
            button.setScaleX(1);
            button.setScaleY(1);
        });
    }

    /**
     * Adds an on mouse entered effect as above, but for buttons
     *
     * @param button        the button that will be hovered
     * @param hoverImageIn  the image that will be changed into when mouse entered
     * @param hoverImageOut the image that will be change into when mouse exited
     */
    public void addHoverEffect2(Button button, Image hoverImageIn, Image hoverImageOut, double posX, double posY) {

        button.setOnMouseEntered(event -> {

            button.setScaleX(1.1);
            button.setScaleY(1.1);

            setButtonProperties(
                    button,
                    posX,
                    posY,
                    MapEditorInterface::handle,
                    new ImageView(hoverImageIn)
            );

            button.getScene().setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {

            button.setScaleX(1);
            button.setScaleY(1);

            setButtonProperties(
                    button,
                    posX,
                    posY,
                    MapEditorInterface::handle,
                    new ImageView(hoverImageOut)
            );

            button.getScene().setCursor(Cursor.DEFAULT);
        });
    }

    /**
     * Adds an on mouse entered effect
     *
     * @param button        the button that will be hovered
     */
    public void addHoverEffect3(Button button) {

        button.setOnMousePressed(event -> button.getScene().setCursor(Cursor.DEFAULT));
        button.setScaleX(1);
        button.setScaleY(1);

        button.setOnMouseEntered(event -> {

            button.getGraphic().setScaleX(1.02);
            button.getGraphic().setScaleY(1.02);
            button.getScene().setCursor(Cursor.HAND);
        });

        button.setOnMouseExited(event -> {

            button.getGraphic().setScaleX(1);
            button.getGraphic().setScaleY(1);
            button.getScene().setCursor(Cursor.DEFAULT);
        });
    }
}