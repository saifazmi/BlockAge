package entity;

import graph.GraphNode;
import sceneElements.SpriteImage;

/**
 * @author : Anh Pham
 * @version : 23/03/2016;
 *          <p>
 *          Defines a base data structure.
 *          This represents the goal for the game.
 * @date : 24/02/16
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
}