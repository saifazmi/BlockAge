package sceneElements;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters
 * @date : 05/02/16, last edited by Dominic Walters on 05/02/16
 */
public class SpriteImage extends ImageView {

    private Entity entity;

    //@TODO: complete doc
    /**
     * Constructs an sprite image to display
     *
     * @param image
     * @param entity
     */
    public SpriteImage(Image image, Entity entity) {

        super(image);
        this.entity = entity;
    }

    /**
     * Gets the entity for this sprite image
     *
     * @return an Entity object
     */
    public Entity getEntity() {

        return this.entity;
    }

    /**
     * Sets the entity for this spire image
     *
     * @param entity the new entity for this sprite image
     */
    public void setEntity(Entity entity) {

        this.entity = entity;
    }
}
