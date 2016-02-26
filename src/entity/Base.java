package entity;

import graph.GraphNode;
import sceneElements.SpriteImage;

/**
 * @author : First created by Anh Pham with code by Anh Pham
 * @date : 24/02/16, last edited by Paul Popa on 23/02/16
 */
public class Base extends Entity {

    public Base(int id, String name, GraphNode position, SpriteImage sprite) {
        super(id, name, position, sprite);
    }

    @Override
    public void update() {
    }
}