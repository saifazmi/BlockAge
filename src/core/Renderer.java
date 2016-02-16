package core;

import entity.Entity;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Renderer extends Group implements Observer {
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

    public ArrayList<Double> calculateSpacing() {
        ArrayList<Double> returnList = new ArrayList<>();
        double pixelWidth = scene.getWidth()-224; //subtract the right sidebar pixelWidth TODO @TODO//
        double pixelHeight = scene.getHeight(); //subtract the bottom bar height TODO @TODO//

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

    public void redraw() {
        this.getChildren().clear();
        initialDraw();
        entitiesToDraw.forEach(this::drawEntity);
    }

    public boolean drawEntity(Entity entity) {
        boolean success;
        if (!this.entitiesToDraw.contains(entity)) {
            this.entitiesToDraw.add(entity);
            entity.addObserver(this);
        }

        SpriteImage sprite = entity.getSprite();
        GraphNode node = entity.getPosition();


        sprite.setFitWidth(xSpacing);
        sprite.setFitHeight(ySpacing);
        sprite.setPreserveRatio(true);
        sprite.setX(entity.getCurrentPixelX());
        sprite.setY(entity.getCurrentPixelY());
        success = this.getChildren().add(sprite);
        return success;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("move graphical");
        Entity entity = (Entity) o;
        Entity oldEntity = (Entity) arg;

        entitiesToDraw.remove(oldEntity);
        //this.getChildren().remove(oldEntity.getSprite());
        Platform.runLater(() ->
        {
            this.getChildren().remove(oldEntity.getSprite());
        });
        entitiesToDraw.add(entity);
        //drawEntity(entity);
        Platform.runLater(() ->
        {
            drawEntity(entity);
        });
    }

    public void produceRouteVisual(List<GraphNode> route) {
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
    }

    public Group produceRouteVisualRecursive(List<GraphNode> route) {
        //could be inefficient, test @TODO
        if (route.size() == 2) {
            GraphNode start = route.get(0);
            GraphNode end = route.get(1);
            return new Group(new Line(this.xSpacing / 2 + start.getX() * xSpacing, this.ySpacing / 2 + start.getY() * ySpacing, this.xSpacing / 2 + end.getX() * xSpacing, this.ySpacing / 2 + end.getY() * ySpacing));
        } else {
            GraphNode start = route.get(0);
            GraphNode end = route.get(1);
            ArrayList<GraphNode> list = new ArrayList<GraphNode>();
            list.add(start);
            list.add(end);
            Group group = produceRouteVisualRecursive(list);
            route.remove(0);
            group.getChildren().addAll(produceRouteVisualRecursive(route));
            return group;
        }
    }


    public double returnXSpacing() {
        return xSpacing;
    }

    public double returnYSpacing() {
        return ySpacing;
    }

    public static FadeTransition buildFadeAnimation(double millis, double opac1, double opac2, Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(millis), node);
        fadeTransition.setAutoReverse(true);
        fadeTransition.setFromValue(opac1);
        fadeTransition.setToValue(opac2);
        return fadeTransition;
    }

}