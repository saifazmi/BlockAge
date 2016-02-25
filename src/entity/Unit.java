package entity;

import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */

public class Unit extends Entity {
    //@TODO: fix the move function, using text log
    private static final Logger LOG = Logger.getLogger(Unit.class.getName());
    private static final Duration SPEED = Duration.millis(600);

    //enum to indicate which search algorithm is being used by a Unit instance
    public enum Search {
        DFS,
        BFS,
        A_STAR
    }

    //enum to indicate which sort algorithm is being used by a Unit instance
    public enum Sort {
        BUBBLE,
        SELECTION,
        QUICK
    }

    private List<GraphNode> route;
    private List<Line> linesOfRoute;
    private SequentialTransition visualTransition;

    private Graph graph;
    private GraphNode goal;
    private Search search;
    private Sort sort;

    private Renderer renderer;

    /*
    Inherited parameters
        private final int id;
        private String name;
        private String description;
        protected GraphNode position;
        protected double currentPixelX;
        protected double currentPixelY;
        protected SpriteImage sprite;
     */
    private GraphNode nextNode;
    private boolean completedMove = true;

    /**
     * Constructor for Unit used by UnitSpawner
     *
     * @param id       unit ID
     * @param name     unit Name
     * @param position Unit position in graph
     * @param sprite   Unit's associated sprite
     * @param search   Unit's search indicator, used for deciding search algorithm used
     * @param sort     Unit's sort indicator, used for deciding sort algorithm used
     * @param graph    The graph the unit is on
     * @param goal     The goal node for the search algorithm
     * @param renderer The renderer to draw the unit's sprite to
     */
    public Unit(int id, String name, GraphNode position, SpriteImage sprite, Search search, Sort sort, Graph graph, GraphNode goal, Renderer renderer) {
        super(id, name, position, sprite);

        setPosition(position);
        this.sprite = sprite;

        this.graph = graph;
        this.goal = goal;
        this.search = search;
        this.sort = sort;
        this.renderer = renderer;

        this.linesOfRoute = new ArrayList<>();

        decideRoute();
    }

    /**
     * Moves the unit up logically, i.e. change its graph position if there is no block there
     *
     * @return whether the move was successful
     */
    public boolean moveUp() {
        // Has the unit moved
        boolean moved;
        GraphNode newPosition;
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
//            GameInterface.textInfoText.clear();
//            GameInterface.textInfoText.setText("Move Up: " + newPosition.getX() + "," + newPosition.getY());
        }

        return moved;
    }

    /**
     * Moves the unit down logically, i.e. change its graph position if there is no block there
     *
     * @return whether the move was successful
     */
    public boolean moveDown() {
        // Has the unit moved
        boolean moved;
        GraphNode newPosition;
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

    /**
     * Moves the unit right logically, i.e. change its graph position if there is no block there
     *
     * @return whether the move was successful
     */
    public boolean moveRight() {
        // Has the unit moved
        boolean moved;
        GraphNode newPosition;
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

    /**
     * Moves the unit left logically, i.e. change its graph position if there is no block there
     *
     * @return whether the move was successful
     */
    public boolean moveLeft() {
        // Has the unit moved
        boolean moved;
        GraphNode newPosition;
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

    /**
     * Checks if the specified graph position has a block associated with it
     *
     * @param position the position to be checked
     * @return whether the position has a block
     * //@TODO comment on removing, adding units
     */
    private boolean blockCheck(GraphNode position) {

        if (position.getBlockade() == null) {
            this.getPosition().getUnits().remove(this);
            position.getUnits().add(this);
            this.setPosition(position);

            return true;
        }
        return false;
    }

    /**
     * Updates the unit's position per frame, if it has completed its previous move, called by CoreEngine.
     * Takes the first node from what remains of the route this unit is following and calculate the difference between
     * that node and its current position, after that moves the unit with MoveUnit. This method also updates the unit's logical position,
     * this is done after the unit moves graphically
     */
    @Override
    public void update() {
        if (completedMove) {

            // Unit's position only set to next node once the unit actually reaches that node graphically
            if (nextNode != null)
                position = nextNode;

            if (route.size() > 0) {
                nextNode = route.remove(0);
                int xChange = nextNode.getX() - position.getX();
                int yChange = nextNode.getY() - position.getY();
                completedMove = false;
                SetPositionAndSpeed(xChange, yChange);
            }
        }
    }

    /**
     * Moves the unit graphically.
     * First checks if the logical move if possible, if it is, calculate the next pixel the Sprite should end up at and
     * translate the Sprite there at a specified speed. Once the Sprite arrives at its destination, set completeMove to true so
     * the update function can make the next move.
     * If the logical move fails, i.e. there was a block, then decide a new route based on current position and search assigned.
     * In the latter case, make completeMove true anyway so that the update function may make the next move
     *
     * @param xChange amount of change in x direction
     * @param yChange amount of change in y direction
     */
    private void SetPositionAndSpeed(int xChange, int yChange) {
        int x = nextNode.getX();
        int y = nextNode.getY();

        if (logicalMove(xChange, yChange)) {
            double spacingX = renderer.getXSpacing();
            double spacingY = renderer.getYSpacing();

            double nextPixelX = x * spacingX;
            double nextPixelY = y * spacingY;

            TranslateTransition transition = new TranslateTransition(SPEED, sprite);
            transition.setToX(nextPixelX);
            transition.setToY(nextPixelY);
            transition.setOnFinished(e -> completedMove = true);
            transition.play();
        } else {
            decideRoute();
            if (this.getVisualTransition() != null) {
                this.getVisualTransition().play();
            }
            completedMove = true;
        }
    }

    /**
     * Does a logical move of the unit in the specified direction, i.e. move it in the graph and change its graph position
     *
     * @param xChange amount of nodes to move in the x axis
     * @param yChange amount of nodes to move in the y axis
     * @return if the logical move was successful
     */
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
     *
     * @param x
     * @param y
     */
    public void setCurrentPixel(double x, double y) {
        currentPixelX = x;
        currentPixelY = y;
    }

    /**
     * Sets a route for the unit to follow, how the route is obtained is determined by the Search Indicator of the Unit
     */
    private void decideRoute() {
        if (search == Search.DFS) {
            //System.out.println("using dfs");
            route = DepthFirstSearch.Instance().findPathFrom(getPosition(), this.goal);
        } else if (search == Search.BFS) {
            //System.out.println("using bfs");
            route = BreadthFirstSearch.Instance().findPathFrom(getPosition(), this.goal);
        } else {
            //System.out.println("using astar");
            route = AStar.search(getPosition(), this.goal);
        }
        //System.out.println(route);
    }


    /**
     * Gets the search algorithm being used by this unit
     *
     * @return search algorithm used
     */
    public Search getSearch() {
        return search;
    }

    /**
     * Gets the sort algorithm being used by this unit
     *
     * @return sort algorithm used
     */
    public Sort getSort() {
        return sort;
    }

    public List<GraphNode> getRoute() {
        return route;
    }

    public void setRoute(List<GraphNode> route) {
        this.route = route;
        setChanged();
        notifyObservers();
    }

    public List<Line> getLinesOfRoute() {
        return linesOfRoute;
    }

    public SequentialTransition getVisualTransition() {
        return visualTransition;
    }

    public void setVisualTransition(SequentialTransition visualTransition) {
        this.visualTransition = visualTransition;
    }
}