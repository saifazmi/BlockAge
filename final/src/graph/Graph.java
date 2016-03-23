package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author : Saif Azmi; Contributors - Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @version : 23/03/2016;
 *          <p>
 *          Defines a graph data structure.
 * @date : 28/01/16
 */
public class Graph {

    private static final Logger LOG = Logger.getLogger(Graph.class.getName());

    // Graph Width
    public static final int WIDTH = 20;
    // Graph Height
    public static final int HEIGHT = 20;

    // List of nodes making up the logical graph
    private List<GraphNode> nodes;

    /**
     * Builds a new graph
     */
    public Graph() {

        this.nodes = new ArrayList<>();
    }

    /**
     * Checks if node is in the graph. Adds it if it isn't, then returns it. If it is, returns the version that is.
     *
     * @param node the node to check
     * @return the node
     */
    public GraphNode nodeWith(GraphNode node) {

        for (GraphNode aNode : nodes) {
            if (aNode.equals(node)) {
                return aNode;
            }
        }
        this.nodes.add(node);

        return node;
    }

    /**
     * Gets a list of nodes in the graph
     *
     * @return a List of GraphNodes making up this graph
     */
    public List<GraphNode> getNodes() {

        return this.nodes;
    }
}
