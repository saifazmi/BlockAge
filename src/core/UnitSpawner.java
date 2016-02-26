package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import entity.Entity;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import sceneElements.Images;
import sceneElements.SpriteImage;

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
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private GraphNode goal;
    private int spawnlimit;
    private Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;
    private Image image = null;

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
     * @param graph    The graph the Unit will be on, passed to Unit Constructor
     * @param renderer The Renderer the Unit will render its Sprite to, passed to Unit Constructor
     * @param goal     The Goal node to which the Unit's search will use, passed to Unit Constructor
     * @return A new Unit
     */
    private Unit CreateUnit(Graph graph, GraphNode goal) {
        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);

        if (Unit.Search.values()[index] == Unit.Search.BFS) {
            image = Images.imageDemon;
        } else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
            image = Images.imageDk;
        } else {
            image = Images.imageBanshee;
        }
        SpriteImage sprite = new SpriteImage(image, null);
        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal);
        sprite.setEntity(unit);

        // focus sprite and displays text when clicked on it
        sprite.setOnMouseClicked(e -> {
            sprite.requestFocus();
            ArrayList<Entity> units = engine.getEntities();
			for(int i=0; i<units.size(); i++) {
    			if(sprite.getEntity() == units.get(i)) {
    				GameInterface.unitDescriptionText.setFont(GameInterface.bellotaFont);
            		GameInterface.unitDescriptionText.setText("Name:   " + sprite.getEntity().getName() + "\n" + 
    		    										  "Search:  " + Unit.Search.values()[index] + "\n" +
    		    										  "Sort:      " + Unit.Sort.values()[index]);
            		// sets the image pressed for each unit accordingly to the search
            		if(Unit.Search.values()[index] == Unit.Search.BFS) {
            			sprite.setImage(Images.imagePressedDemon);
            			((Unit) units.get(i)).showTransition();
            		}
            		else if (Unit.Search.values()[index] == Unit.Search.A_STAR) {
            			sprite.setImage(Images.imagePressedDk);
            			((Unit) units.get(i)).showTransition();
            		}
            		else {
            			sprite.setImage(Images.imagePressedBanshee);
            			((Unit) units.get(i)).showTransition();
            		}
    			}
    			else {
    				SpriteImage obtainedSprite = units.get(i).getSprite();
                    Image image = obtainedSprite.getImage();
    				if (image.equals(Images.imagePressedDemon)) { 
    					units.get(i).getSprite().setImage(Images.imageDemon);
    					((Unit) units.get(i)).showTransition();
					}
					else if (image.equals(Images.imagePressedDk)) {
						units.get(i).getSprite().setImage(Images.imageDk);
						((Unit) units.get(i)).showTransition();
					}
					else if(image.equals(Images.imagePressedBanshee)) {
						units.get(i).getSprite().setImage(Images.imageBanshee);
						((Unit) units.get(i)).showTransition();
					}
    			}
			}
        });
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
