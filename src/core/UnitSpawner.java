package core;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import entity.Entity;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import gui.GameInterface;
import gui.Renderer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;
import stores.ImageStore;

/**
 * @author : First created by Hung Hoang with code by Hung Hoang, and Paul Popa
 * @date : 13/02/16, last edited by Paul Popa on 23/02/16
 */
public class UnitSpawner {
    private CoreEngine engine = CoreEngine.Instance();
    private Graph graph = engine.getGraph();
    private Renderer renderer = Renderer.Instance();
    private static final Logger LOG = Logger.getLogger(UnitSpawner.class.getName());

    //A pool of units instantiated at start-time, prevents lagging from Garbage Collection
    private ArrayList<Unit> unitPool;
    private int unitPoolCount;
    private int totalSpawnables = 2;
    private int spawnCount = 0;
    private GraphNode goal;
    private int spawnlimit;
    private Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;


    private static UnitSpawner instance;

    /**
     * Creates enemy unit for a game.
     * Instantiates here the list of names and description for units.
     * Calls the CreateUnit method for a certain amount specified by programmer.
     */
    public static UnitSpawner Instance() {
        return instance;
    }

    public UnitSpawner(int spawnlimit, GraphNode goal) {
        instance = this;
        this.names = new String[]{"Banshee", "Demon", "Death knight"};
        this.descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        this.rndSearchGen = new Random(System.currentTimeMillis());
        this.unitPool = new ArrayList<>();
        this.goal = goal;
        this.spawnlimit = spawnlimit;

        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
            CreateUnit(graph, goal);
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
    private Unit CreateUnit(Graph graph, GraphNode goal) {
        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);
        Image image;

        if (Unit.Search.values()[index] == Unit.Search.BFS) {
        	image = ImageStore.imageDemon;
        } else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
        	image = ImageStore.imageDk;
        } else {
        	image = ImageStore.imageBanshee;
        }
        SpriteImage sprite = new SpriteImage(image, null);
        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal);
        sprite.setEntity(unit);

        // focus sprite and displays text when clicked on it
        if (ElementsHandler.options.getShowPath()) {
            sprite.setOnMouseClicked(e -> {
                sprite.requestFocus();
                GameRunTime.Instance().setLastClicked(sprite);
                ArrayList<Entity> units = engine.getEntities();
                for (Entity unit1 : units) {
                    if (sprite.getEntity() == unit1) {
                        // sets the image pressed for each unit accordingly to the search
                    	GameInterface.namePaneLabel.setText("Name: " + sprite.getEntity().getName());
                    	GameInterface.searchPaneLabel.setText("Search: " + Unit.Search.values()[index]);
                    	GameInterface.sortPaneLabel.setText("Sort: " + Unit.Sort.values()[index]);
                        if (Unit.Search.values()[index] == Unit.Search.BFS) {
                        	sprite.setImage(ImageStore.imagePressedDemon);
                        	ImageView demon = new ImageView(ImageStore.imageDemon);
                        	demon.setFitHeight(80);
                        	demon.setFitWidth(80);
                        	GameInterface.unitImage.setGraphic(demon);

                        } else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
                        	sprite.setImage(ImageStore.imagePressedDk);
                        	ImageView dk = new ImageView(ImageStore.imageDk);
                        	dk.setFitHeight(80);
                        	dk.setFitWidth(80);
                        	GameInterface.unitImage.setGraphic(dk);
                        } else {
                        	sprite.setImage(ImageStore.imagePressedBanshee);
                        	ImageView banshee = new ImageView(ImageStore.imageBanshee);
                        	banshee.setFitHeight(80);
                        	banshee.setFitWidth(80);
                        	GameInterface.unitImage.setGraphic(banshee);
                        }
                    } else {
                        SpriteImage obtainedSprite = unit1.getSprite();
                        ElementsHandler.pressedToNotPressed(obtainedSprite);
                    }
                }
            });
        }
        // adds the units into an array list
        unitPool.add(unit);
        return unit;
    }

    /**
     * Actually puts the unit into the game by taking it out of the unit pool and putting it into the list of units in the Core Engine.
     * If the pool is empty, creates a new Unit and put that into the Core Engine's list instead
     */
    private void spawnUnit() {
        Unit newUnit;
        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(this.graph, this.goal);
        }
        spawnCount++;
        engine.getEntities().add(newUnit);
        Platform.runLater(() -> renderer.drawInitialEntity(newUnit));
    }

    /**
     * Moves the unit back into the pool if its not needed
     *
     * @param unit The Unit to move back
     */
    private void despawnUnit(Unit unit) {
        unitPool.add(unit);
        //remove from list here?
    }

    /**
     * Updates the spawner itself, If the number of Units in game is less than the set limit, spawn a new one
     */
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

    /**
     * Sets the limit to the number of unit to spawn in one game
     *
     * @param spawnlimit number of unit allowed to spawn
     */
    public void setSpawnlimit(int spawnlimit) {
        this.spawnlimit = spawnlimit;
    }
}
