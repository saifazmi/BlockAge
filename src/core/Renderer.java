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

public class Renderer extends Group
{
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

    public void redraw() {
        //@TODO redundant, will break transitions on resize
        this.getChildren().clear();
        initialDraw();
        entitiesToDraw.forEach(this::drawInitialEntity);
    }

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

    // delete the old route and draw new route
    public SequentialTransition produceRouteVisual(List<Line> oldRoute, List<Line> newRoute)
    {
        for (int i = 0; i <  oldRoute.size(); i++)
        {
            Line line = oldRoute.get(i);
            if(this.getChildren().contains(line))
            {
                this.getChildren().remove(line);
            }
        }

        System.out.println("DDDDDD" +
                "DDDDDDDDDDD - Route Forgotten");

        SequentialTransition trans = new SequentialTransition();
        for (int i = 0; i < newRoute.size(); i++)
        {
            Line line = newRoute.get(i);
            line.setOpacity(0.0);
            if(!this.getChildren().contains(line))
            {
                this.getChildren().add(line);
                FadeTransition lineTransition = buildFadeAnimation(50, 0.0, 1.0, line);
                trans.getChildren().add(lineTransition);
            }
        }
        if(newRoute.isEmpty()) {
            System.out.println("Unit dies");
        }
        System.out.println("DDDDDDDDDDDDDDDDD - Route Produced");
        return trans;
    }

    public ArrayList<Line> produceRoute(List<GraphNode> route)
    {
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < route.size(); i++)
        {
            GraphNode start = route.get(i);
            if(i + 1 < route.size())
            {
                GraphNode end = route.get(i + 1);
                Line line = new Line(this.xSpacing / 2 + start.getX() * xSpacing,
                        this.ySpacing / 2 + start.getY() * ySpacing,
                        this.xSpacing / 2 + end.getX() * xSpacing,
                        this.ySpacing / 2 + end.getY() * ySpacing);
                lines.add(line);
            }
            else
            {
                i = route.size();
            }
        }
        return lines;
    }

    public double getXSpacing() {
        return xSpacing;
    }

    public double getYSpacing() {
        return ySpacing;
    }

    public List<Entity> getEntitiesToDraw() {
        return entitiesToDraw;
    }

    public static FadeTransition buildFadeAnimation(double millis, double opac1, double opac2, Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(opac1);
        fadeTransition.setToValue(opac2);
        return fadeTransition;
    }

}