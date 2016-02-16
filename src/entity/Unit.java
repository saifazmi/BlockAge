package entity;

import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import sceneElements.SpriteImage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Unit extends Entity
{

    private static final Logger LOG = Logger.getLogger(Unit.class.getName());
    private static final Duration SPEED = Duration.millis(250);

    private enum Search
    {
        DFS,
        BFS,
        A_STAR
    }

    private enum Sort
    {
        BUBBLE,
        SELECTION,
        QUICK
    }

    private List<GraphNode> route;
    private SequentialTransition visualTransition;
    private Graph graph;

    private Renderer renderer;

    private GraphNode position;
    private GraphNode nextNode;
    private double nextPixelX;
    private double nextPixelY;
    int xChange;
    int yChange;
    private boolean completedMove = true;

    public Unit(int id, String name, String description, GraphNode position, SpriteImage sprite, List<GraphNode> route, Graph graph, Renderer renderer) {
        super(id, name, description, position, sprite);
        this.route = route;
        this.graph = graph;
        this.position = position;
        this.sprite = sprite;
        this.renderer = renderer;

        route.remove(0);
    }

    public Unit(int id, String name, GraphNode position, SpriteImage sprite, List<GraphNode> route, Graph graph, Renderer renderer) {
        super(id, name, position, sprite);
        this.route = route;
        this.graph = graph;
        this.position = position;
        this.sprite = sprite;
        this.renderer = renderer;

        showRouteTransition();
        route.remove(0);
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
        }

        return moved;
    }

    private boolean blockCheck(GraphNode position)
    {

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
                xChange = nextNode.getX() - position.getX();
                yChange = nextNode.getY() - position.getY();
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

            nextPixelX = x * spacingX;
            nextPixelY = y * spacingY;

            TranslateTransition transition = new TranslateTransition(SPEED, sprite);
            transition.setToX(nextPixelX);
            transition.setToY(nextPixelY);
            transition.setOnFinished(e -> {
                completedMove = true;
            });
            transition.play();
        }
    }

    private boolean logicalMove(int xChange, int yChange) {

        boolean success = true;

        System.out.println("xchange = " + xChange);
        System.out.println("ychange = " + yChange);
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

    public void showRouteTransition()
    {
        this.visualTransition = renderer.produceRouteVisual(route); //Generates route line on unit spawn
    }

    public void cancelRouteTransition()
    {
        this.visualTransition.stop();
        renderer.removeTransition(this.visualTransition);


    }
}