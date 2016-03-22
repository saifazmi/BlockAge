package gui;

import core.GameRunTime;
import entity.Entity;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters
 * @date : 29/01/16, last edited by Dominic Walters on 26/02/16
 */
public class Renderer extends Group {
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    private Scene scene = GameRunTime.Instance().getScene();
    private List<Entity> entitiesToDraw;
    private double xSpacing;
    private double ySpacing;
    private ArrayList<Double> spacingOutput;
    private static Renderer instance = null;

    public static Renderer Instance() {
        if (instance == null) {
            instance = new Renderer();
        }
        return instance;
    }

    /*private Renderer() {
        super();
        this.entitiesToDraw = new ArrayList<>();
    }*/

    public Renderer()
    {
        super();
        this.entitiesToDraw = new ArrayList<>();
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    public double getXSpacing() {
        return xSpacing;
    }

    public double getYSpacing() {
        return ySpacing;
    }

    /**
     * Function to perform the initial draw of the screen.
     * This includes calculating the xSpacing, ySpacing, and the grid lines
     *
     * @return success boolean representing the success of the operation
     */
    public boolean initialDraw() {
        boolean success;
        ArrayList<Double> results = spacingOutput;
        int width = (int) (double) results.get(0);
        int height = (int) (double) results.get(1);
        double xSpacing = results.get(2);
        this.xSpacing = xSpacing;
        double ySpacing = results.get(3);
        this.ySpacing = ySpacing;
        double pixelWidth = results.get(4);
        double pixelHeight = results.get(5);
        success = drawLines(xSpacing, ySpacing, pixelWidth, pixelHeight, width, height);
        return success;
    }

    public Node remove(Node node) {
        if (this.getChildren().contains(node)) {
            LOG.log(Level.INFO, "Deleted " + node.toString());
            this.getChildren().remove(node);
            return node;
        } else {
            return null;
        }
    }

    public void clear() {
        ObservableList list = this.getChildren();
        for (Object object : list) {
            Node node = (Node) object;
            remove(node);
        }
    }

    public void redraw() {
        //@TODO redundant, will break transitions on resize
//        if(CoreEngine.Instance().isPaused()) {
//            this.getChildren().clear();
//            calculateSpacing();
//            initialDraw();
//            entitiesToDraw.forEach(this::drawInitialEntity);
//        }
    }

    /**
     * Creates the grid lines and adds them to the renderer.
     *
     * @param xSpacing     the x width of grid quadrilaterals
     * @param ySpacing     the y width of grid quadrilaterals
     * @param width        the width of the rendering pane
     * @param height       the height of the rendering pane
     * @param xAccumulator the amount of graphnodes in the x direction
     * @param yAccumulator the amount of graphnodes in the y direction
     * @return success boolean representing the success of the operation
     */
    public boolean drawLines(double xSpacing, double ySpacing, double width, double height, int xAccumulator, int yAccumulator) {
        for (int i = 0; i < xAccumulator + 1; i++) {
            Line line = new Line(xSpacing * i, 0, xSpacing * i, height);
            line.setStroke(Color.web("#201616"));
            this.getChildren().add(line);
        }
        for (int i = 0; i < yAccumulator + 1; i++) {
            Line line = new Line(0, ySpacing * i, width, ySpacing * i);
            line.setStroke(Color.web("#201616"));
            this.getChildren().add(line);
        }
        return true;
    }

    /**
     * Calculates the spacing needed for the grid to fit the space it has been granted.
     *
     * @return an array list containing 6 things ordered as follows:
     * <p>
     * width           Number of GraphNodes on the width
     * height          Number of GraphNodes on the height
     * xSpacing        Pixel width of each grid quadrilateral
     * ySpacing        Pixel height of each grid quadrilateral
     * pixelWidth      Pane width in pixels
     * pixelHeight     Pane height in pixels
     */
    public void calculateSpacing() {
        ArrayList<Double> returnList = new ArrayList<>();

        double pixelWidth = scene.getWidth() - GameInterface.rightPaneWidth;
        double pixelHeight = scene.getHeight() - GameInterface.bottomPaneHeight;
        int width = Graph.WIDTH;
        int height = Graph.HEIGHT;
        this.xSpacing = pixelWidth / (width);
        this.ySpacing = pixelHeight / (height);

        returnList.add((double) width);
        returnList.add((double) height);
        returnList.add(xSpacing);
        returnList.add(ySpacing);
        returnList.add(pixelWidth);
        returnList.add(pixelHeight);
        spacingOutput = returnList;
    }

    /**
     * Draws an entity before it starts to move.
     *
     * @param entity the entity to be drawn
     * @return success boolean representing the success of the operation
     */
    public boolean drawInitialEntity(Entity entity) {
        boolean success;
        if (!this.entitiesToDraw.contains(entity)) {
            this.entitiesToDraw.add(entity);
        }
        GraphNode node = entity.getPosition();
        SpriteImage sprite = entity.getSprite();
        sprite.setFitWidth(xSpacing);
        sprite.setFitHeight(ySpacing);
        sprite.setX(node.getX() * xSpacing);
        sprite.setY(node.getY() * ySpacing);
        success = this.getChildren().add(sprite);
        return success;
    }

    public SequentialTransition produceRouteVisual(List<Line> lines) {
        SequentialTransition trans = new SequentialTransition();
        for (Line line : lines) {
            line.setOpacity(0.0);
            if (!this.getChildren().contains(line)) {
                this.getChildren().add(line);
            }
            FadeTransition lineTransition = buildFadeAnimation(50, 0.0, 1.0, line);
            trans.getChildren().add(lineTransition);
        }
        return trans;
    }

    public List<Line> produceRoute(List<GraphNode> route) {
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < route.size(); i++) {
            GraphNode start = route.get(i);
            if (i + 1 < route.size()) {
                GraphNode end = route.get(i + 1);
                Line line = new Line(this.xSpacing / 2 + start.getX() * xSpacing,
                        this.ySpacing / 2 + start.getY() * ySpacing,
                        this.xSpacing / 2 + end.getX() * xSpacing,
                        this.ySpacing / 2 + end.getY() * ySpacing);
                line.setStrokeWidth(4.0);
                lines.add(line);
            } else {
                i = route.size();
            }
        }
        return lines;
    }

    public List<Line> produceRoute(List<GraphNode> route, GraphNode start) {
        List<GraphNode> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.addAll(route);
        return produceRoute(nodes);
    }

    /**
     * Builds a fade animation with the given inputs
     *
     * @param millis the millisecond time the transition should occur over
     * @param opac1  the opacity to start at
     * @param opac2  the opacity to end at
     * @param node   the node that the transition should be performed on
     * @return the transition that has been made
     */
    private FadeTransition buildFadeAnimation(double millis, double opac1, double opac2, Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(opac1);
        fadeTransition.setToValue(opac2);
        return fadeTransition;
    }

    public SequentialTransition produceAlgoRouteVisual(Unit unit) {
        SequentialTransition trans = new SequentialTransition();

        List<Line> lines = produceAlgoRoute(unit);

        for (Line line : lines) {
            line.setMouseTransparent(true);
            Rectangle rect = new Rectangle(xSpacing, ySpacing);
            rect.setFill(Color.GREEN);
            rect.setOpacity(0.0);
            rect.setX(line.getStartX() - xSpacing / 2);
            rect.setY(line.getStartY() - ySpacing / 2);

            if (!this.getChildren().contains(rect)) {
                rect.setMouseTransparent(true);
                this.getChildren().addAll(rect);
                unit.getSprite().requestFocus();
            }

            this.getChildren().add(line);
            line.setOpacity(0.0);

            FadeTransition rectIn = buildFadeAnimation(25.0, 0.0, 1.0, rect);
            FadeTransition lineTransition = buildFadeAnimation(50, 0.0, 1.0, line);
            FadeTransition rectOut = buildFadeAnimation(25.0, 1.0, 0.0, rect);

            trans.getChildren().addAll(rectIn, lineTransition, rectOut);
        }

        Rectangle rect = new Rectangle(xSpacing, ySpacing);
        rect.setOpacity(0.0);
        rect.setFill(Color.ORANGE);
        rect.setX(lines.get(lines.size() - 1).getStartX() - xSpacing / 2);
        rect.setY(lines.get(lines.size() - 1).getStartY() - ySpacing / 2);
        this.getChildren().add(rect);
        FadeTransition rectIn2 = buildFadeAnimation(1000.0, 0.0, 1.0, rect);
        FadeTransition rectOut2 = buildFadeAnimation(1000.0, 1.0, 0.0, rect);

        trans.getChildren().addAll(rectIn2, rectOut2);
        List<Line> routeLines = produceRoute(unit.getRoute(), unit.getPosition());
        for (Line line : routeLines) {
            remove(line);
            line.setStroke(Color.GREEN);
        }
        Transition trans2 = produceRouteVisual(routeLines);
        trans.getChildren().add(trans2);
        return trans;
    }

    private List<Line> produceAlgoRoute(Unit unit) {
        List<Line> lines = new ArrayList<>();
        List<GraphNode> temp = unit.getVisited();
        List<GraphNode> visitedNodes = hardCopy(temp);

        List<GraphNode> drawn = new ArrayList<>();
        drawn.add(visitedNodes.remove(0));

        for (GraphNode node : visitedNodes) {
            GraphNode drawTo = linkedNode(unit, node);//nodeToDrawTo(node, drawn);
            drawn.add(node);
            Line line = new Line(this.xSpacing / 2 + node.getX() * xSpacing,
                    this.ySpacing / 2 + node.getY() * ySpacing,
                    this.xSpacing / 2 + drawTo.getX() * xSpacing,
                    this.ySpacing / 2 + drawTo.getY() * ySpacing);
            line.setStrokeWidth(4.0);
            lines.add(line);
        }
        return lines;
    }

    private GraphNode nodeToDrawTo(GraphNode from, List<GraphNode> drawn) {
        List<GraphNode> successors = from.getSuccessors();
        int min = Integer.MAX_VALUE;
        for (GraphNode node : successors) {
            int temp = drawn.indexOf(node);
            if (temp != -1 && temp < min) {
                min = temp;
            }
        }
        return drawn.get(min);
    }

    private GraphNode linkedNode(Unit unit, GraphNode toNode) {
        List<Pair<GraphNode, GraphNode>> nodeAssociations = unit.getNodeAssociations();
//        for(Pair pair : nodeAssociations) {
//            if(toNode.equals(pair.getValue())) {
//                return (GraphNode) pair.getKey();
//            }
//        }
        for(int i = nodeAssociations.size() - 1; i >= 0; i--) {
            if(toNode.equals(nodeAssociations.get(i).getValue())) {
                return nodeAssociations.get(i).getKey();
            }
        }

        return null;
    }

    private <A> List<A> hardCopy(List<A> list) {
        return list.stream().collect(Collectors.toList());
    }
}