package core;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import sceneElements.SpriteImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hung on 13/02/16.
 */
public class UnitSpawner {

    private ArrayList<Unit> unitPool;
    private int unitPoolCount = 0;
    private int totalSpawnables = 10;
    private int spawnCount = 0;
    private int spawnlimit;
    private GameRunTime runTime;
    Random rndSearchGen;

    private String[] names;
    private String[] descriptions;
    private int cooldown = 60;

    public UnitSpawner(GameRunTime runTime) {
        names = new String[]{"Banshee", "Demon", "Death knight"};
        descriptions = new String[]{"Depth First Search", "Breadth First Search", "A* Search", "Selection Sort", "Insertion Sort", "Bubble Sort"};
        rndSearchGen = new Random(System.currentTimeMillis());
        unitPool = new ArrayList<>();

        this.runTime = runTime;

        Graph graph = runTime.getEngine().getGraph();
        Renderer renderer = runTime.getRenderer();
        // this should be passed in
        GraphNode goal = graph.getNodes().get(graph.getNodes().size() - 1);

        for (unitPoolCount = 0; unitPoolCount < totalSpawnables; unitPoolCount++) {
            CreateUnit(graph, renderer, goal);
        }
    }

    private Unit CreateUnit(Graph graph, Renderer renderer, GraphNode goal) {
        String SEPARATOR = File.separator;
        Image image = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
        SpriteImage sprite = new SpriteImage(image, null);
        sprite.setOnMouseClicked(e -> {
            sprite.requestFocus();
            Unit unit = ((Unit)sprite.getEntity());
            SequentialTransition currentTrans = unit.getVisualTransition();
            if(currentTrans == null && unit.getRoute() != null)
            {
                SequentialTransition transition = runTime.getRenderer().produceRouteVisual(runTime.getRenderer().produceRoute(unit.getRoute(), unit.getPosition()));
                unit.setVisualTransition(transition);
                transition.play();
            }
            else
            {
                currentTrans.stop();
                ObservableList<Animation> transitions = currentTrans.getChildren();
                for(int i = 0; i < transitions.size(); i++)
                {
                    FadeTransition trans = (FadeTransition)transitions.get(i);
                    Line line = (Line)trans.getNode();
                    line.setOpacity(0.0);
                    line = null;
                }
                currentTrans = null;
                unit.setVisualTransition(currentTrans);
            }
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
        Graph graph = this.runTime.getEngine().getGraph();

        if (unitPool.size() > 0) {
            newUnit = unitPool.remove(0);
        } else {
            newUnit = CreateUnit(runTime.getEngine().getGraph(), runTime.getRenderer(), graph.getNodes().get(graph.getNodes().size() - 1));
        }

        spawnCount++;

        runTime.getEngine().getEntities().add(newUnit);
        runTime.getRenderer().drawInitialEntity(newUnit);
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
