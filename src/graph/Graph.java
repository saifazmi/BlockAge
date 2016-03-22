package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Saif Amzi on 02/02/16
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

    //@TODO: document this method

    /**
     * @param node
     * @return
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
