package lambdastorage;
import core.GameRunTime;
import entity.Blockade;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by walte on 16/02/2016.
 */
public class LambdaStore {
    private static final Logger LOG = Logger.getLogger(LambdaStore.class.getName());
    private GameRunTime runTime;
    private final EventHandler<MouseEvent> sceneClickPlaceBlockade = e -> {
        LOG.log(Level.INFO, "Click registered at:  (x, " + e.getX() + "), (y, " + e.getY() + ")");
        Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(0,0), null);
        SpriteImage spriteImage = new SpriteImage("http://imgur.com/dZZdmUr.png", blockadeInstance);
        blockadeInstance.setSprite(spriteImage);
        Blockade blockade = Blockade.createBlockade(e, runTime, blockadeInstance);
        if (blockade != null)
        {
            LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
            runTime.getRenderer().drawInitialEntity(blockade);
        }
        else
        {
            LOG.log(Level.INFO, "Blockade creation failed. Request rejected, node has contents.");
        }
    };

    public LambdaStore(GameRunTime runTime)
    {
        this.runTime = runTime;
    }

    public EventHandler<MouseEvent> getSceneClickPlaceBlockade() {
        return sceneClickPlaceBlockade;
    }
}
