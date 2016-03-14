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

    // Number of elements in the sort list
    private static final int SORT_ELEMENT_QTY = 10;

    // List to sort to get pass blockade
    private List<Integer> toSortArray;

    /**
     * Builds a sortable blockade with the given attributes.
     *
     * @param id the ID of this entity
     * @param name name of the entity
     * @param position position of the entity on the graph
     * @param sprite the sprite image representing this entity
     */
    public SortableBlockade(int id, String name, GraphNode position, SpriteImage sprite, List<Integer> toSortArray) {

        super(id, name, position, sprite);
        this.toSortArray = toSortArray;
        this.breakable = true;
    }

    /**
     * Creates a new blockade with given properties
     *
     * @param sortableBlockadeInstance blockade properties to use
     * @return the blockade created
     */
    public static SortableBlockade create(SortableBlockade sortableBlockadeInstance) {

        GraphNode node = CoreEngine.Instance().getGraph().nodeWith(sortableBlockadeInstance.getPosition());

        if (node != null && !node.equals(new GraphNode(0, 0))) {

            SortableBlockade blockade = new SortableBlockade(
                    calcId(),
                    sortableBlockadeInstance.getName(),
                    node,
                    sortableBlockadeInstance.getSprite(),
                    generateUniqSortArray()
            );

            if (blockade.getPosition().getBlockade() == null &&
                    blockade.getPosition().getBase() == null &&
                    blockade.getPosition().getUnits().size() == 0) {

                blockade.getPosition().setBlockade(blockade);

                return blockade;
            }
        }

        return null;
    }

    /**
     * Gets the list that needs to be sorted
     * @return a List of Integers
     */
    public List<Integer> getToSortArray() {

        return this.toSortArray;
    }

    /**
     * Generates a list of randomly generated integers
     *
     * @return a List of randomly generated Integers
     */
    private static List<Integer> generateSortArray() {

        List<Integer> arrToSort = new ArrayList<>(SORT_ELEMENT_QTY);
        Random generator = new Random();

        for (int i = 0; i < SORT_ELEMENT_QTY; i++) {
            arrToSort.add(generator.nextInt(SORT_ELEMENT_QTY));
        }

        LOG.log(Level.INFO, arrayToString(arrToSort));

        return arrToSort;
    }

    /**
     * Generates a list of randomly generated unique elements
     *
     * @return a List of randomly generated Integers
     */
    private static List<Integer> generateUniqSortArray() {

        List<Integer> arrToSort = new ArrayList<>(SORT_ELEMENT_QTY);

        for (int i = 1; i <= SORT_ELEMENT_QTY; i++) {
            arrToSort.add(i);
        }

        Collections.shuffle(arrToSort);
        LOG.log(Level.INFO, arrayToString(arrToSort));

        return arrToSort;
    }

    /**
     * A toString function for the randomly generated list of integers
     * @param intList list to be converted to string representation
     * @return a string representing the list passed in
     */
    private static String arrayToString(List<Integer> intList) {

        StringBuilder sb = new StringBuilder();
        sb.append("ARRAY = [");

        for (Integer e : intList) {

            sb.append(Integer.toString(e));
            sb.append(",");
        }

        sb.append("]");

        return sb.toString();
    }
}
