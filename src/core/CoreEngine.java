package core;

import entity.Entity;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import javafx.application.Platform;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class CoreEngine {
    private static final Logger LOG = Logger.getLogger(CoreEngine.class.getName());
    private final int FRAME_RATE = 60;

    public static boolean running;
    public static boolean paused = false;
    private long startTime;
    private Graph graph;
    private ArrayList<Entity> entities;
    private UnitSpawner spawner;

    long deltaTime;

    private boolean slept = false;

    public CoreEngine() {
        this.graph = null;

        Graph graph = new Graph();

        for (int x = 0; x < Graph.WIDTH; x++) {
            for (int y = 0; y < Graph.HEIGHT; y++) {
                GraphNode node = new GraphNode(x, y);
                graph.nodeWith(node);
            }
        }

        for (GraphNode aNode : graph.getNodes()) {
            aNode.addNeighbours(graph);
        }

        this.graph = graph;

        entities = new ArrayList<>();
    }

    public Graph getGraph() {
        return graph;
    }

    public void startGame() {
        this.running = true;
        startTime = System.nanoTime();
        // @TODO in case it's not running
        while (running) {
            while(paused)
            {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isTimeToUpdate(startTime)) {
                updateGameState();
                this.slept = false;
            } else if (!slept) {
                waitForNextFrame();
                this.slept = true;
            }
        }
    }

    private boolean isTimeToUpdate(long startTime) {
        boolean timeToUpdate = false;

        long currentTime = System.nanoTime();
        deltaTime = currentTime - startTime;

        // NOTE: first five thread sleeps cause
        // interference with delta time.
        if (deltaTime >= (Math.pow(10, 9) / FRAME_RATE)) {
            timeToUpdate = true;
            this.startTime = currentTime;
        }
        return timeToUpdate;
    }

    private void waitForNextFrame() {
        try {
            Thread.sleep(950 / FRAME_RATE);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void updateGameState() {
        //may be null because startGame is called before renderer even instantiates (different threads but still not guaranteed)
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                Entity a = entities.get(i);

                // if the entity is a unit, update route if change route.
                if(a.getClass().getName() == "entity.Unit") {
                    Unit b = (Unit) a;
                    List<Line> route = b.getCurrentRoute();
                    b.update();
                    if(b.routeChanged()){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // forget the current route
                                GameRunTime.Instance().getRenderer().forgetRouteVisual(route);
                                // set the next route
                                b.setCurrentRoute(GameRunTime.Instance().getRenderer().produceRoute(b.getRoute()));
                                // draw the next route
                                GameRunTime.Instance().getRenderer().produceRouteVisual(b.getCurrentRoute()).play();
                                // notify that the unit has change route
                                b.setChangingRoute(false);
                            }
                        });;
                    }
                } else {
                    a.update();
                }
            }
        }

        // @TODO redundancy
        //if (spawner != null) {
          //  spawner.update();
       // }
    }

    public static void setEngineState(boolean running) {
        CoreEngine.running = running;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setSpawner(UnitSpawner spawner) {
        this.spawner = spawner;
    }
}