package core;

import entity.Entity;
import graph.Graph;
import graph.GraphNode;

import java.util.ArrayList;
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

    private boolean running;
    private boolean paused = false;
    private long startTime;
    private Graph graph;
    //Entites that the CoreEngine will 'update'
    private ArrayList<Entity> entities;
    private UnitSpawner spawner;

    private long deltaTime;
    private boolean slept = false;

    private static CoreEngine instance;

    public static CoreEngine Instance() {
        return instance;
    }

    /**
     * The graph used by the game instance will be instantiated in the CoreEngine,
     * All the nodes will be created and added to the graph, each will have their corresponding neighbours added
     */
    public CoreEngine() {
        instance = this;
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

    /**
     * Called to start the game's ticker.
     * The frame rate is 60 frames per second, so checks if its time to update yet (every 1/60 a second),
     * if it is, update the game's state, otherwise wait until next frame. Waiting only occurs if this thread has not yet slept,
     * because waiting will put the thread to sleep.
     */
    public void startGame() {
        running = true;
        startTime = System.nanoTime();
        // @TODO in case it's not running
        while (running) {
            while (paused) {
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

    /**
     * Checks if its time to update,
     * this is done by checking if the change in time since the last update is larger than or equals to
     * the frame rate (1/60 a second). Resets the time the clock starts counting for the next update if it is time to update
     *
     * @param startTime
     * @return Whether its time to update or not
     */
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

    /**
     * Waits for the next frame, puts this thread to sleep whilst waiting
     */
    private void waitForNextFrame() {
        try {
            Thread.sleep(950 / FRAME_RATE);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Updates all game objects that need updating, includes all the entites, spawner
     */
    private void updateGameState() {
        //may be null because startGame is called before renderer even instantiates (different threads but still not guaranteed)
        if (entities != null) {
            for (Entity entity : entities) {
                entity.update();
            }
        }

        if (spawner != null) {
            spawner.update();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the spawner for this engine to use
     *
     * @param spawner the spawner used
     */
    public void setSpawner(UnitSpawner spawner) {
        this.spawner = spawner;
    }
}
