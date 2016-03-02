package entity;

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

    private int[] toSort;

    public SortableBlockade(int id, String name, GraphNode position, SpriteImage sprite, int[] toSort) {
        super(id, name, position, sprite);
        this.toSort = toSort;
    }

    public SortableBlockade(int id, String name, String description, GraphNode position, SpriteImage sprite, int[] toSort) {
        super(id, name, description, position, sprite);
        this.toSort = toSort;
    }

    public SortableBlockade create(MouseEvent e, SortableBlockade sortableBlockadeInstance) {
        GraphNode node = calcGraphNode(e);
        if (node != null) {
            SortableBlockade blockade = new SortableBlockade(calcId(), sortableBlockadeInstance.getName(), calcGraphNode(e), sortableBlockadeInstance.getSprite(), generateSortArray());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }

        return null;
    }

    private int[] generateSortArray() {
        int elements = 10;
        int[] sortArr = new int[elements];
        Random generator = new Random();

        for (int i = 0; i < elements; i++) {
            sortArr[i] = generator.nextInt(elements);
        }

        return sortArr;
    }
}
