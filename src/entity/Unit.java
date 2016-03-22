package entity;

import core.CoreEngine;
import graph.Graph;
import graph.GraphNode;
import gui.GameInterface;
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
import sorts.visual.SortVisual;
import tutorial.Tutorial;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Dominic Walters on 21/02/16
 */
public class Unit extends Entity {

    private static final Logger LOG = Logger.getLogger(Unit.class.getName());

    /**
     * Search flags
     */
    public enum Search {

        /**
         * Depth First Search
         */
        DFS,

        /**
         * Breadth First Search
         */
        BFS,

        /**
         * A* Search
         */
        A_STAR
    }

    /**
     * Sort flags
     */
    public enum Sort {

        /**
         * Bubble Sort
         */
        BUBBLE,

        /**
         * Selection Sort
         */
        SELECTION,

        /**
         * Insert Sort
         */
        INSERT
    }

    // Unit movement speed
    private static final Duration SPEED = Duration.millis(600);

    // Dependencies
    private Renderer renderer = Renderer.Instance();
    private List<GraphNode> route;
    private List<GraphNode> visited;

    private SequentialTransition visualTransition;

    private Graph graph;
    private GraphNode goal;
    private Search search;
    private Sort sort;

    private boolean completedMove = true;

    private SortableBlockade sorting = null;

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

    // GETTER methods

    /**
     * Gets the search algorithm being used by this unit
     *
     * @return search algorithm used
     */
    public Search getSearch() {

        return this.search;
    }

    /**
     * Gets the sort algorithm being used by this unit
     *
     * @return sort algorithm used
     */
    public Sort getSort() {

        return this.sort;
    }

    /**
     * Gets the route calculated by this unit
     *
     * @return List of GraphNodes making up the route
     */
    public List<GraphNode> getRoute() {

        return this.route;
    }

    /**
     * Gets the visual transition used by the unit
     *
     * @return a Sequential Transition used by the unit
     */
    public SequentialTransition getVisualTransition() {

        return this.visualTransition;
    }

    /**
     * Gets a list of graph nodes visited by this unit
     *
     * @return a List of GraphNodes visited by this unit
     */
    public List<GraphNode> getVisited() {

        return this.visited;
    }

    /**
     * Gets the blockade being sorted by this unit
     *
     * @return a sortable blockade
     */
    public SortableBlockade getSorting() {

        return this.sorting;
    }

    // SETTER methods

    /**
     * Sets the route to be followed by this unit
     *
     * @param route List of GraphNodes making up the route
     */
    public void setRoute(List<GraphNode> route) {

        this.route = route;
    }

    /**
     * Sets the visual transition for this unit
     *
     * @param visualTransition a Sequential Transition for this unit
     */
    public void setVisualTransition(SequentialTransition visualTransition) {

        this.visualTransition = visualTransition;
    }

    /**
     * Sets the list of visited graph nodes for this unit
     *
     * @param visited a List of GraphNodes visited by this unit
     */
    public void setVisited(List<GraphNode> visited) {

        this.visited = visited;
    }

    /**
     * Sets the blockade being sorted by this unit
     *
     * @param sorting a sortable blockade
     */
    public void setSorting(SortableBlockade sorting) {

        this.sorting = sorting;

        if (sorting == null) {
            this.completedMove = true;
        }
    }

    // MOVEMENT methods

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
            newPosition = graph.nodeWith(

                    new GraphNode(
                            getPosition().getX(),
                            getPosition().getY() - 1
                    )
            );

            LOG.log(Level.INFO, "Move Up: " + newPosition.getX() + "," + newPosition.getY());
            // Check if the new position has a blockade in it.
            moved = blockCheck(newPosition);
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
            newPosition = graph.nodeWith(

                    new GraphNode(
                            getPosition().getX(),
                            getPosition().getY() + 1
                    )
            );

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
            newPosition = graph.nodeWith(

                    new GraphNode(
                            getPosition().getX() + 1,
                            getPosition().getY()
                    )
            );

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
            newPosition = graph.nodeWith(

                    new GraphNode(
                            getPosition().getX() - 1,
                            getPosition().getY()
                    )
            );

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
    public boolean blockCheck(GraphNode position) {

        Blockade blockade = position.getBlockade();

        if (blockade == null) {

            getPosition().getUnits().remove(this);
            position.getUnits().add(this);
            setPosition(position);

            return true;

        } else if (blockade instanceof SortableBlockade &&
                ((SortableBlockade) blockade).getSortVisual() == null &&
                sorting == null) {

            sorting = (SortableBlockade) blockade;
            SortVisual sortVisual = new SortVisual((SortableBlockade) blockade, this);

            ((SortableBlockade) blockade).setSortVisual(sortVisual);
            sortVisual.getPane().setLayoutX(424 / 2 - 300 / 2 + 20);
            sortVisual.getPane().setLayoutY(50 + 3 * 30 + 90);

            Platform.runLater(() -> GameInterface.rightMenuBox.getChildren().add(sortVisual.getPane()));

            return false;

        } else {
            return false;
        }
    }

    //@TODO: document this method

    /**
     * Updates the unit's position. Whenever the unit is changing it's direction
     * the sprite also rotates accordingly. 
     * If the unit reaches the goal point then the scene is set to the End Menu which will
     * display the score.
     */
    @Override
    public void update() {

        if (completedMove) {

            if (route.size() > 0) {

                this.completedMove = false;
                GraphNode nextNode = route.remove(0);

                int x = nextNode.getX();
                int y = nextNode.getY();
                int xChange = x - this.position.getX();
                int yChange = y - this.position.getY();

                boolean result = logicalMove(xChange, yChange);

                if (result) {

                    double nextPixelX = x * renderer.getXSpacing();
                    double nextPixelY = y * renderer.getYSpacing();

                    TranslateTransition transition = new TranslateTransition(SPEED, sprite);
                    transition.setToX(nextPixelX);
                    transition.setToY(nextPixelY);
                    transition.setOnFinished(e -> this.completedMove = true);
                    transition.play();

                } else {

                    if (this.sorting == null) {

                        decideRoute();
                        this.completedMove = true;

                    } else {
                        route.add(0, nextNode);
                    }
                }

            } else if (this.getPosition() == goal) {

                Platform.runLater(() -> {

                    CoreEngine.Instance().setPaused(true);
                    MenuHandler.switchScene(MenuHandler.END_GAME_MENU);
                });

            } else {

                CoreEngine.Instance().setPaused(true);
                CoreEngine.Instance().getScore().halveScore();
                Platform.runLater(() -> MenuHandler.switchScene(MenuHandler.END_GAME_MENU));
            }
        }
    }

    /**
     * Does a logical move of the unit in the specified direction,
     * i.e. move it in the graph and change its graph position
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

        getSprite().setRotate(rotate);

        return success;
    }

    /**
     * Decides a route based on the search algorithm of the unit
     */
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

    //@TODO: document this method

    /** Shows the transition of this unit. There is the option of choosing if it produces the route or the
     * algorithm visualisation.
     * 
     * @param route if the route or the algorithm visualisation is going to be displayed
     * @param show if the unit will show or not the route they will follow
     */
    public void showTransition(boolean route, boolean show) {

    	// Create a visual transition for the unit
        SequentialTransition currentTrans = this.getVisualTransition();

        // If there is no transition at this moment
        if (currentTrans == null && this.getRoute() != null && show) {
        	
        	// If route is true, produce the route visualisation
            if (route) {
            	// Create a new one
                SequentialTransition transition = renderer.produceRouteVisual(
                        renderer.produceRoute(getRoute(), getPosition()
                        )
                );

                setVisualTransition(transition);
                transition.play();
                // updates the tutorial
                Tutorial.routeShown = Tutorial.active;

            } 
            // If route is false, produce the algorithm visualisation
            else {

                setVisualTransition(renderer.produceAlgoRouteVisual(this));
                getVisualTransition().play();
                // updates the tutorial
                Tutorial.visualShown = Tutorial.active;
            }

        } 
        // Deletes the route or algorithm visualisation if requested
        else if (currentTrans != null && !show) {

            currentTrans.stop(); // stop the current transition
            ObservableList<Animation> transitions = currentTrans.getChildren();

            for (Animation transition : transitions) {

                if (transition instanceof FadeTransition) {

                    FadeTransition trans = (FadeTransition) transition;
                    nullObject(trans.getNode()); // delete the current node

                } else if (transition instanceof SequentialTransition) {

                    SequentialTransition trans = (SequentialTransition) transition;
                    ObservableList<Animation> transitions2 = trans.getChildren();

                    for (Animation transition2 : transitions2) {

                        FadeTransition trans2 = (FadeTransition) transition2;
                        nullObject(trans2.getNode()); // delete the current node
                    }
                }
                transition = null;
                setVisualTransition(null);
                Tutorial.routeShown = false; // route set to not shown if the tutorial is on
                Tutorial.visualShown = false; // visual set to not shown if the tutorial is on
            }
        }
    }

    //@TODO: document this method

    /**
     * Deletes the lines and rectangles that were displayed for this unit
     * 
     * @param object the object which will be deleted
     */
    private void nullObject(Object object) {

    	// if the object is a line, delete the line
        if (object instanceof Line) {

            Line line = (Line) object;
            line.setOpacity(0.0);
            line = null;

        } 
        // if the object is a rectangle, delete the rectangle
        else if (object instanceof Rectangle) {

            Rectangle rect = (Rectangle) object;
            rect.setOpacity(0.0);
            rect = null;
        }
    }
}