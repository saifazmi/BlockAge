package entity;

import core.CoreEngine;
import graph.GraphNode;
import sceneElements.SpriteImage;
import sorts.visual.SortVisual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 27/02/16
 */
public class SortableBlockade extends Blockade {

    private static final Logger LOG = Logger.getLogger(SortableBlockade.class.getName());

    // Maximum number of elements in array to be sorted
    private static final int SORT_ELEMENT_QTY = 10;

    // Array to be sorted
    private ArrayList<Integer> toSortArray;

    // Dependencies
    private static int sortID;
    private SortVisual sortVisual = null;

    /**
     * Builds a sortable blockade with the given attributes.
     *
     * @param id       the ID of this entity
     * @param name     name of the entity
     * @param position position of the entity on the graph
     * @param sprite   the sprite image representing this entity
     */
    public SortableBlockade(int id, String name, GraphNode position, SpriteImage sprite, ArrayList<Integer> toSortArray) {

        super(id, name, position, sprite);
        this.toSortArray = toSortArray;
        setBreakable(true);
    }

    // GETTER methods

    /**
     * Gets a list to be sorted
     *
     * @return a list of integers to be sorted
     */
    public ArrayList<Integer> getToSortArray() {

        return this.toSortArray;
    }

    /**
     * Gets the sort visualisation for this blockade
     *
     * @return a sort visualisation
     */
    public SortVisual getSortVisual() {

        return this.sortVisual;
    }

    //@TODO: never used, delete?
    public static int getSortID() {

        return sortID;
    }

    // SETTER methods.

    /**
     * Sets the sort visualisation for this blockade
     *
     * @param sortVisual a new sort visualisation for this blockade
     */
    public void setSortVisual(SortVisual sortVisual) {

        this.sortVisual = sortVisual;
    }

    //@TODO: never used, delete?
    public static void setSortID(int sortID) {

        SortableBlockade.sortID = sortID;
    }

    /**
     * Creates a new sortable blockade
     *
     * @param sortableBlockadeInstance the sortable blockade properties
     * @return the blockade created else null
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
     * Generates a random list of integers containing unique elements
     *
     * @return a random list of integers
     */
    private static ArrayList<Integer> generateUniqSortArray() {

        ArrayList<Integer> arrToSort = new ArrayList<>(SORT_ELEMENT_QTY);

        for (int i = 0; i < SORT_ELEMENT_QTY; i++) {
            arrToSort.add(i);
        }

        Collections.shuffle(arrToSort);
        LOG.log(Level.INFO, arrayToString(arrToSort));

        return arrToSort;
    }

    /**
     * A toString for the generated list of integers
     *
     * @param arrList the list to be printed
     * @return a string representation of the list
     */
    public static String arrayToString(List<Integer> arrList) {

        StringBuilder sb = new StringBuilder();
        sb.append("ARRAY = [");

        for (Integer e : arrList) {
            sb.append(Integer.toString(e));
            sb.append(";");
        }

        sb.append("]");

        return sb.toString();
    }
}
