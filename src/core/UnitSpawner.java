package core;

import entity.Base;
import entity.Blockade;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import sceneElements.SpriteImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hung on 13/02/16.
 */
public class UnitSpawner {

    private static final Logger LOG = Logger.getLogger(UnitSpawner.class.getName());
    private static String SEPARATOR = File.separator;

    private ArrayList<Unit> unitPool;
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private int spawnlimit;
    private GameRunTime runTime;
    private GraphNode goal;
    Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;

    public UnitSpawner(GameRunTime runTime, int spawnlimit) {
        names = new String[]{"Banshee", "Demon", "Death knight"};
        descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        rndSearchGen = new Random(System.currentTimeMillis());
        unitPool = new ArrayList<>();

        this.runTime = runTime;
        this.spawnlimit = spawnlimit;
        Renderer renderer = runTime.getRenderer();
        CoreEngine engine = runTime.getEngine();
        Graph graph = engine.getGraph();

        // create a number of blockade randomly
        int blockadeNeeded = 10;
        while(blockadeNeeded > 0) {
            Random rand = new Random();
            int randomX = rand.nextInt(graph.HEIGHT);
            int randomY = rand.nextInt(graph.WIDTH);
            Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(randomX, randomY), null);
            Image image1 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, false, false);
            SpriteImage spriteImage1 = new SpriteImage(image1, blockadeInstance);
            spriteImage1.setFitWidth(renderer.getXSpacing());
            spriteImage1.setFitHeight(renderer.getYSpacing());
            spriteImage1.setPreserveRatio(false);
            spriteImage1.setSmooth(true);
            blockadeInstance.setSprite(spriteImage1);
            Blockade blockade = Blockade.randomBlockage(runTime, blockadeInstance);
            if (blockade != null) {
                renderer.drawInitialEntity(blockade);
                blockadeNeeded --;
                LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
            }
        }

        // setting function for choosing the goal of the game
        runTime.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                // get the limitation on where to click to choose the goal
                double xSpacing = renderer.getXSpacing();
                double ySpacing = renderer.getYSpacing();
                double x = e.getX();
                double y = e.getY() - 34;                //@TODO subtract pane height of pauls menu
                double logicalX = Math.floor(x / xSpacing);
                double logicalY = Math.floor(y / ySpacing);

                // checking if the position is within the boundaries
                if (logicalX >= 0 && logicalX < Graph.WIDTH && logicalY >= 0 && logicalY <= Graph.HEIGHT) {
                    // set the goal
                    goal = engine.getGraph().nodeWith(new GraphNode((int) logicalX, (int) logicalY));
                    System.out.println(goal.toString());

                    // make the base
                    Base base = new Base(9999, "Base", goal, null);
                    Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Blokage sprite copy.png");
                    SpriteImage spriteImage = new SpriteImage(image, base);
                    spriteImage.setFitWidth(renderer.getXSpacing());
                    spriteImage.setFitHeight(renderer.getYSpacing());
                    spriteImage.setPreserveRatio(false);
                    spriteImage.setSmooth(true);
                    base.setSprite(spriteImage);
                    LOG.log(Level.INFO, "Base created at: (x, " + base.getPosition().getX() + "), (y, " + base.getPosition().getY() + ")");
                    renderer.drawInitialEntity(base);
                    goal.setBase(base);
                    GameRunTime.Instance().setBasePlaced(true);

                    // remove the setting function
                    runTime.getScene().setOnMouseClicked(null);

                    // spawning the units
                    for (unitPoolCount = 0; unitPoolCount < spawnlimit; unitPoolCount++) {
                        spawnUnit();
                    }
                }
            }
        });
    }

    private Unit CreateUnit(Graph graph, Renderer renderer, GraphNode goal) {
        String SEPARATOR = File.separator;
        //new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, false, false);
        Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        SpriteImage sprite = new SpriteImage(image, null);
        sprite.setOnMouseClicked(e -> sprite.requestFocus());

        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);

        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal, renderer);
        sprite.setEntity(unit);
        //unit.setCurrentPixel(sprite.getX(), sprite.getY());
        unitPool.add(unit);

        return unit;
    }

    private void spawnUnit() {
        Unit newUnit;
        Graph graph = this.runTime.getEngine().getGraph();

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(runTime.getEngine().getGraph(), runTime.getRenderer(), goal);
        }

        spawnCount++;

        runTime.getEngine().getEntities().add(newUnit);
        runTime.getRenderer().drawInitialEntity(newUnit);
        // set the current route
        newUnit.setCurrentRoute(GameRunTime.Instance().getRenderer().produceRoute(newUnit.getRoute()));
        // draw the route
        runTime.getRenderer().produceRouteVisual(new ArrayList<Line>(), newUnit.getCurrentRoute()).play();
    }

    private void despawnUnit(Unit unit) {
        unitPool.add(unit);
        //remove from list here?
    }

    public void update() {
        /*if (cooldown > 0)
        {
            cooldown--;
        }
        else*/
        {
            if (spawnCount < spawnlimit) {
                cooldown = 60;
                spawnUnit();
            }
        }
    }

    // @TODO redundancy
    public void setSpawnlimit(int spawnlimit) {
        this.spawnlimit = spawnlimit;
    }
}
