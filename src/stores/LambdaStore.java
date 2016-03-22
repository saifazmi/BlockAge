package stores;

import core.CoreEngine;
import core.GameRunTime;
import entity.Blockade;
import entity.Entity;
import entity.SortableBlockade;
import entity.Unit;
import graph.GraphNode;
import gui.GameInterface;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;
import sorts.visual.SortVisual;

import java.util.ArrayList;
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

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the only engine to be created
     */
    public static LambdaStore Instance() {
        if (instance == null) {
            instance = new LambdaStore();
        }
        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {
        instance = null;
    }

    // Places a unsortable blockade on the grid where the mouse is clicked
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

    // Places a sortable blockade on the grid where the mouse is clicked
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
                blockade.getSprite().setOnMouseClicked(getShowSort());
                CoreEngine.Instance().getEntities().add(blockade);
                CoreEngine.Instance().breakableBlockadesPlaced();
            }
        }
    };

    public EventHandler<MouseEvent> getShowSort() {
        return showSort;
    }

    private final EventHandler<MouseEvent> showSort = e -> {

        if (SortVisual.rendered != null) {
            SortVisual.rendered.display(false);
        }
        SpriteImage sprite = (SpriteImage) e.getSource();
        SortableBlockade blockade = (SortableBlockade) sprite.getEntity();
        if (blockade.getSortVisual() != null) {
            blockade.getSortVisual().display(true);
        }
    };

    private final EventHandler<MouseEvent> unitClickEvent = e -> {

        SpriteImage sprite = (SpriteImage) e.getSource();
        Unit unit = (Unit) sprite.getEntity();
        sprite.requestFocus();

        for (int i = 0; i < 4; i++)
            GameInterface.unitTextPane.getChildren().get(i).setVisible(true);

        if (SortVisual.rendered != null) {
            SortVisual.rendered.display(false);
        }

        if (unit.getSorting() != null) {
            unit.getSorting().getSortVisual().display(true);
        }

        Unit.Search search = unit.getSearch();
        Unit.Sort sort = unit.getSort();

        GameRunTime.Instance().setLastClicked(sprite);
        ArrayList<Entity> units = CoreEngine.Instance().getEntities();

        for (Entity unit1 : units) {

            if (sprite.getEntity() == unit1) {

                // sets the image pressed for each unit accordingly to the search
                GameInterface.unitDescriptionLabel.setText("Unit Description");
                GameInterface.unitDescriptionLabel.setLayoutX(GameInterface.rightPaneWidth / 2 - 175 / 2);
                GameInterface.namePaneLabel.setText("Name: " + sprite.getEntity().getName());
                GameInterface.searchPaneLabel.setText("Search: " + search.name());
                GameInterface.sortPaneLabel.setText("Sort: " + sort.name());

                if (search == Unit.Search.BFS) {

                    sprite.setImage(ImageStore.imagePressedDemon);
                    ImageView demon = new ImageView(ImageStore.imageDemon);
                    demon.setFitHeight(80);
                    demon.setFitWidth(80);
                    GameInterface.unitImage.setGraphic(demon);

                } else if (search == Unit.Search.A_STAR) {

                    sprite.setImage(ImageStore.imagePressedDk);
                    ImageView dk = new ImageView(ImageStore.imageDk);
                    dk.setFitHeight(80);
                    dk.setFitWidth(80);
                    GameInterface.unitImage.setGraphic(dk);

                } else {

                    sprite.setImage(ImageStore.imagePressedBanshee);
                    ImageView banshee = new ImageView(ImageStore.imageBanshee);
                    banshee.setFitHeight(80);
                    banshee.setFitWidth(80);
                    GameInterface.unitImage.setGraphic(banshee);
                }

            } else {

                SpriteImage obtainedSprite = unit1.getSprite();
                ElementsHandler.pressedToNotPressed(obtainedSprite);
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

    public EventHandler<MouseEvent> getUnitClickEvent() {
        return unitClickEvent;
    }
}
