package core;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by hung on 13/02/16.
 */
public class UnitSpawner {

    //A pool of units instantiated at start-time, prevents lagging from Garbage Collection
    private ArrayList<Unit> unitPool;
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private int spawnlimit;
    private GameRunTime runTime;
    private Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;

    /**
     * Creates enemy unit for a game.
     * Instantiates here the list of names and description for units.
     * Calls the CreateUnit method for a certain amount specified by programmer.
     * @param runTime the 'Game Instance' this spawner is used for.
     */
    public UnitSpawner(GameRunTime runTime) {
        names = new String[]{"Banshee", "Demon", "Death knight"};
        descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        rndSearchGen = new Random(System.currentTimeMillis());
        unitPool = new ArrayList<>();

        this.runTime = runTime;

        Graph graph = runTime.getEngine().getGraph();
        Renderer renderer = runTime.getRenderer();
        GraphNode goal = graph.getNodes().get(graph.getNodes().size() - 1);

        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
            CreateUnit(graph, renderer, goal);
        }
    }

    /**
     * Creates a SpriteImage and set up its appropriate listeners for Mouse Click.
     * Create a new Unit with the appropriate search and sort algorithm indicator 'attached'.
     * Sets the 2-way relationship between the sprite and the unit.
     * Adds this newly created unit to the 'Pool' of units
     * @param graph The graph the Unit will be on, passed to Unit Constructor
     * @param renderer The Renderer the Unit will render its Sprite to, passed to Unit Constructor
     * @param goal The Goal node to which the Unit's search will use, passed to Unit Constructor
     * @return A new Unit
     */
    private Unit CreateUnit(Graph graph, Renderer renderer, GraphNode goal) {

        SpriteImage sprite = new SpriteImage("http://imgur.com/FAt5VBo.png", null);
        sprite.setOnMouseClicked(e ->
        {
            sprite.requestFocus();
        });

        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);

        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal, renderer);
        sprite.setEntity(unit);
        //unit.setCurrentPixel(sprite.getX(), sprite.getY());
        unitPool.add(unit);

        return unit;
    }

    /**
     * Actually puts the unit into the game by taking it out of the unit pool and putting it into the list of units in the Core Engine.
     * If the pool is empty, creates a new Unit and put that into the Core Engine's list instead
     */
    private void spawnUnit() {
        Unit newUnit;
        Graph graph = this.runTime.getEngine().getGraph();

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(runTime.getEngine().getGraph(), runTime.getRenderer(), graph.getNodes().get(graph.getNodes().size() - 1));
        }

        spawnCount++;

        runTime.getEngine().getEntities().add(newUnit);
        runTime.getRenderer().drawInitialEntity(newUnit);
        runTime.getRenderer().produceRouteVisual(newUnit.getRoute());
    }

    /**
     * Moves the unit back into the pool if its not needed
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
     * @param spawnlimit number of unit allowed to spawn
     */
    public void setSpawnlimit(int spawnlimit) {
        this.spawnlimit = spawnlimit;
    }
}
