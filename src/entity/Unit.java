package entity;

import core.CoreEngine;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import menus.MenuHandler;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Dominic Walters on 21/02/16
 */
public class Unit extends Entity {
    //@TODO: fix the move function
    private Renderer renderer = Renderer.Instance();
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
    private List<GraphNode> visited;

    private SequentialTransition visualTransition;

    private Graph graph;
    private GraphNode goal;
    private Search search;
    private Sort sort;

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
     */
    public Unit(int id, String name, GraphNode position, SpriteImage sprite, Search search, Sort sort, Graph graph, GraphNode goal) {

        super(id, name, position, sprite);
        this.graph = graph;
        this.goal = goal;
        this.search = search;
        this.sort = sort;

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

            LOG.log(Level.INFO, "completed move");
            if (this.nextNode != null) {
                LOG.log(Level.INFO, "next node is " + this.nextNode);
                this.position = this.nextNode;
            }
            if (route.size() > 0) {
                this.completedMove = false;
                this.nextNode = this.route.remove(0);
                int x = this.nextNode.getX();
                int y = this.nextNode.getY();
                int xChange = this.nextNode.getX() - this.position.getX();
                int yChange = this.nextNode.getY() - this.position.getY();
                if (logicalMove(xChange, yChange)) {
                    double nextPixelX = x * renderer.getXSpacing();
                    double nextPixelY = y * renderer.getYSpacing();

                    TranslateTransition transition = new TranslateTransition(SPEED, sprite);
                    transition.setToX(nextPixelX);
                    transition.setToY(nextPixelY);
                    transition.setOnFinished(e -> this.completedMove = true);
                    transition.play();
                } else {
                    decideRoute();
                    nextNode = null;
                    this.completedMove = true;
                }
            } else if (this.getPosition() == goal) {
                nextNode = null;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        CoreEngine.Instance().setPaused(true);
                        MenuHandler.switchScene(MenuHandler.END_GAME_MENU);
                    }
                });

            } else {
                nextNode = null;
                CoreEngine.Instance().setPaused(true);
                CoreEngine.Instance().halveScore();
                Platform.runLater(() -> MenuHandler.switchScene(MenuHandler.END_GAME_MENU));
            }
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
        boolean success;
        double rotate;
        if (xChange == 0) {
            if (yChange > 0) {
                success = moveDown();
                rotate = 0;
            } else {
                success = moveUp();
                rotate = 180;
            }
        } else {
            if (xChange > 0) {
                success = moveRight();
                rotate = 270;
            } else {
                success = moveLeft();
                rotate = 90;
            }
        }
        this.getSprite().setRotate(rotate);
        return success;
    }


    public void decideRoute() {
        LOG.log(Level.INFO, "my position is " + getPosition().toString());
        if (search == Search.DFS) {
            DepthFirstSearch.findPathFrom(this, this.goal);
        } else if (search == Search.BFS) {
            BreadthFirstSearch.findPathFrom(this, this.goal);
        } else {
            AStar.search(this, this.goal);
        }
        LOG.log(Level.INFO, route.toString());
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
    }

    public SequentialTransition getVisualTransition() {
        return visualTransition;
    }

    public void setVisualTransition(SequentialTransition visualTransition) {
        this.visualTransition = visualTransition;
    }

    public List<GraphNode> getVisited() {
        return visited;
    }

    public void setVisited(List<GraphNode> visited) {
        this.visited = visited;
    }

    public void showTransition(boolean route, boolean show) {
        SequentialTransition currentTrans = this.getVisualTransition();
        if (currentTrans == null && this.getRoute() != null && show) {
            if (route) {
                SequentialTransition transition = renderer.produceRouteVisual(renderer.produceRoute(this.getRoute(), this.getPosition()));
                this.setVisualTransition(transition);
                transition.play();
            } else {
                this.setVisualTransition(renderer.produceAlgoRouteVisual(this));
                this.getVisualTransition().play();
            }
        } else if (currentTrans != null && !show) {
            currentTrans.stop();
            ObservableList<Animation> transitions = currentTrans.getChildren();
            for (Animation transition : transitions) {
                if (transition instanceof FadeTransition) {
                    FadeTransition trans = (FadeTransition) transition;
                    nullObject(trans.getNode());
                } else if (transition instanceof SequentialTransition) {
                    SequentialTransition trans = (SequentialTransition) transition;
                    ObservableList<Animation> transitions2 = trans.getChildren();
                    for (Animation transition2 : transitions2) {
                        FadeTransition trans2 = (FadeTransition) transition2;
                        nullObject(trans2.getNode());
                    }
                }
                this.setVisualTransition(null);
            }
        }
    }

    private void nullObject(Object object) {
        if (object instanceof Line) {
            Line line = (Line) object;
            line.setOpacity(0.0);
        } else if (object instanceof Rectangle) {
            Rectangle rect = (Rectangle) object;
            rect.setOpacity(0.0);
        }
    }
}