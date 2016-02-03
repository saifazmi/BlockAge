import javafx.scene.Node;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Unit extends Entity {

    private static final Logger LOG = Logger.getLogger(Unit.class.getName());

    private enum Search {
        DFS,
        BFS,
        A_STAR
    }

    private enum Sort {
        BUBBLE,
        SELECTION,
        QUICK
    }

    private List<GraphNode> route;
    private Graph graph;

    public Unit(int id, String name, String description, GraphNode position, Node sprite, List<GraphNode> route, Graph graph) {
        super(id, name, description, position, sprite);
        this.route = route;
        this.graph = graph;
    }

    public Unit(int id, String name, GraphNode position, Node sprite, List<GraphNode> route, Graph graph) {
        super(id, name, position, sprite);
        this.route = route;
        this.graph = graph;
    }

    public boolean moveUp() {

        // Has the unit moved
        boolean moved = false;

        GraphNode newPosition = null;

        // Check if the unit is still in the graph bounds.
        if ((this.getPosition().getY() - 1) < 0) {
            moved = false;
            LOG.log(Level.SEVERE, "Move Up: Failed!");
        } else {
            // Get the new position.
            newPosition = graph.nodeWith(new GraphNode(this.getPosition().getX(), getPosition().getY() - 1));
            LOG.log(Level.INFO, "Move Up: " + newPosition.getX() + "," + newPosition.getY());
            // Check if the new position has a blockade in it.
            moved = blockCheck(newPosition);
        }

        return moved;
    }

    public boolean moveDown() {

        // Has the unit moved
        boolean moved = false;

        GraphNode newPosition = null;

        // Check if the unit is still in the graph bounds.
        if ((this.getPosition().getY() + 1) >= Graph.HEIGHT) {
            moved = false;
            LOG.log(Level.SEVERE, "Move Down: Failed!");
        } else {
            // Get the new position.
            newPosition = graph.nodeWith(new GraphNode(this.getPosition().getX(), getPosition().getY() + 1));
            LOG.log(Level.INFO, "Move Down: " + newPosition.getX() + "," + newPosition.getY());
            // Check if the new position has a blockade in it.
            moved = blockCheck(newPosition);
        }

        return moved;
    }

    public boolean moveRight() {

        // Has the unit moved
        boolean moved = false;

        GraphNode newPosition = null;

        // Check if the unit is still in the graph bounds.
        if ((this.getPosition().getX() + 1) >= Graph.WIDTH) {
            moved = false;
            LOG.log(Level.SEVERE, "Move Right: Failed!");
        } else {
            // Get the new position.
            newPosition = graph.nodeWith(new GraphNode(this.getPosition().getX() + 1, getPosition().getY()));
            LOG.log(Level.INFO, "Move Right: " + newPosition.getX() + "," + newPosition.getY());
            // Check if the new position has a blockade in it.
            moved = blockCheck(newPosition);
        }

        return moved;
    }

    public boolean moveLeft() {

        // Has the unit moved
        boolean moved = false;

        GraphNode newPosition = null;

        // Check if the unit is still in the graph bounds.
        if ((this.getPosition().getX() - 1) < 0) {
            moved = false;
            LOG.log(Level.SEVERE, "Move Left: Failed!");
        } else {
            // Get the new position.
            newPosition = graph.nodeWith(new GraphNode(this.getPosition().getX() - 1, getPosition().getY()));
            LOG.log(Level.INFO, "Move Left: " + newPosition.getX() + "," + newPosition.getY());
            // Check if the new position has a blockade in it.
            moved = blockCheck(newPosition);
        }

        return moved;
    }

    private boolean blockCheck(GraphNode position) {

        if (position.getBlockade() == null) {
            this.getPosition().removeUnit(this);
            position.addUnit(this);
            this.setPosition(position);

            return true;
        }

        return false;
    }
}
