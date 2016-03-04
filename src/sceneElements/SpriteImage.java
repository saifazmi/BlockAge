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

    public SpriteImage(Image image, Entity entity) {
        super(image);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
