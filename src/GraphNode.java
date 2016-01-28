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
}
