package core;

import entity.Base;
import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import maps.MapParser;
import sceneElements.SpriteImage;

import java.io.*;
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

    private static String SEPARATOR = File.separator;

    private GraphNode goal;
    private static BaseSpawner instance = null;

    public static BaseSpawner Instance() {
        if (instance == null) {
            instance = new BaseSpawner();
        }
        return instance;
    }

    /*public BaseSpawner() {
        instance = this;
        // create a number of blockade randomly
        int blockadeNeeded = 10;
        while (blockadeNeeded > 0) {
            Random rand = new Random();
            int randomX = rand.nextInt(Graph.HEIGHT);
            int randomY = rand.nextInt(Graph.WIDTH);
            Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(randomX, randomY), null);
            Image image1 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, false, false);
            SpriteImage spriteImage1 = new SpriteImage(image1, blockadeInstance);
            spriteImage1.setFitWidth(renderer.getXSpacing());
            spriteImage1.setFitHeight(renderer.getYSpacing());
            spriteImage1.setPreserveRatio(false);
            spriteImage1.setSmooth(true);
            blockadeInstance.setSprite(spriteImage1);
            Blockade blockade = Blockade.randomBlockage(blockadeInstance);
            if (blockade != null) {
                renderer.drawInitialEntity(blockade);
                blockadeNeeded--;
                //LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
            }

            //LOG.log(Level.INFO, "Outside the MouseEvent");
            // setting function for choosing the goal of the game
            GameRunTime.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    //LOG.log(Level.INFO, "Reached the MouseEvent");
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
                        Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Blockade_sprite.png");
                        SpriteImage spriteImage = new SpriteImage(image, base);
                        spriteImage.setFitWidth(renderer.getXSpacing());
                        spriteImage.setFitHeight(renderer.getYSpacing());
                        spriteImage.setPreserveRatio(false);
                        spriteImage.setSmooth(true);
                        base.setSprite(spriteImage);
                        //LOG.log(Level.INFO, "Base created at: (x, " + base.getPosition().getX() + "), (y, " + base.getPosition().getY() + ")");
                        renderer.drawInitialEntity(base);
                        goal.setBase(base);
                        GameRunTime.Instance().setBasePlaced(true);

                        // remove the setting function
                        GameRunTime.getScene().setOnMouseClicked(null);
                    }
                }
            });
        }

    }*/

    public BaseSpawner()
    {
        instance = this;
        //rnd use for randomising between maps
        Random rnd = new Random();
        InputStream in = getClass().getResourceAsStream(SEPARATOR + "maps" + SEPARATOR + "map0.txt");

        Reader fReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(fReader);

        MapParser parser = new MapParser(reader);
        parser.generateBlockades();

        GameRunTime.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //LOG.log(Level.INFO, "Reached the MouseEvent");
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
                    Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Blockade_sprite.png");
                    SpriteImage spriteImage = new SpriteImage(image, base);
                    spriteImage.setFitWidth(renderer.getXSpacing());
                    spriteImage.setFitHeight(renderer.getYSpacing());
                    spriteImage.setPreserveRatio(false);
                    spriteImage.setSmooth(true);
                    base.setSprite(spriteImage);
                    //LOG.log(Level.INFO, "Base created at: (x, " + base.getPosition().getX() + "), (y, " + base.getPosition().getY() + ")");
                    renderer.drawInitialEntity(base);
                    goal.setBase(base);
                    GameRunTime.Instance().setBasePlaced(true);

                    // remove the setting function
                    GameRunTime.getScene().setOnMouseClicked(null);
                }
            }
        });

    }

    public GraphNode getGoal() {
        return goal;
    }
}
