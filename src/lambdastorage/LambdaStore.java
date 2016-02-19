package lambdastorage;

import core.GameInterface;
import core.GameRunTime;
import entity.Blockade;
import graph.GraphNode;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sceneElements.SpriteImage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by walte on 16/02/2016.
 */
public class LambdaStore {
    private static final Logger LOG = Logger.getLogger(LambdaStore.class.getName());
    private static final EventHandler<MouseEvent> sceneClickPlaceUnbreakableBlockade = e -> {
        LOG.log(Level.INFO, "Click registered at:  (x, " + e.getX() + "), (y, " + e.getY() + ")");
        Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(0, 0), null);
        Image image = ((ImageView)GameInterface.unsortableButton.getGraphic()).getImage();
        SpriteImage spriteImage = new SpriteImage(image, blockadeInstance);
        spriteImage.setFitWidth(GameRunTime.Instance().getRenderer().getXSpacing());
        spriteImage.setFitHeight(GameRunTime.Instance().getRenderer().getYSpacing());
        spriteImage.setPreserveRatio(false);
        spriteImage.setSmooth(true);
        blockadeInstance.setSprite(spriteImage);
        Blockade blockade = Blockade.createBlockade(e, GameRunTime.Instance(), blockadeInstance);
        if (blockade != null) {
            LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
            GameRunTime.Instance().getRenderer().drawInitialEntity(blockade);
        } else {
            LOG.log(Level.INFO, "Blockade creation failed. Request rejected, node has contents.");
        }
    };

    public LambdaStore(){
           }

    public EventHandler<MouseEvent> getSceneClickPlaceUnbreakableBlockade() {
        System.out.println("Blockade Event Handler Created");
        return sceneClickPlaceUnbreakableBlockade;
    }
}
