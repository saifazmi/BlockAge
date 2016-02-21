package entity;

import graph.GraphNode;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Dominic on 11/02/2016.
 */
public class BreakableBlockade extends Blockade {
    private static final Logger LOG = Logger.getLogger(BreakableBlockade.class.getName());

    private int sortSpeed;
    private ArrayList<Comparable> listToSort;

    public BreakableBlockade(int id, String name, GraphNode position, SpriteImage sprite, int sortSpeed, ArrayList<Comparable> listToSort) {
        super(id, name, position, sprite);
        setBreakable(true);
        this.sortSpeed = sortSpeed;
        this.listToSort = listToSort;
    }

    public BreakableBlockade(int id, String name, String description, GraphNode position, SpriteImage sprite, int sortSpeed, ArrayList<Comparable> listToSort) {
        super(id, name, description, position, sprite);
        setBreakable(true);
        this.sortSpeed = sortSpeed;
        this.listToSort = listToSort;
    }

    public static Blockade createBlockade(MouseEvent e, BreakableBlockade blockadeInstance) {
        return new BreakableBlockade(calcId(), blockadeInstance.getName(), calcGraphNode(e), blockadeInstance.getSprite(), blockadeInstance.getSortSpeed(), blockadeInstance.getListToSort());
    }

    public int getSortSpeed() {
        return sortSpeed;
    }

    public ArrayList<Comparable> getListToSort() {
        return listToSort;
    }
}
