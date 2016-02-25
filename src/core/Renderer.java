package core;

import entity.Entity;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Renderer extends Group {
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    private Scene scene;
    private List<Entity> entitiesToDraw;

    private double xSpacing;
    private double ySpacing;

    private ArrayList<Double> spacingOutput;

    public Renderer(Scene scene) {
        super();
        this.scene = scene;
        this.entitiesToDraw = new ArrayList<>();
    }

    /**
     * Function to perform the initial draw of the screen.
     * This includes calculating the xSpacing, ySpacing, and the grid lines
     *
     * @return success boolean representing the success of the operation
     */
    public boolean initialDraw() {
        boolean success = true;
        ArrayList<Double> results = spacingOutput;
        int width = (int) (double) results.get(0);
        int height = (int) (double) results.get(1);
        double xSpacing = results.get(2);
        this.xSpacing = xSpacing;
        double ySpacing = results.get(3);
        this.ySpacing = ySpacing;
        double pixelWidth = results.get(4);
        double pixelHeight = results.get(5);
        success = success && drawLines(xSpacing, ySpacing, pixelWidth, pixelHeight, width, height);
        return success;
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
        boolean success;
        for (int i = 0; i < xAccumulator + 1; i++) {
            Line line = new Line(xSpacing * i, 0, xSpacing * i, height);
            line.setStroke(Color.LIGHTGREY);
            this.getChildren().add(line);
        }
        for (int i = 0; i < yAccumulator + 1; i++) {
            Line line = new Line(0, ySpacing * i, width, ySpacing * i);
            line.setStroke(Color.LIGHTGREY);
            this.getChildren().add(line);
        }
        success = true;
        return success;
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
        System.out.println(GameInterface.rightPaneWidth);
        System.out.println(GameInterface.topPaneHeight);
        double pixelWidth = scene.getWidth() - GameInterface.rightPaneWidth; //subtract the right sidebar pixelWidth TODO @TODO//
        double pixelHeight = scene.getHeight() - GameInterface.topPaneHeight; //subtract the bottom bar height TODO @TODO//

        int width = Graph.WIDTH;
        int height = Graph.HEIGHT;
        double xSpacing = pixelWidth / (width);
        double ySpacing = pixelHeight / (height);

        this.xSpacing = xSpacing;
        this.ySpacing = ySpacing;
        returnList.add((double) width);
        returnList.add((double) height);
        returnList.add(xSpacing);
        returnList.add(ySpacing);
        returnList.add(pixelWidth);
        returnList.add(pixelHeight);
        spacingOutput = returnList;
    }

    /**
     * Irrelevant, should be removed.
     */
    public void redraw() {
        //@TODO redundant, will break transitions on resize
        this.getChildren().clear();
        initialDraw();
        entitiesToDraw.forEach(this::drawInitialEntity);
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

        SpriteImage sprite = entity.getSprite();
        GraphNode node = entity.getPosition();

        sprite.setFitWidth(xSpacing);
        sprite.setFitHeight(ySpacing);
        //sprite.setPreserveRatio(true);
        sprite.setX(node.getX() * xSpacing);
        sprite.setY(node.getY() * ySpacing);
        success = this.getChildren().add(sprite);
        LOG.log(Level.INFO, "Entity drawn at logical " + node + ", graphical (" + sprite.getX() + ", " + sprite.getY() + ")");
        return success;
    }

    public SequentialTransition produceRouteVisual(List<Line> lines) {
        SequentialTransition trans = new SequentialTransition();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.setOpacity(0.0);
            if (!this.getChildren().contains(line)) {
                this.getChildren().add(line);
                FadeTransition lineTransition = buildFadeAnimation(50, 0.0, 1.0, line);
                trans.getChildren().add(lineTransition);
            }
        }
        System.out.println("DDDDDDDDDDDDDDDDD - Route Produced");
        return trans;
    }

    public ArrayList<Line> produceRoute(List<GraphNode> route) {
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < route.size(); i++) {
            GraphNode start = route.get(i);
            if (i + 1 < route.size()) {
                GraphNode end = route.get(i + 1);
                Line line = new Line(this.xSpacing / 2 + start.getX() * xSpacing,
                        this.ySpacing / 2 + start.getY() * ySpacing,
                        this.xSpacing / 2 + end.getX() * xSpacing,
                        this.ySpacing / 2 + end.getY() * ySpacing);
                lines.add(line);
            } else {
                i = route.size();
            }
        }

        return lines;
    }

    /**
     * Get the x spacing
     *
     * @return the xSpacing
     */
    public double getXSpacing() {
        return xSpacing;
    }

    /**
     * Get the y spacing
     *
     * @return the ySpacing
     */
    public double getYSpacing() {
        return ySpacing;
    }

    /**
     * Get the entities list
     *
     * @return the entities list
     */
    public List<Entity> getEntitiesToDraw() {
        return entitiesToDraw;
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
    public static FadeTransition buildFadeAnimation(double millis, double opac1, double opac2, Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(opac1);
        fadeTransition.setToValue(opac2);
        return fadeTransition;
    }
}