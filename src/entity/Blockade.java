package entity;

import core.CoreEngine;
import core.Renderer;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
     * @param blockadeInstance the blockade object to 'duplicate'
     * @return the blockade created (null if not in grid, clicked on a blockade, clicked on a unit)
     */
    public static Blockade createBlockade(MouseEvent e, Blockade blockadeInstance) {
        GraphNode node = calcGraphNode(e);
        if (node != null) {
            Blockade blockade = new Blockade(calcId(), blockadeInstance.getName(), calcGraphNode(e), blockadeInstance.getSprite());
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
     * @return the list of blockades
     */
    private static ArrayList<Blockade> getBlockades() {

        ArrayList<Blockade> blockades = new ArrayList<>();
        ArrayList<Entity> entities = CoreEngine.Instance().getEntities();
        blockades.addAll(entities.stream().filter(entity -> entity instanceof Blockade).map(entity -> (Blockade) entity).collect(Collectors.toList()));
        /*
            Equivalent to:
            for (Entity entity : entities)
        {
            if (entity instanceof Blockade)
            {
                blockades.add((Blockade) entity);
            }
        }
         */
        return blockades;
    }

    /**
     * Calculates the graphnode representation of a mouse click
     *
     * @param e the mouse event ot be considered
     * @return the graphnode created
     */
    protected static GraphNode calcGraphNode(MouseEvent e) {
        Renderer renderer = Renderer.Instance();

        double xSpacing = renderer.getXSpacing();
        double ySpacing = renderer.getYSpacing();
        double x = e.getX();
        double y = e.getY();                //@TODO subtract pane height of pauls menu
        double logicalX = Math.floor(x / xSpacing);
        double logicalY = Math.floor(y / ySpacing);

        if (logicalX >= 0 && logicalX < Graph.WIDTH && logicalY >= 0 && logicalY <= Graph.HEIGHT) {
            GraphNode position = CoreEngine.Instance().getGraph().nodeWith(new GraphNode((int) logicalX, (int) logicalY));

            System.out.println(position.toString());
            return position;
        }
        return null;
    }

    /**
     * Calculates the id that should be assigned to the next blockade
     *
     * @return the id to use for the new blockade
     */
    protected static int calcId() {
        ArrayList<Blockade> blockades = getBlockades();

        int max = 0;
        for (Blockade blockade : blockades) {
            if (max < blockade.getId()) {
                max = blockade.getId();
            }
        }
        return max + 1;
    }
}
