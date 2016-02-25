package entity;

import core.GameRunTime;
import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFristSearch;

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

    public enum Search {
        DFS,
        BFS,
        A_STAR
    }

    public enum Sort {
        BUBBLE,
        SELECTION,
        QUICK
    }

    private List<GraphNode> route;
    private List<Line> currentRoute;
    private List<Line> linesOfRoute;
    private SequentialTransition visualTransition;

    private Graph graph;
    private GraphNode goal;
    private Search search;
    private Sort sort;
    private boolean changingRoute = false;

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
//            GameInterface.textInfoText.clear();
//            GameInterface.textInfoText.setText("Move Down: " + newPosition.getX() + "," + newPosition.getY());
        }

        return moved;
    }

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
//            GameInterface.textInfoText.clear();
//            GameInterface.textInfoText.setText("Move Right: " + newPosition.getX() + "," + newPosition.getY());
        }

        return moved;
    }

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
//            GameInterface.textInfoText.clear();
//            GameInterface.textInfoText.setText("Move Left: " + newPosition.getX() + "," + newPosition.getY());
        }

        return moved;
    }

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
     * Updates the unit's position per frame, called by CoreEngine
     * uses same logic as followRoute() but with delay
     * Does not include starting node
     * Also note that its only changing the position of the Sprite ImageView, the actually rendering has to be updated by Renderer instant
     * Recommend new method (or alter old method) so that it draws according to pixel position rather than logical position
     */
    @Override
    public void update() {
        if (completedMove) {

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
            if(this.getVisualTransition() != null)
            {
                this.getVisualTransition().play();
            }
            completedMove = true;
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
     *
     * @param x
     * @param y
     */
    public void setCurrentPixel(double x, double y) {
        currentPixelX = x;
        currentPixelY = y;
    }

    private void decideRoute() {
        if (search == Search.DFS) {
            System.out.println("using dfs");
            setRoute(DepthFristSearch.Instance().findPathFrom(getPosition(), this.goal));
        } else if (search == Search.BFS) {
            System.out.println("using bfs");
            setRoute(BreadthFirstSearch.Instance().findPathFrom(getPosition(), this.goal));
        } else {
            System.out.println("using astar");
            setRoute(AStar.search(getPosition(), this.goal));
        }
        System.out.println(route);
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

    public void setRoute(List<GraphNode> route)
    {
        this.route = route;
        changingRoute = true;
        setChanged();
        notifyObservers();
    }

    public boolean routeChanged() {
        return changingRoute;
    }

    public void setChangingRoute(boolean bool) {
        this.changingRoute = bool;
    }

    public List<Line> getLinesOfRoute()
    {
        return linesOfRoute;
    }
    public SequentialTransition getVisualTransition()
    {
        return visualTransition;
    }
    public void setVisualTransition(SequentialTransition visualTransition)
    {
        this.visualTransition = visualTransition;
    }

    // get the current route
    public List<Line> getCurrentRoute() {
        return currentRoute;
    }

    // set the current route
    public void setCurrentRoute(List<Line> currentRoute) {
        this.currentRoute = currentRoute;
    }
}