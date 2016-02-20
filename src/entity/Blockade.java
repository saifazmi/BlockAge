package entity;

import core.CoreEngine;
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

    public Blockade(int id, String name, GraphNode position, SpriteImage sprite) {
        super(id, name, position, sprite);
        setBreakable(false);
    }

    public Blockade(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        super(id, name, description, position, sprite);
        setBreakable(false);
    }


    @Override
    public void update() {

    }

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public static Blockade createBlockade(MouseEvent e, Blockade blockadeInstance) {
        GraphNode node = calcGraphNode(e);
        if(node != null)
        {
            Blockade blockade = new Blockade(calcId(), blockadeInstance.getName(), calcGraphNode(e), blockadeInstance.getSprite());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

    private static ArrayList<Blockade> getBlockades() {
        ArrayList<Blockade> blockades = new ArrayList<>();
        ArrayList<Entity> entities = CoreEngine.Instance().getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Blockade) {
                blockades.add((Blockade) entity);
            }
        }
        return blockades;
    }

    protected static GraphNode calcGraphNode(MouseEvent e) {
        Renderer renderer = Renderer.Instance();

        double xSpacing = renderer.getXSpacing();
        double ySpacing = renderer.getYSpacing();
        double x = e.getX();
        double y = e.getY() - 34;                //@TODO subtract pane height of pauls menu
        double logicalX = Math.floor(x / xSpacing);
        double logicalY = Math.floor(y / ySpacing);
        if (logicalX >= 0 && logicalX < Graph.WIDTH && logicalY >= 0 && logicalY <= Graph.HEIGHT) {
            GraphNode position = CoreEngine.Instance().getGraph().nodeWith(new GraphNode((int) logicalX, (int) logicalY));
            System.out.println(position.toString());
            return position;
        }
        return null;
    }

    protected static int calcId() {
        ArrayList<Blockade> blockades = getBlockades();
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
