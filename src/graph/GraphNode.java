package graph;

import entity.Base;
import entity.Blockade;
import entity.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Hung Hoang on 02/02/16
 */
public class GraphNode {

    private static final Logger LOG = Logger.getLogger(GraphNode.class.getName());

    // X-coordinate of the node
    private int x;
    // Y-coordinate of the node
    private int y;

    // Properties
    private List<Unit> units;
    private Blockade blockade;
    private List<GraphNode> successors;
    private Base base;

    /**
     * Builds a GraphNode with the given coordinates
     *
     * @param x x-coordinate of the node
     * @param y y-coordinate of the node
     */
    public GraphNode(int x, int y) {

        this.x = x;
        this.y = y;
        this.units = new ArrayList<>();
        this.successors = new ArrayList<>();
        blockade = null;
        base = null;
    }

    /**
     *
     *
     * @param graph
     */
    public void addNeighbours(Graph graph) {
        for (int i = 0; i < graph.getNodes().size(); i++) {
            GraphNode node = graph.getNodes().get(i);
            double deltaX = Math.abs(this.x - node.getX());
            double deltaY = Math.abs(this.y - node.getY());

            if (!node.equals(this) &&
                    (deltaX <= 1 && deltaY <= 1) &&
                    (deltaX + deltaY) < 2) {
                addSuccessor(node, graph);
            }
        }
    }

    // GETTER methods

    /**
     * Gets the X-coordinate of this graph node
     *
     * @return the X-coordinate
     */
    public int getX() {

        return this.x;
    }

    /**
     * Gets the Y-coordinate of this graph node
     *
     * @return the Y-coordinate
     */
    public int getY() {

        return this.y;
    }

    /**
     * Gets the list of successors for this graph node
     *
     * @return a List of successive GraphNodes for this GraphNode
     */
    public List<GraphNode> getSuccessors() {

        return this.successors;
    }

    /**
     * Gets the blockade on this graph node
     *
     * @return a Blockade
     */
    public Blockade getBlockade() {

        return this.blockade;
    }

    /**
     *
     *
     * @param node
     * @param graph
     */
    public void addSuccessor(GraphNode node, Graph graph) {
        GraphNode successor = graph.nodeWith(node);
        this.successors.add(successor);
    }

    public void setBlockade(Blockade blockade) {
        this.blockade = blockade;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GraphNode{x=").append(x).append(", y=").append(y).append('}');
        sb.append(" ");
        for (GraphNode successor : this.successors) {
            sb.append("(").append(successor.getX()).append(",").append(successor.getY()).append(")");
        }
        return sb.toString();
    }

    /**
     * Checks if a given object is equal to this GraphNode
     *
     * @param o object to be compared
     * @return true if the object is equal to this GraphNode else false
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof GraphNode)) return false;

        GraphNode graphNode = (GraphNode) o;

        return x == graphNode.x && y == graphNode.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
