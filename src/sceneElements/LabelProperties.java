package sceneElements;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LabelProperties {

    /**
     * Sets the properties for the label
     *
     * @param label     - the label we will use
     * @param name      - the name of the label
     * @param posX      - the x position of the label
     * @param posY      - the y position of the label
     * @param imageView - the image placed on the label
     */
    public void setLabelProperties(Label label, String name, double posX, double posY, ImageView imageView) {
        label.setText(name);
        label.setLayoutX(posX);
        label.setLayoutY(posY);
        label.setGraphic(imageView);
    }
}
