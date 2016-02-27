package core;

import entity.Base;
import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.image.Image;
import sceneElements.Images;
import sceneElements.SpriteImage;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, and Hung Hoang
 * @date : 25/02/16, last edited by ___ on
 */
public class BaseSpawner {

    private static final Logger LOG = Logger.getLogger(BaseSpawner.class.getName());
    private CoreEngine engine = CoreEngine.Instance();
    private Graph graph = engine.getGraph();
    private Renderer renderer = Renderer.Instance();

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
        // create a number of blockade randomly
        int blockadeNeeded = 10;
        while (blockadeNeeded > 0) {
            Random rand = new Random();
            int randomX = rand.nextInt(Graph.HEIGHT);
            int randomY = rand.nextInt(Graph.WIDTH);
            Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(randomX, randomY), null);
            Image image1 = Images.unsortableImage1;
            SpriteImage spriteImage1 = new SpriteImage(image1, blockadeInstance);
            spriteImage1.setFitWidth(renderer.getXSpacing());
            spriteImage1.setFitHeight(renderer.getYSpacing());
            spriteImage1.setPreserveRatio(false);
            spriteImage1.setSmooth(true);
            blockadeInstance.setSprite(spriteImage1);
            Blockade blockade = Blockade.randomBlockade(blockadeInstance);
            if (blockade != null) {
                renderer.drawInitialEntity(blockade);
                blockadeNeeded--;
            }

            // setting function for choosing the goal of the game
            GameRunTime.Instance().getScene().setOnMouseClicked(e -> {
                // get the limitation on where to click to choose the goal
                double xSpacing = renderer.getXSpacing();
                double ySpacing = renderer.getYSpacing();
                double x = e.getX();
                double y = e.getY();
                double logicalX = Math.floor(x / xSpacing);
                double logicalY = Math.floor(y / ySpacing);

                // checking if the position is within the boundaries
                if (logicalX >= 0 && logicalX < Graph.WIDTH && logicalY >= 0 && logicalY <= Graph.HEIGHT) {
                    // set the goal
                    goal = graph.nodeWith(new GraphNode((int) logicalX, (int) logicalY));
                    System.out.println("goal at " + goal.toString());
                    // make the base
                    Base base = new Base(9999, "Base", goal, null);
                    Image image = Images.base;
                    SpriteImage spriteImage = new SpriteImage(image, base);
                    spriteImage.setFitWidth(renderer.getXSpacing());
                    spriteImage.setFitHeight(renderer.getYSpacing());
                    spriteImage.setPreserveRatio(false);
                    spriteImage.setSmooth(true);
                    base.setSprite(spriteImage);
                    renderer.drawInitialEntity(base);
                    goal.setBase(base);
                    GameRunTime.Instance().setBasePlaced(true);
                    // remove the setting function
                    GameRunTime.Instance().getScene().setOnMouseClicked(null);
                }
            });
        }
    }

    public GraphNode getGoal() {
        return goal;
    }
}
