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

    public Blockade(int id, String name, GraphNode position, SpriteImage sprite) {
        super(id, name, position, sprite);
        setBreakable(false);
    }

    public Blockade(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        super(id, name, description, position, sprite);
        setBreakable(false);
    }


    @Override
    public void update() { }

    public boolean isBreakable() { return breakable; }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public static Blockade createBlockade(MouseEvent e, GameRunTime runTime, Blockade blockadeInstance) {
        GraphNode node = calcGraphNode(e, runTime);
        if(node != null)
        {
            Blockade blockade = new Blockade(calcId(runTime), blockadeInstance.getName(), calcGraphNode(e, runTime), blockadeInstance.getSprite());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getBase() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

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

    public static Blockade randomBlockage(GameRunTime runTime, Blockade blockadeInstance) {
        GraphNode node = runTime.getEngine().getGraph().nodeWith(blockadeInstance.getPosition());
        if(node != null && !node.equals(new GraphNode(0,0)))
        {
            Blockade blockade = new Blockade(calcId(runTime), blockadeInstance.getName(), node, blockadeInstance.getSprite());
            if (blockade.getPosition().getBlockade() == null && blockade.getPosition().getBase() == null && blockade.getPosition().getUnits().size() == 0) {
                blockade.getPosition().setBlockade(blockade);
                return blockade;
            }
        }
        return null;
    }

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