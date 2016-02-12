package sceneElements;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ButtonProperties {

    /**
     * Sets the button properties
     *
     * @param button - the button to be set
     * @param name   - the text on the button
     * @param posX   - the x coord of the button
     * @param posY   - the y coord of the button
     * @param value  - the event that will happen
     */
    public void setButtonProperties(Button button, String name, double posX, double posY, EventHandler<ActionEvent> value) {
        button.setText(name);
        button.setLayoutX(posX);
        button.setLayoutY(posY);
        button.setOnAction(value);
    }

    /**
     * Sets the button properties
     *
     * @param button       - the button to be set
     * @param name         - the text on the button
     * @param posX         - the x coord of the button
     * @param posY         - the y coord of the button
     * @param eventHandler - the event that will happen
     * @param imgView      - the image set on the button
     */
    public void setButtonProperties(Button button, String name, double posX, double posY, EventHandler<ActionEvent> eventHandler, ImageView imgView) {
        button.setText(name);
        button.setLayoutX(posX);
        button.setLayoutY(posY);
        button.setOnAction(eventHandler);
        button.setGraphic(imgView);
        button.setBackground(null);
    }

    /**
     * Adds an on mouse entered effect
     *
     * @param button        - the button that will be hovered
     * @param hoverImageIn  - the image that will be changed into when mouse entered
     * @param hoverImageOut - the image that will be change into when mouse exited
     */
    public void addHoverEffect(Button button, Image hoverImageIn, Image hoverImageOut, double posX, double posY) {
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setButtonProperties(button, "", posX, posY, e -> ElementsHandler.handle(e), new ImageView(hoverImageIn));
                button.getScene().setCursor(Cursor.HAND);
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setButtonProperties(button, "", posX, posY, e -> ElementsHandler.handle(e), new ImageView(hoverImageOut));
                button.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

}