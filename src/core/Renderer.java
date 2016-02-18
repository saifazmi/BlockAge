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
import java.util.logging.Logger;

public class Renderer extends Group// implements Observer
{
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    private Scene scene;
    private List<Entity> entitiesToDraw;
    private List<Line> linesToDraw;

    private double xSpacing;
    private double ySpacing;

    public Renderer(Scene scene) {
        super();
        this.scene = scene;
        this.entitiesToDraw = new ArrayList<>();
        this.linesToDraw = new ArrayList<>();
    }

    /**
     * Function to perform the initial draw of the screen.
     * This includes calculating the xSpacing, ySpacing, and the grid lines
     * @return success boolean representing the success of the operation
     */
    public boolean initialDraw() {
        boolean success = true;
        ArrayList<Double> results = calculateSpacing();
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
     * @param xSpacing the x width of grid quadrilaterals
     * @param ySpacing the y width of grid quadrilaterals
     * @param width the width of the rendering pane
     * @param height the height of the rendering pane
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
     * @return an array list containing 6 things ordered as follows:
     *
        width           Number of GraphNodes on the width
        height          Number of GraphNodes on the height
        xSpacing        Pixel width of each grid quadrilateral
        ySpacing        Pixel height of each grid quadrilateral
        pixelWidth      Pane width in pixels
        pixelHeight     Pane height in pixels
     */
    public ArrayList<Double> calculateSpacing() {
        ArrayList<Double> returnList = new ArrayList<>();
        double pixelWidth = scene.getWidth() - 224; //subtract the right sidebar pixelWidth TODO @TODO//
        double pixelHeight = scene.getHeight() - 48; //subtract the bottom bar height TODO @TODO//

        int width = Graph.WIDTH;
        int height = Graph.HEIGHT;
        double xSpacing = pixelWidth / (width);
        double ySpacing = pixelHeight / (height);

        returnList.add((double) width);
        returnList.add((double) height);
        returnList.add(xSpacing);
        returnList.add(ySpacing);
        returnList.add(pixelWidth);
        returnList.add(pixelHeight);
        return returnList;                            //ordered return list, see above for order
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
     * @param entity the entity to be drawn
     * @return success boolean representing the success of the operation
     */
    public boolean drawInitialEntity(Entity entity) {
        boolean success;
        if (!this.entitiesToDraw.contains(entity)) {
            this.entitiesToDraw.add(entity);
            //entity.addObserver(this);
        }

        SpriteImage sprite = entity.getSprite();
        GraphNode node = entity.getPosition();

        sprite.setFitWidth(xSpacing);
        sprite.setFitHeight(ySpacing);
        sprite.setPreserveRatio(true);
        //sprite.setX(entity.getCurrentPixelX());
        //sprite.setY(entity.getCurrentPixelY());
        sprite.setX(node.getX() * xSpacing);
        sprite.setY(node.getY() * ySpacing);
        success = this.getChildren().add(sprite);
        System.out.println(node);
        System.out.println(sprite.getX() + "," + sprite.getY());
        return success;
    }

    /**
     * Produces a Transition effect to play for a given list of lines.
     * @param route the list of lines to be given transitions
     * @return the transition created
     */
    public SequentialTransition produceRouteVisual(List<GraphNode> route) {
        //test @TODO
        SequentialTransition trans = new SequentialTransition();
        for (int i = 0; i < route.size(); i++) {
            GraphNode start = route.get(i);
            if (i + 1 < route.size()) {
                GraphNode end = route.get(i + 1);
                Line line = new Line(this.xSpacing / 2 + start.getX() * xSpacing,
                        this.ySpacing / 2 + start.getY() * ySpacing,
                        this.xSpacing / 2 + end.getX() * xSpacing,
                        this.ySpacing / 2 + end.getY() * ySpacing);
                line.setOpacity(0.0);
                if (!this.getChildren().contains(line)) {
                    this.getChildren().add(line);
                    linesToDraw.add(line);
                    FadeTransition lineTransition = buildFadeAnimation(50, 0.0, 1.0, line);
                    trans.getChildren().add(lineTransition);
                }
            } else {
                i = route.size();
            }
        }
        trans.play();
        return trans;
    }

    /**
     * Turns off a transition, and sets the opacity of all lines involved to 0
     * @param transition the transition to be removed
     */
    public void removeTransition(SequentialTransition transition) {
        for (int i = 0; i < transition.getChildren().size(); i++) {
            FadeTransition trans = (FadeTransition) transition.getChildren().get(i);
            Line line = (Line) trans.getNode();
            line.setOpacity(0.0);
            this.linesToDraw.remove(line);
        }
    }

    /**
     * Get the x spacing
     * @return the xSpacing
     */
    public double getXSpacing() {
        return xSpacing;
    }

    /**
     * Get the y spacing
     * @return the ySpacing
     */
    public double getYSpacing() {
        return ySpacing;
    }

    /**
     * Get the entities list
     * @return the entities list
     */
    public List<Entity> getEntitiesToDraw() {
        return entitiesToDraw;
    }

    /**
     * Builds a fade animation with the given inputs
     * @param millis the millisecond time the transition should occur over
     * @param opac1 the opacity to start at
     * @param opac2 the opacity to end at
     * @param node the node that the transition should be performed on
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