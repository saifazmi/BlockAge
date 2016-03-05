package core;

import entity.Base;
import entity.Blockade;
import entity.SortableBlockade;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.scene.image.Image;
import sceneElements.ElementsHandler;
import sceneElements.ImageStore;

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
            Image image = ImageStore.base;
            ImageStore.setSpriteProperties(base, image);
            renderer.drawInitialEntity(base);
            goal.setBase(base);
            runTime.setBasePlaced(true);
            // surround the base with sortable blockades.
            protectBase(goal);
            // generate 100 random blockades.
            spawnBlockades(100);
            runTime.getScene().setOnMouseClicked(null);
        });

    }

    private void protectBase(GraphNode base) {

        int row = base.getX();
        int col = base.getY();

        for (int i = (row - 1); i <= (row + 1); i++) {

            for (int j = (col - 1); j <= (col + 1); j++) {

                // coordinate should be on grid and not the same as the base
                if (isOnGrid(i, j) && !(i == row && j == col)) {

                    SortableBlockade sortableBlockadeInstance = new SortableBlockade(1, "Sortable Blockade", new GraphNode(i, j), null, null);
                    ImageStore.setSpriteProperties(sortableBlockadeInstance, ImageStore.sortableImage1);
                    SortableBlockade blockade = SortableBlockade.create(sortableBlockadeInstance);
                    if (blockade != null) {
                        renderer.drawInitialEntity(blockade);
                    }
                }
            }
        }
    }

    private boolean isOnGrid(int row, int col) {

        return !((row < 0 || col < 0 ||
                row >= Graph.HEIGHT || col >= Graph.WIDTH));
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
                ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
                Blockade blockade = Blockade.randomBlockade(blockadeInstance);
                if (blockade != null) {
                    renderer.drawInitialEntity(blockade);
                    count--;
                }
            }
        }
    }
}
