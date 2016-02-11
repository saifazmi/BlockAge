package entity;

import javafx.scene.image.ImageView;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by walte on 05/02/2016.
 */
public class SpriteImage extends ImageView implements FocusListener {
    private Entity entity;

    public SpriteImage(String imageURL, Entity entity) {
        super(imageURL);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void focusGained(FocusEvent e) {
        //@TODO tell sidebar to redraw
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
