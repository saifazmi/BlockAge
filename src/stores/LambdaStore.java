package stores;

import core.CoreEngine;
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

    public static boolean delete() {
        instance = null;
        return true;
    }

    private final EventHandler<MouseEvent> sceneClickPlaceUnbreakableBlockade = e -> {

        if (CoreEngine.Instance().unbreakableBlockadesLeft()) {
            Blockade blockadeInstance = new Blockade(
                    1,
                    "Blockade",
                    new GraphNode(0, 0),
                    null
            );
            System.out.println("Blockade instance made");
            ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
            Blockade blockade = Blockade.createBlockade(e, blockadeInstance);
            if (blockade != null) {
                System.out.println("Blockade rendered");
                renderer.drawInitialEntity(blockade);
                CoreEngine.Instance().getEntities().add(blockade);
                CoreEngine.Instance().unbreakableBlockadesPlaced();
            }
        }
    };

    private final EventHandler<MouseEvent> sceneClickPlaceBreakableBlockade = e -> {

        if (CoreEngine.Instance().breakableBlockadesLeft()) {
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
                CoreEngine.Instance().getEntities().add(blockade);
                CoreEngine.Instance().breakableBlockadesPlaced();
            }
        }
    };

    public EventHandler<MouseEvent> getPlaceUnbreakableBlockade() {
        return sceneClickPlaceUnbreakableBlockade;
    }

    public EventHandler<MouseEvent> getPlaceBreakableBlockade() {
        return sceneClickPlaceBreakableBlockade;
    }

    public void setBlockadeClickEvent(boolean sortable) {
        if (scene.getOnMouseClicked() == null) {
            if (!sortable) {
                System.out.println("Unsortable on");
                scene.setOnMouseClicked(getPlaceUnbreakableBlockade());
            } else {
                System.out.println("Sortable on");
                scene.setOnMouseClicked(getPlaceBreakableBlockade());
            }
        } else if (scene.getOnMouseClicked().equals(getPlaceUnbreakableBlockade())) {
            if (!sortable) {
                System.out.println("Unsortable off");
                scene.setOnMouseClicked(null);
            } else {
                System.out.println("Sortable on");
                scene.setOnMouseClicked(getPlaceBreakableBlockade());
            }
        } else if (scene.getOnMouseClicked().equals(getPlaceBreakableBlockade())) {
            if (!sortable) {
                System.out.println("Unsortable on");
                scene.setOnMouseClicked(getPlaceUnbreakableBlockade());
            } else {
                System.out.println("Sortable off");
                scene.setOnMouseClicked(null);
            }
        }
    }
}
