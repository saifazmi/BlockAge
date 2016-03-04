package core;

import entity.Base;
import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.image.Image;
import sceneElements.Images;
import sceneElements.ElementsHandler;

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
        spawnBlockades(100);
        System.out.println("Spawned all the blockades");
        runTime.getScene().setOnMouseClicked(e -> {
            goal = Blockade.calcGraphNode(e);
            Base base = new Base(9999, "Base", goal, null);
            Image image = Images.base;
            Images.setSpriteProperties(base, image);
            renderer.drawInitialEntity(base);
            goal.setBase(base);
            runTime.setBasePlaced(true);
            runTime.getScene().setOnMouseClicked(null);
        });
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
