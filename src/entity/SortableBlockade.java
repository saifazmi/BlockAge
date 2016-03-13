package entity;

import core.CoreEngine;
import graph.GraphNode;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 27/02/16
 */
public class SortableBlockade extends Blockade {

    private static final Logger LOG = Logger.getLogger(SortableBlockade.class.getName());
    private static final int SORT_ELEMENT_QTY = 10;

    private List<Integer> toSortArray;

    public SortableBlockade(int id, String name, GraphNode position, SpriteImage sprite, List<Integer> toSortArray) {
        super(id, name, position, sprite);
        this.toSortArray = toSortArray;
        setBreakable(true);
    }

    public static SortableBlockade create(SortableBlockade sortableBlockadeInstance) {
        GraphNode node = CoreEngine.Instance().getGraph().nodeWith(sortableBlockadeInstance.getPosition());
        if (node != null && !node.equals(new GraphNode(0, 0))) {
            SortableBlockade blockade = new SortableBlockade(calcId(), sortableBlockadeInstance.getName(), node, sortableBlockadeInstance.getSprite(), generateUniqSortArray());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getBase() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

    private static List<Integer> generateSortArray() {

        List<Integer> arrToSort = new ArrayList<>(SORT_ELEMENT_QTY);
        Random generator = new Random();

        for (int i = 0; i < SORT_ELEMENT_QTY; i++) {
            arrToSort.add(generator.nextInt(SORT_ELEMENT_QTY));
        }

        LOG.log(Level.INFO, arrayToString(arrToSort));
        return arrToSort;
    }

    private static List<Integer> generateUniqSortArray() {

        List<Integer> arrToSort = new ArrayList<>(SORT_ELEMENT_QTY);

        for (int i = 0; i < SORT_ELEMENT_QTY; i++) {
            arrToSort.add(i);
        }

        Collections.shuffle(arrToSort);
        LOG.log(Level.INFO, arrayToString(arrToSort));
        return arrToSort;
    }

    private static String arrayToString(List<Integer> arrList) {

        StringBuilder sb = new StringBuilder();
        sb.append("ARRAY = [");

        for (Integer e : arrList) {
            sb.append(Integer.toString(e));
            sb.append(";");
        }

        sb.append("]");
        return sb.toString();
    }

    public List<Integer> getToSortArray() {
        return toSortArray;
    }
}
