package entity;

import graph.GraphNode;
import sceneElements.SpriteImage;

/**
 * @author : First created by Anh Pham with code by Anh Pham
 * @date : 24/02/16, last edited by Paul Popa on 23/02/16
 */
public class Base extends Entity {

    /**
     * Builds a base with the given attributes.
     *
     * @param id       the ID of this entity
     * @param name     name of the entity
     * @param position position of the entity on the graph
     * @param sprite   the sprite image representing this entity
     */
    public Base(int id, String name, GraphNode position, SpriteImage sprite) {

        super(id, name, position, sprite);
    }

    /**
     * Abstract method from Entity that required implementation.
     * Left empty as nothing is necessary.
     */
    @Override
    public void update() {

        // Nothing to update.
    }
}