package core;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;
import stores.ImageStore;
import stores.LambdaStore;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author : Hung Hoang; Contributors - Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Spawns the units at certain time intervals.
 * @date : 13/02/16
 */
public class UnitSpawner {

    //A pool of units instantiated at start-time, prevents lagging from Garbage Collection
    private ArrayList<Unit> unitPool;
    private int unitPoolCount;

    // totalSpawnables must be >= to spawnlimit
    private int totalSpawnables = 5;
    private int spawnCount = 0;
    private GraphNode goal;
    private int spawnlimit;
    private Random rndSearchGen;

    // Properties
    private String[] names;
    private int cooldown = 0;

    // Dependencies.
    private CoreEngine engine = CoreEngine.Instance();
    private Graph graph = engine.getGraph();
    private Renderer renderer = Renderer.Instance();

    // Instance for singleton.
    private static UnitSpawner instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the unit spawner instance
     */
    public static UnitSpawner Instance() {

        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {

        instance = null;
    }

    /**
     * Creates enemy unit for a game.
     * Instantiates here the list of names and description for units.
     * Calls the CreateUnit method for a certain amount specified by programmer.
     */
    public UnitSpawner(int spawnlimit, GraphNode goal) {

        instance = this;

        // The names of our units that will be spawned on the map
        this.names = new String[]{
                "Banshee",
                "Demon",
                "Death knight"
        };

        this.rndSearchGen = new Random(System.currentTimeMillis());
        this.unitPool = new ArrayList<>();
        this.goal = goal;
        this.spawnlimit = spawnlimit;

        // Creates the units and put them in a pool from where they will be taken to be spawned
        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
            create(graph, goal);
        }
    }

    /**
     * Creates a SpriteImage and set up its appropriate listeners for Mouse Click.
     * Create a new Unit with the appropriate search and sort algorithm indicator 'attached'.
     * Sets the 2-way relationship between the sprite and the unit.
     * Adds this newly created unit to the 'Pool' of units
     *
     * @param graph The graph the Unit will be on, passed to Unit Constructor
     * @param goal  The Goal node to which the Unit's search will use, passed to Unit Constructor
     * @return A new Unit
     */
    private Unit create(Graph graph, GraphNode goal) {

        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);
        Image image;

        // Setting the images appropriately for different searches
        // BFS - demon, A_STAR - death knight, DFS - banshee
        if (Unit.Search.values()[index] == Unit.Search.BFS) {
            image = ImageStore.imageDemon;
        } else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
            image = ImageStore.imageDk;
        } else {
            image = ImageStore.imageBanshee;
        }

        SpriteImage sprite = new SpriteImage(image, null);

        Unit unit = new Unit(
                unitPoolCount,
                names[index],
                graph.nodeWith(new GraphNode(0, 0)),
                sprite,
                Unit.Search.values()[index],
                Unit.Sort.values()[index],
                graph,
                goal
        );

        sprite.setEntity(unit);

        // focus sprite and displays text when clicked on it
        if (ElementsHandler.options.getShowPath()) {
            sprite.setOnMouseClicked(LambdaStore.Instance().getUnitClickEvent());
        }

        // adds the units into an array list
        unitPool.add(unit);

        return unit;
    }

    /**
     * Actually puts the unit into the game by taking it out of the unit pool
     * and putting it into the list of units in the Core Engine.
     * If the pool is empty, creates a new Unit and put that into the Core Engine's list instead
     */
    private void spawnUnit() {

        Unit newUnit;

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = create(this.graph, this.goal);
        }

        spawnCount++;
        engine.getUnits().add(newUnit);

        Platform.runLater(() -> renderer.drawInitialEntity(newUnit));
    }

    /**
     * Updates the spawner itself, If the number of Units in game is less than the set limit, spawn a new one
     */
    public void update() {

        if (cooldown > 0) {
            cooldown--;

        } else if (spawnCount < spawnlimit) {

            this.cooldown = 300;
            spawnUnit();
        }
    }
}
