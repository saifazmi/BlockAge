package graph;

import entity.Blockade;
import entity.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class GraphNode {

    private static final Logger LOG = Logger.getLogger(GraphNode.class.getName());

    private int x;
    private int y;
    private List<Unit> units;     // Empty list of entities
    private Blockade blockade;    // null blockade
    private List<GraphNode> successors;

    public GraphNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.units = new ArrayList<Unit>();
        this.successors = new ArrayList<GraphNode>();
        blockade = null;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraphNode)) return false;

        GraphNode graphNode = (GraphNode) o;

        if (x != graphNode.x) return false;
        return y == graphNode.y;
    }

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

        LOG.log(Level.INFO, "This node is: " + this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph.GraphNode{" +
                "x=" + x +
                ", y=" + y +
                '}');
        sb.append(" ");
        for (int i = 0; i < this.successors.size(); i++) {
            sb.append("(" + this.successors.get(i).getX() + "," +
                    this.successors.get(i).getY() + ")");
        }

        return sb.toString();
    }

    public void addSuccessor(GraphNode node, Graph graph) {
        GraphNode successor = graph.nodeWith(node);
        this.successors.add(successor);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Blockade getBlockade() {
        return blockade;
    }

    public void setBlockade(Blockade blockade) {
        this.blockade = blockade;
    }

    public int addUnit(Unit unit) {
        int numberOfUnitsAdded = 0;

        units.add(unit);
        numberOfUnitsAdded++;

        return numberOfUnitsAdded;
    }

    public int removeUnit(Unit unit) {
        int numberOfUnitsRemoved = 0;

        units.remove(unit);
        numberOfUnitsRemoved++;

        return numberOfUnitsRemoved;
    }
}
