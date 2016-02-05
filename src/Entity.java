import javafx.scene.Node;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public abstract class Entity extends Observable {

    private static final Logger LOG = Logger.getLogger(Entity.class.getName());

    private final int id;
    private String name;
    private String description;
    private GraphNode position;
    private SpriteImage sprite;

    public Entity(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.position = position;
        this.sprite = sprite;
    }

    public Entity(int id, String name, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.description = null;
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GraphNode getPosition() {
        return position;
    }

    public SpriteImage getSprite() {
        return sprite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSprite(SpriteImage sprite) {
        this.sprite = sprite;
    }

    public void setPosition(GraphNode position) {
        Entity oldEntity = this;
        this.position = position;
        setChanged();
        notifyObservers(oldEntity);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
