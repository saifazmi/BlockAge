package sceneElements;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author : Dominic Walters; Contributors - Dominic Walters
 * @version : 23/03/2016;
 *          <p>
 *          This class associates an entity to an image.
 * @date : 05/02/16
 */
public class SpriteImage extends ImageView {

    private Entity entity;

    /**
     * Constructs a sprite image to display
     *
     * @param image  the image to use
     * @param entity the entity that the image relates to
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
