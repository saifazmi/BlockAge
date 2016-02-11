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

    private Renderer renderer;

    private GraphNode currentNode;
    private GraphNode nextNode;
    private double nextPixelX;
    private double nextPixelY;
    int xChange;
    int yChange;
    private boolean completedMove = true;
    private double speed;



    public Unit(int id, String name, String description, GraphNode position, SpriteImage sprite, List<GraphNode> route, Graph graph, Renderer renderer) {
        super(id, name, description, position, sprite);
        this.route = route;
        this.graph = graph;
        currentNode = position;
        this.sprite = sprite;
        this.renderer = renderer;

        route.remove(0);
    }

    public Unit(int id, String name, GraphNode position, SpriteImage sprite, List<GraphNode> route, Graph graph, Renderer renderer) {
        super(id, name, position, sprite);
        this.route = route;
        this.graph = graph;
        currentNode = position;
        this.sprite = sprite;
        this.renderer = renderer;

        route.remove(0);
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
            if(!success)
            {
                //@TODO search has hit a blockade.//
            }
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
        Entity oldEntity = this;

        if (completedMove)
        {
            if (route.size() > 0)
            {
                System.out.println(route.get(0));
                nextNode = route.remove(0);
                xChange = nextNode.getX() - currentNode.getX();
                yChange = nextNode.getY() - currentNode.getY();
                completedMove = false;

                SetPositionAndSpeed(xChange,yChange);
            }
        }
        else
        {
            if (currentPixelX != nextPixelX || currentPixelY != nextPixelY)
            {
                /*System.out.println("current: " + currentPixelX + ", " + currentPixelY);
                System.out.println("current: " + nextPixelX + ", " + nextPixelY);*/

                /*System.out.println("delta time = " + deltaTime);
                System.out.println("speed = " + speed);*/

                currentPixelX += xChange * speed;
                currentPixelY += yChange * speed;
                setChanged();
                notifyObservers(oldEntity);
            }
            else
            {
                completedMove = true;
            }
        }
        //get pixel position of next node
        //set move to that in time step
    }

    private void SetPositionAndSpeed(int xChange, int yChange) {

        int x = nextNode.getX();
        int y = nextNode.getY();

        if (logicalMove(xChange,yChange)) {

            double spacingX = renderer.returnXSpacing();
            double spacingY = renderer.returnYSpacing();

            nextPixelX = x * spacingX;
            nextPixelY = y * spacingY;

            speed = spacingX / 10;
        }
    }

    private boolean logicalMove(int xChange, int yChange) {

        boolean success = true;

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