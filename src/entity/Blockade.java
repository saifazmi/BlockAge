package entity;

import core.CoreEngine;
import core.GameRunTime;
import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Blockade extends Entity {
    private static final Logger LOG = Logger.getLogger(Blockade.class.getName());
    private boolean breakable;

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param position
     * @param sprite
     */
    public Blockade(int id, String name, GraphNode position, SpriteImage sprite) {
        super(id, name, position, sprite);
        setBreakable(false);
    }

    /**
     * Constructor
     *
     * @param id
     * @param name
     * @param description
     * @param position
     * @param sprite
     */
    public Blockade(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        super(id, name, description, position, sprite);
        setBreakable(false);
    }

    /**
     * Abstract method from Entity that required implementation.
     * Left empty as nothing is necessary.
     */
    @Override
    public void update() {

    }

    /**
     * Check if the blockade is breakable
     *
     * @return the value of breakable
     */
    public boolean isBreakable() {
        return breakable;
    }

    /**
     * Set the boolean breakbale
     *
     * @param breakable the boolean to set breakable to
     */
    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    /**
     * Creates a blockade at a given mouse event, on a given run time, and respecting a given blockade instance
     *
     * @param e                the mouse event to get the graphnode from
     * @param runTime          the runtime to put the blockade on
     * @param blockadeInstance the blockade object to 'duplicate'
     * @return the blockade created (null if not in grid, clicked on a blockade, clicked on a unit)
     */
    public static Blockade createBlockade(MouseEvent e, GameRunTime runTime, Blockade blockadeInstance) {
        GraphNode node = calcGraphNode(e, runTime);
        if (node != null) {
            Blockade blockade = new Blockade(calcId(runTime), blockadeInstance.getName(), calcGraphNode(e, runTime), blockadeInstance.getSprite());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

    /**
     * Get all current blockades from the engine.
     *
     * @param engine the engine on which to get blockades
     * @return the list of blockades
     */
    private static ArrayList<Blockade> getBlockades(CoreEngine engine) {
        ArrayList<Blockade> blockades = new ArrayList<>();
        ArrayList<Entity> entities = engine.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Blockade) {
                blockades.add((Blockade) entity);
            }
        }
        return blockades;
    }

    /**
     * Calculates the graphnode representation of a mouse click
     *
     * @param e       the mouse event ot be considered
     * @param runTime the runtime on which the click should be registered
     * @return the graphnode created
     */
    protected static GraphNode calcGraphNode(MouseEvent e, GameRunTime runTime) {
        CoreEngine engine = runTime.getEngine();
        Renderer renderer = runTime.getRenderer();

        double xSpacing = renderer.getXSpacing();
        double ySpacing = renderer.getYSpacing();
        double x = e.getX();
        double y = e.getY() - 34;                //@TODO subtract pane height of pauls menu
        double logicalX = Math.floor(x / xSpacing);
        double logicalY = Math.floor(y / ySpacing);

        if (logicalX >= 0 && logicalX < Graph.WIDTH && logicalY >= 0 && logicalY <= Graph.HEIGHT) {
            GraphNode position = engine.getGraph().nodeWith(new GraphNode((int) logicalX, (int) logicalY));
            System.out.println(position.toString());
            return position;
        }
        return null;
    }

    /**
     * Calculates the id that should be assigned to the next blockade
     *
     * @param runTime the run timem on which to create the blockade
     * @return the id to use for the new blockade
     */
    protected static int calcId(GameRunTime runTime) {
        ArrayList<Blockade> blockades = getBlockades(runTime.getEngine());
        int max = 0;
        for (int i = 0; i < blockades.size(); i++) {
            if (max < blockades.get(i).getId()) {
                max = blockades.get(i).getId();
            }
        }
        int id = max + 1;
        return id;
    }
}
