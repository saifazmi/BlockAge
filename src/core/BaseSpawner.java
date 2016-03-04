package core;

import entity.Base;
import entity.Blockade;
import entity.SortableBlockade;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import maps.MapParser;
import sceneElements.SpriteImage;
import java.io.*;
import sceneElements.ElementsHandler;
import sceneElements.Images;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, and Hung Hoang
 * @date : 25/02/16, last edited by ___ on
 */
public class BaseSpawner {

    private static final Logger LOG = Logger.getLogger(BaseSpawner.class.getName());
    private Renderer renderer = Renderer.Instance();
    private GameRunTime runTime = GameRunTime.Instance();

    private String SEPARATOR = File.separator;

    private GraphNode goal;
    private static BaseSpawner instance = null;

    public static BaseSpawner Instance() {
        if (instance == null) {
            instance = new BaseSpawner();
        }
        return instance;
    }

    public BaseSpawner() {
        instance = this;
        runTime.getScene().setOnMouseClicked(e -> {
            goal = Blockade.calcGraphNode(e);
            Base base = new Base(9999, "Base", goal, null);
            Image image = Images.base;
            Images.setSpriteProperties(base, image);
            renderer.drawInitialEntity(base);
            goal.setBase(base);
            runTime.setBasePlaced(true);
            protectBase(goal);
            //spawnBlockades(100);
            generateBlockades();
            runTime.getScene().setOnMouseClicked(null);
        });

    }

    private void generateBlockades() {
        InputStream in = getClass().getResourceAsStream(SEPARATOR + "maps" + SEPARATOR + "map0.txt");

        Reader fReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(fReader);

        MapParser parser = new MapParser(reader);
        parser.generateBlockades();
    }

    private void protectBase(GraphNode base) {

        int row = base.getX();
        int col = base.getY();

        for (int i = (row - 1); i <= (row + 1); i++) {

            for (int j = (col - 1); j <= (col + 1); j++) {

                // coordinate should be on grid and not the same as the base
                if (isOnGrid(base) && !(i == row && j == col)) {

                    SortableBlockade sortableBlockadeInstance = new SortableBlockade(1, "Sortable Blockade", new GraphNode(i, j), null, null);
                    Images.setSpriteProperties(sortableBlockadeInstance, Images.sortableImage1);
                    SortableBlockade blockade = SortableBlockade.create(sortableBlockadeInstance);
                    if (blockade != null) {
                        renderer.drawInitialEntity(blockade);
                    }
                }
            }
        }
    }

    private boolean isOnGrid(GraphNode position) {
        return !((position.getX() < 0 || position.getY() < 0) ||
                (position.getX() >= Graph.WIDTH || position.getY() >= Graph.HEIGHT));

    }

    public GraphNode getGoal() {
        return goal;
    }

    private void spawnBlockades(int count) {
        if (ElementsHandler.options.getInitialBlockades()) {
            while (count > 0) {
                System.out.println("Blockade " + count);
                Random rand = new Random();
                int randomX = rand.nextInt(Graph.HEIGHT);
                int randomY = rand.nextInt(Graph.WIDTH);
                Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(randomX, randomY), null);
                Images.setSpriteProperties(blockadeInstance, Images.unsortableImage1);
                Blockade blockade = Blockade.randomBlockade(blockadeInstance);
                if (blockade != null) {
                    renderer.drawInitialEntity(blockade);
                    count--;
                }
            }
        }
    }
}
