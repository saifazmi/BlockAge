package core;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.image.Image;
import sceneElements.SpriteImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hung on 13/02/16.
 */
public class UnitSpawner {
    private CoreEngine engine = CoreEngine.Instance();
    private Graph graph = engine.getGraph();
    private Renderer renderer = Renderer.Instance();

    private ArrayList<Unit> unitPool;
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private int spawnlimit;
    Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;

    private static UnitSpawner instance;

    public static UnitSpawner Instance() {
        return instance;
    }

    public UnitSpawner() {
        instance = this;
        names = new String[]{"Banshee", "Demon", "Death knight"};
        descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        rndSearchGen = new Random(System.currentTimeMillis());
        unitPool = new ArrayList<>();
        // this should be passed in
        GraphNode goal = graph.getNodes().get(graph.getNodes().size() - 1);
        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
            CreateUnit(this.graph, this.renderer, goal);
        }
    }

    private Unit CreateUnit(Graph graph, Renderer renderer, GraphNode goal) {
        String SEPARATOR = File.separator;
        Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0.png", this.renderer.getXSpacing(), this.renderer.getYSpacing(), true, true);
        SpriteImage sprite = new SpriteImage(image, null);
        sprite.setOnMouseClicked(e -> {
            sprite.requestFocus();
            Unit unit = ((Unit)sprite.getEntity());
            unit.showTransition();
        });

        // doing random for now, could return sequence of numbers representing units wanted
        int index = rndSearchGen.nextInt(3);

        Unit unit = new Unit(unitPoolCount, names[index], graph.nodeWith(new GraphNode(0, 0)), sprite, Unit.Search.values()[index], Unit.Sort.values()[index], graph, goal, renderer);
        sprite.setEntity(unit);
        unitPool.add(unit);
        return unit;
    }

    private void spawnUnit() {
        Unit newUnit;

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(graph, renderer, graph.getNodes().get(graph.getNodes().size() - 1));
        }

        spawnCount++;

        engine.getEntities().add(newUnit);
        renderer.drawInitialEntity(newUnit);
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

    public void setSpawnlimit(int spawnlimit) {
        this.spawnlimit = spawnlimit;
    }
}
