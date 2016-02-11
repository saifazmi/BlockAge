import javafx.scene.Node;
import javafx.scene.image.ImageView;

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

    private GraphNode currentNode;
    private GraphNode nextNode;
    private ImageView sprite;
    private double nextPixelX;
    private double nextPixelY;
    private boolean completedMove = true;
    private double speed = 3;


    public Unit(int id, String name, String description, GraphNode position, Node sprite, List<GraphNode> route, Graph graph) {
        super(id, name, description, position, sprite);
        this.route = route;
        this.graph = graph;
        currentNode = position;
        this.sprite = (ImageView) sprite;
    }

    public Unit(int id, String name, GraphNode position, Node sprite, List<GraphNode> route, Graph graph) {
        super(id, name, position, sprite);
        this.route = route;
        this.graph = graph;
        currentNode = position;
        this.sprite = (ImageView) sprite;
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

    private boolean followRoute() {
        //assumes route includes starting node @TODO
        //doesn't time delay @TODO
        //doesn't perform a re-search @TODO
        boolean success = true;
        for (int i = 0; i < route.size(); i++) {
            GraphNode start = route.get(i);
            if (i < route.size() - 1) {
                GraphNode end = route.get(i + 1);
                int xChange = start.getX() - end.getX();
                int yChange = start.getY() - end.getY();

                if (xChange == 0) {
                    if (yChange > 0) {
                        success = success && moveDown();
                    } else {
                        success = success && moveUp();
                    }
                } else {
                    if (xChange > 0) {
                        success = success && moveRight();
                    } else {
                        success = success && moveLeft();
                    }
                }
            } else {
                return success;
            }
        }
        return false;
    }

    /**
     * Updates the unit's position per frame, called by CoreEngine
     * uses same logic as followRoute() but with delay
     * Does not include starting node
     * Also note that its only changing the position of the Sprite ImageView, the actually rendering has to be updated by Renderer instant
     * Recommend new method (or alter old method) so that it draws according to pixel position rather than logical position
     */
    @Override
    public void update(long deltaTime)
    {
        if (completedMove)
        {
            if (route.size() > 0)
            {
                nextNode = route.remove(0);
                completedMove = false;
                SetPositionAndSpeed(nextNode);
            }
        }
        else
        {
            if (currentPixelX != nextPixelX && currentPixelY != nextPixelY)
            {
                currentPixelX += speed * deltaTime;
                currentPixelY += speed * deltaTime;
                //CoreGUI.Instance().returnRenderer().update(this,this);
            }
            else
            {
                completedMove = true;
            }
        }
        //get pixel position of next node
        //set move to that in time step
    }

    private void SetPositionAndSpeed(GraphNode nextNode) {

        int x = nextNode.getX();
        int y = nextNode.getY();

        if (logicalMove(x,y)) {

            double spacingX = CoreGUI.Instance().returnRenderer().returnXSpacing();
            double spacingY = CoreGUI.Instance().returnRenderer().returnYSpacing();

            nextPixelX = x * spacingX;
            nextPixelY = y * spacingY;

            speed = (nextPixelX - currentPixelX) / 10;
        }
    }

    private boolean logicalMove(int x, int y) {

        boolean success = true;

        int xChange = currentNode.getX() - x;
        int yChange = currentNode.getY() - y;

        if (xChange == 0) {
            if (yChange > 0) {
                success = success && moveDown();
            } else {
                success = success && moveUp();
            }
        } else {
            if (xChange > 0) {
                success = success && moveRight();
            } else {
                success = success && moveLeft();
            }
        }

        return success;
    }

    /**
     * Should be called as soon as sprite is rendered
     * @param x
     * @param y
     */
    public void setCurrentPixel(double x, double y)
    {
        currentPixelX = x;
        currentPixelY = y;
    }

}