package entity;

import core.GameRunTime;
import graph.GraphNode;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

/**
 * Created by user on 24/02/2016.
 */
public class Base extends Entity {

    public Base(int id, String name, GraphNode position, SpriteImage sprite) {
        super(id, name, position, sprite);
    }

    @Override
    public void update() { }
}