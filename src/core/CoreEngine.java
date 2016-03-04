package core;

import entity.Entity;
import graph.Graph;
import graph.GraphNode;
import sound.SoundManager;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, and Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 25/02/16
 */
public class CoreEngine {

    private static final Logger LOG = Logger.getLogger(CoreEngine.class.getName());
    private final int FRAME_RATE = 60;

    private boolean running;
    private boolean paused = false;
    private long startTime;
    private Graph graph;
    private ArrayList<Entity> entities;
    private UnitSpawner spawner;
    private boolean slept = false;
    private static CoreEngine instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     * @return the only engine to be created
     */
    public static CoreEngine Instance() {
        if(instance == null)
        {
            instance = new CoreEngine();
        }
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
    /**
     * Gets the graph that the game is running on
     * @return the graph
     */
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
        // added for sound
        SoundManager.Instance().startSoundtrack();
        //

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
     * @param startTime the time since the last update
     * @return Whether its time to update or not
     */
    private boolean isTimeToUpdate(long startTime) {
        boolean timeToUpdate = false;
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - startTime;
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
    /**
     * Check if the engine is paused
     * @return the engine paused state
     */
    public boolean isPaused() {
        return paused;
    }
    /**
     * Set the paused state of the engine.
     * @param paused the new paused state
     */
    public void setPaused(boolean paused) {
        System.out.println("Paused set:" + paused);
        this.paused = paused;
    }
    /**
     * Check if the engine is running
     * @return the engine running state
     */
    public boolean isRunning() {
        return running;
    }
    /**
     * Sets the running state of the engine
     * @param running the state to set the engine to
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    /**
     * Gets the list of entities that the engine updates
     * @return the list of entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    /**
     * Sets the spawner for this engine to use
     * @param spawner the spawner used
     */
    public void setSpawner(UnitSpawner spawner) {
        this.spawner = spawner;
    }
}
