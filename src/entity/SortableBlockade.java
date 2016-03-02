package entity;

import core.CoreEngine;
import graph.GraphNode;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 27/02/16
 */
public class SortableBlockade extends Blockade {

    private static final Logger LOG = Logger.getLogger(SortableBlockade.class.getName());

    private static final int SORT_ELEMENT_QTY = 10;

    private int[] toSortArray;

    public SortableBlockade(int id, String name, GraphNode position, SpriteImage sprite, int[] toSortArray) {
        super(id, name, position, sprite);
        this.toSortArray = toSortArray;
    }

    public SortableBlockade(int id, String name, String description, GraphNode position, SpriteImage sprite, int[] toSortArray) {
        super(id, name, description, position, sprite);
        this.toSortArray = toSortArray;
    }

    public static SortableBlockade create(SortableBlockade sortableBlockadeInstance) {
        GraphNode node = CoreEngine.Instance().getGraph().nodeWith(sortableBlockadeInstance.getPosition());
        if (node != null && !node.equals(new GraphNode(0, 0))) {
            SortableBlockade blockade = new SortableBlockade(calcId(), sortableBlockadeInstance.getName(), node, sortableBlockadeInstance.getSprite(), generateSortArray());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getBase() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

    private static int[] generateSortArray() {

        int[] toSortArr = new int[SORT_ELEMENT_QTY];
        Random generator = new Random();

        for (int i = 0; i < SORT_ELEMENT_QTY; i++) {
            toSortArr[i] = generator.nextInt(SORT_ELEMENT_QTY);
        }

        return toSortArr;
    }

    public int[] getToSortArray() {
        return toSortArray;
    }
}
