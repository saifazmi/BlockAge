package lambdastorage;

import core.GameInterface;
import core.Renderer;
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
 * @author : First created by Dominic Walters with code by Dominic Walters
 * @date : 16/02/16, last edited by Dominic Walters on 25/02/16
 */
public class LambdaStore {
    private Renderer renderer = Renderer.Instance();
    private static final Logger LOG = Logger.getLogger(LambdaStore.class.getName());

    private final EventHandler<MouseEvent> sceneClickPlaceUnbreakableBlockade = e -> {
        //LOG.log(Level.INFO, "Click registered at:  (x, " + e.getX() + "), (y, " + e.getY() + ")");
        Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(0, 0), null);
        Image image = ((ImageView) GameInterface.unsortableButton.getGraphic()).getImage();
        SpriteImage spriteImage = new SpriteImage(image, blockadeInstance);
        spriteImage.setFitWidth(renderer.getXSpacing());
        spriteImage.setFitHeight(renderer.getYSpacing());
        spriteImage.setPreserveRatio(false);
        spriteImage.setSmooth(true);
        blockadeInstance.setSprite(spriteImage);
        Blockade blockade = Blockade.createBlockade(e, blockadeInstance);
        if (blockade != null) {
            //LOG.log(Level.INFO, "Blockade created at: (x, " + blockade.getPosition().getX() + "), (y, " + blockade.getPosition().getY() + ")");
            renderer.drawInitialEntity(blockade);
        } else {
            //LOG.log(Level.INFO, "Blockade creation failed. Request rejected, node has contents.");
        }
    };


    public EventHandler<MouseEvent> getSceneClickPlaceUnbreakableBlockade() {
        //System.out.println("Blockade Event Handler Created");
        return sceneClickPlaceUnbreakableBlockade;
    }
}
