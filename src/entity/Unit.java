package entity;

import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFristSearch;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */

public class Unit extends Entity {
    //@TODO: fix the move function
    private Renderer renderer = Renderer.Instance();
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
    private SequentialTransition visualTransition;

    private Graph graph;
    private GraphNode goal;
    private Search search;
    private Sort sort;

    private GraphNode nextNode;
    private boolean completedMove = true;
    /*
        Inherited parameters
            protected final int id;
            protected String name;
            protected String description;
            protected GraphNode position;
            protected SpriteImage sprite;
     */
    public Unit(int id, String name, GraphNode position, SpriteImage sprite, Search search, Sort sort, Graph graph, GraphNode goal) {
        super(id, name, position, sprite);
        this.graph = graph;
        this.goal = goal;
        this.search = search;
        this.sort = sort;

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
    public void update()
    {
        if (completedMove)
        {
            if (this.nextNode != null)
                this.position = this.nextNode;

            if (route.size() > 0)
            {
                this.completedMove = false;
                this.nextNode = this.route.remove(0);
                int x = this.nextNode.getX();
                int y = this.nextNode.getY();
                int xChange = this.nextNode.getX() - this.position.getX();
                int yChange = this.nextNode.getY() - this.position.getY();
                if(xChange + yChange > 1 || xChange + yChange < -1)
                {
                    LOG.log(Level.SEVERE, "Route has dictated a path that moves more than one grid square at a time. " +
                                          "Fatal error, check search implementation: " + this.search.toString());
                    return;
                }
                if (logicalMove(xChange, yChange))
                {
                    double nextPixelX = x * this.renderer.getXSpacing();
                    double nextPixelY = y * this.renderer.getYSpacing();

                    TranslateTransition transition = new TranslateTransition(SPEED, sprite);
                    transition.setToX(nextPixelX);
                    transition.setToY(nextPixelY);
                    transition.setOnFinished(e -> this.completedMove = true);
                    transition.play();
                } else {
                    decideRoute();
                    this.completedMove = true;
                }
            }
        }
    }

    private boolean logicalMove(int xChange, int yChange)
    {
        boolean success;
        if (xChange == 0) {
            if (yChange > 0) {
                success = moveDown();
            } else {
                success = moveUp();
            }
        } else {
            if (xChange > 0) {
                success = moveRight();
            } else {
                success = moveLeft();
            }
        }
        return success;
    }

    private void decideRoute() {
        if (search == Search.DFS) {
            System.out.println("using dfs");
            setRoute(DepthFristSearch.Instance().findPathFrom(getPosition(), this.goal));
        } else if (search == Search.BFS) {
            System.out.println("using bfs");
            setRoute(BreadthFirstSearch.Instance().findPathFrom(getPosition(), this.goal));
        } else {
            System.out.println("using a-star");
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
    }
    public SequentialTransition getVisualTransition()
    {
        return visualTransition;
    }
    public void setVisualTransition(SequentialTransition visualTransition)
    {
        this.visualTransition = visualTransition;
    }

    public void showTransition()
    {
        SequentialTransition currentTrans = this.getVisualTransition();
        if(currentTrans == null && this.getRoute() != null)
        {
            SequentialTransition transition = renderer.produceRouteVisual(renderer.produceRoute(this.getRoute(), this.getPosition()));
            this.setVisualTransition(transition);
            transition.play();
        }
        else if (currentTrans != null)
        {
            currentTrans.stop();
            ObservableList<Animation> transitions = currentTrans.getChildren();
            for (Animation transition : transitions)
            {
                FadeTransition trans = (FadeTransition) transition;
                Line line = (Line) trans.getNode();
                line.setOpacity(0.0);
            }
            this.setVisualTransition(null);
        }
    }
}