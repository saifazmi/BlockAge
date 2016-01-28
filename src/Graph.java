import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Graph {

    private static final Logger LOG = Logger.getLogger(Graph.class.getName());

    private Set<GraphNode> nodes;

    public Graph() {
        this.nodes = new LinkedHashSet<GraphNode>();
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
}
