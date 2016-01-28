import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class CoreEngine {

    private static final Logger LOG = Logger.getLogger(CoreEngine.class.getName());

    private boolean running;
    private final int FRAME_RATE = 60;
    private long startTime;

    private boolean slept = false;

    public CoreEngine() {
    }

    public void startGame() {
        this.running = true;
        startTime = System.nanoTime();

        while (running) {
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
        long deltaTime = currentTime - startTime;

        // NOTE: first five thread sleeps cause
        // interference with delta time.
        if (deltaTime >= (Math.pow(10, 9) / FRAME_RATE)) {
            LOG.log(Level.INFO, "Delta time: " + String.valueOf(deltaTime * Math.pow(10, -9)));
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

    }

}
