package stores;

import core.GameRunTime;
import entity.Blockade;
import entity.SortableBlockade;
import graph.GraphNode;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.util.logging.Logger;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters
 * @date : 16/02/16, last edited by Dominic Walters on 25/02/16
 */
public final class LambdaStore {

    private static final Logger LOG = Logger.getLogger(LambdaStore.class.getName());

    private Renderer renderer = Renderer.Instance();
    private Scene scene = GameRunTime.Instance().getScene();
    private static LambdaStore instance = null;

    private LambdaStore() {
        // To prevent instantiation.
    }

    public static LambdaStore Instance() {
        if (instance == null) {
            instance = new LambdaStore();
        }
        return instance;
    }

    private final EventHandler<MouseEvent> sceneClickPlaceUnbreakableBlockade = e -> {
        Blockade blockadeInstance = new Blockade(
                1,
                "Blockade",
                new GraphNode(0, 0),
                null
        );
        ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
        Blockade blockade = Blockade.createBlockade(e, blockadeInstance);
        if (blockade != null) {
            renderer.drawInitialEntity(blockade);
        }
    };

    private final EventHandler<MouseEvent> sceneClickPlaceBreakableBlockade = e -> {
        SortableBlockade sortableBlockadeInstance = new SortableBlockade(
                0,
                "Sortable Blockade",
                Blockade.calcGraphNode(e),
                null,
                null
        );
        ImageStore.setSpriteProperties(sortableBlockadeInstance, ImageStore.sortableImage1);
        SortableBlockade blockade = SortableBlockade.create(sortableBlockadeInstance);
        if (blockade != null) {
            renderer.drawInitialEntity(blockade);
        }
    };

    public EventHandler<MouseEvent> getPlaceUnbreakableBlockade() {
        return sceneClickPlaceUnbreakableBlockade;
    }

    public EventHandler<MouseEvent> getPlaceBreakableBlockade() {
        return sceneClickPlaceBreakableBlockade;
    }

    public void setBlockadeClickEvent(boolean sortable) {
        if (scene.getOnMouseClicked() != null && (scene.getOnMouseClicked().equals(getPlaceUnbreakableBlockade()) || scene.getOnMouseClicked().equals(getPlaceBreakableBlockade()))) {
            scene.setOnMouseClicked(null);
        } else if (!sortable) {
            scene.setOnMouseClicked(getPlaceUnbreakableBlockade());
        } else {
            scene.setOnMouseClicked(getPlaceBreakableBlockade());
        }
    }
}
