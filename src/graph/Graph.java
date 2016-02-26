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
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    private List<GraphNode> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public GraphNode nodeWith(GraphNode node) {

        for (GraphNode aNode : nodes) {
            if (aNode.equals(node)) {
                return aNode;
            }
        }
        nodes.add(node);

        return node;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }
}
