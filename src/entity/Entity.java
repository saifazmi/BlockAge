package entity;

import graph.GraphNode;
import sceneElements.SpriteImage;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Dominic Walters on 20/02/16
 */
public abstract class Entity extends Observable {

    private static final Logger LOG = Logger.getLogger(Entity.class.getName());

    // Entity properties
    protected final int id;
    protected String name;
    protected GraphNode position;
    protected SpriteImage sprite;

    /**
     * Builds an entity with the given attributes.
     *
     * @param id       the ID of this entity
     * @param name     name of the entity
     * @param position position of the entity on the graph
     * @param sprite   the sprite image representing this entity
     */
    public Entity(int id, String name, GraphNode position, SpriteImage sprite) {

        this.id = id;
        this.name = name;
        this.position = position;
        this.sprite = sprite;
    }

    // GETTER methods

    /**
     * Gets the ID of entity
     *
     * @return ID of the Entity
     */
    public int getId() {

        return this.id;
    }

    /**
     * Gets the name of entity
     *
     * @return name of the Entity
     */
    public String getName() {

        return this.name;
    }

    /**
     * Gets the position of entity
     *
     * @return position of the Entity
     */
    public GraphNode getPosition() {

        return this.position;
    }

    /**
     * Gets the sprite image representing the entity
     *
     * @return SpriteImage for this Entity
     */
    public SpriteImage getSprite() {

        return this.sprite;
    }

    // SETTER methods

    /**
     * Sets the position of entity
     *
     * @param position new GraphNode position of Entity
     */
    public void setPosition(GraphNode position) {

        this.position = position;
    }

    /**
     * Sets the sprite representing this entity
     *
     * @param sprite new SpriteImage of Entity
     */
    public void setSprite(SpriteImage sprite) {

        this.sprite = sprite;
    }

    //@TODO: document this method

    /**
     *
     */
    public abstract void update();

    /**
     * Checks if a given object is equal to this Entity
     *
     * @param o object to be compared
     * @return true if the object is equal to this Entity else false
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (id != entity.id) return false;
        if (name != null
                ? !name.equals(entity.name)
                : entity.name != null) return false;

        if (!position.equals(entity.position)) return false;

        return sprite.equals(entity.sprite);
    }

    @Override
    public int hashCode() {

        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + position.hashCode();
        result = 31 * result + sprite.hashCode();

        return result;
    }
}
