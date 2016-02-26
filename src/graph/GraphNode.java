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

    private int x;
    private int y;

    private List<Unit> units;     // Empty list of entities
    private Blockade blockade;    // null blockade
    private List<GraphNode> successors;
    private Base base;

    public GraphNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.units = new ArrayList<>();
        this.successors = new ArrayList<>();
        blockade = null;
        base = null;
    }

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

    public List<GraphNode> getSuccessors() {
        return successors;
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

    public List<Unit> getUnits() {
        return units;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
}
