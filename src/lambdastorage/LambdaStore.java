package lambdastorage;

import core.GameRunTime;
import entity.Blockade;
import graph.GraphNode;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import sceneElements.ImageStore;

import java.util.logging.Logger;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters
 * @date : 16/02/16, last edited by Dominic Walters on 25/02/16
 */
public class LambdaStore {
    private Renderer renderer = Renderer.Instance();
    private Scene scene = GameRunTime.Instance().getScene();
    private static final Logger LOG = Logger.getLogger(LambdaStore.class.getName());
    private static LambdaStore instance = null;

    public static LambdaStore Instance() {
        if (instance == null) {
            instance = new LambdaStore();
        }
        return instance;
    }

    private final EventHandler<MouseEvent> sceneClickPlaceUnbreakableBlockade = e -> {
        Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(0, 0), null);
        ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
        Blockade blockade = Blockade.createBlockade(e, blockadeInstance);
        if (blockade != null) {
            renderer.drawInitialEntity(blockade);
        }
    };

    public EventHandler<MouseEvent> getPlaceUnbreakableBlockade() {
        return sceneClickPlaceUnbreakableBlockade;
    }

    public void setBlockadeClickEvent() {
        if (scene.getOnMouseClicked() != null && scene.getOnMouseClicked().equals(getPlaceUnbreakableBlockade())) {
            scene.setOnMouseClicked(null);
        } else {
            scene.setOnMouseClicked(getPlaceUnbreakableBlockade());
        }
    }
}
