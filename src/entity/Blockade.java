package entity;

import core.CoreEngine;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, Paul Popa, and Saif Amzi
 * @date : 28/01/16, last edited by Dominic Walters on 20/02/16
 */
public class Blockade extends Entity {

    private static final Logger LOG = Logger.getLogger(Blockade.class.getName());

    // Blockade property
    protected boolean breakable;

    /**
     * Builds a normal blockade with the given attributes.
     *
     * @param id       the ID of this entity
     * @param name     name of the entity
     * @param position position of the entity on the graph
     * @param sprite   the sprite image representing this entity
     */
    public Blockade(int id, String name, GraphNode position, SpriteImage sprite) {

        super(id, name, position, sprite);
        this.breakable = false;
    }

    /**
<<<<<<< HEAD
     * Builds a normal blockade with the given attributes.
     *
     * @param id          the ID of this entity
     * @param name        name of the entity
     * @param description additional detail for the entity
     * @param position    position of the entity on the graph
     * @param sprite      the sprite image representing this entity
     */
    public Blockade(int id, String name, String description, GraphNode position, SpriteImage sprite) {

        super(id, name, position, sprite);
        this.breakable = false;
    }

    /**
=======
>>>>>>> master
     * Abstract method from Entity that required implementation.
     * Left empty as nothing is necessary.
     */
    @Override
    public void update() {

        // Nothing to update
    }

    /**
     * Check if the blockade is breakable
     *
     * @return the value of breakable
     */
    public boolean isBreakable() {

        return this.breakable;
    }

    /**
     * Sets the breakable property of the blockade
     *
     * @param breakable the boolean to set breakable to
     */
    protected void setBreakable(boolean breakable) {

        this.breakable = breakable;
    }

    /**
     * Creates a blockade at a given mouse event, on a given run time, and respecting a given blockade instance
     *
     * @param e                the mouse event to get the graph node from
     * @param blockadeInstance the blockade object to 'duplicate'
     * @return the blockade if created else null
     */
    public static Blockade createBlockade(MouseEvent e, Blockade blockadeInstance) {

        GraphNode node = calcGraphNode(e);

        return create(blockadeInstance, node);
    }

    /**
     * Get all current blockades from the engine.
     *
     * @return the list of blockades
     */
    public static ArrayList<Blockade> getBlockades() {

        ArrayList<Blockade> blockades = new ArrayList<>();
        ArrayList<Entity> entities = CoreEngine.Instance().getEntities();

        blockades.addAll(
                entities.stream().filter(entity ->
                        entity instanceof Blockade).map(entity ->
                        (Blockade) entity).collect(Collectors.toList())
        );

        return blockades;
    }

    /**
     * Calculates the graph node representation of a mouse click
     *
     * @param e the mouse event to be used
     * @return the GraphNode created
     */
    public static GraphNode calcGraphNode(MouseEvent e) {

        Renderer renderer = Renderer.Instance();

        double xSpacing = renderer.getXSpacing();
        double ySpacing = renderer.getYSpacing();
        double x = e.getX();
        double y = e.getY();
        double logicalX = Math.floor(x / xSpacing);
        double logicalY = Math.floor(y / ySpacing);

        //@TODO: place the if statement logic in method.
        if (logicalX >= 0 && logicalX < Graph.WIDTH &&
                logicalY >= 0 && logicalY <= Graph.HEIGHT) {

            return CoreEngine.Instance().getGraph().nodeWith(

                    new GraphNode(
                            (int) logicalX,
                            (int) logicalY
                    )
            );
        }

        return null;
    }

    /**
     * Method for creating blockade in random GraphNode
     *
     * @param blockadeInstance instance of the blockade to be created
     * @return the blockade created
     */
    public static Blockade randomBlockade(Blockade blockadeInstance) {

        GraphNode node = CoreEngine.Instance().getGraph().nodeWith(blockadeInstance.getPosition());

        return create(blockadeInstance, node);
    }

    /**
     * Calculate the id that should be assigned to the next blockade
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

    /**
     * Creates a new blockade with given properties
     *
     * @param blockadeInstance blockade properties to use
     * @param node             GraphNode to create the blockade at
     * @return the blockade created
     */
    private static Blockade create(Blockade blockadeInstance, GraphNode node) {

        if (node != null && !node.equals(new GraphNode(0, 0))) {

            Blockade blockade = new Blockade(
                    calcId(),
                    blockadeInstance.getName(),
                    node,
                    blockadeInstance.getSprite()
            );

            if (blockade.getPosition().getBlockade() == null &&
                    blockade.getPosition().getBase() == null &&
                    blockade.getPosition().getUnits().size() == 0) {

                blockade.getPosition().setBlockade(blockade);

                return blockade;
            }
        }

        return null;
    }

    //@TODO: document this method

    /**
     * @param e
     * @param blockadeInstance
     * @param renderer
     * @param graph
     * @return
     */
    public static Blockade mapBlockade(MouseEvent e, Blockade blockadeInstance, Renderer renderer, Graph graph) {

        GraphNode node = calcMapGraphNode(e, renderer, graph);

        return create(blockadeInstance, node);
    }

    //@TODO: document this method

    /**
     * @param e
     * @param renderer
     * @param graph
     * @return
     */
    private static GraphNode calcMapGraphNode(MouseEvent e, Renderer renderer, Graph graph) {

        double xSpacing = renderer.getXSpacing();
        double ySpacing = renderer.getYSpacing();
        double x = e.getX();
        double y = e.getY();
        double logicalX = Math.floor(x / xSpacing);
        double logicalY = Math.floor(y / ySpacing);

        if (logicalX >= 0 && logicalX < Graph.WIDTH &&
                logicalY >= 0 && logicalY <= Graph.HEIGHT) {

            return graph.nodeWith(

                    new GraphNode(
                            (int) logicalX,
                            (int) logicalY
                    )
            );
        }

        return null;
    }
}
