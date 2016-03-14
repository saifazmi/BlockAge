package sceneElements;

import core.BaseSpawner;
import core.CoreEngine;
import core.GameRunTime;
import core.UnitSpawner;
import entity.Entity;
import entity.Unit;
import graph.GraphNode;
import gui.GameInterface;
import gui.Renderer;
import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import maps.MapChooserInterface;
import maps.MapEditorInterface;
import menus.EndGameMenu;
import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import menus.Options;
import menus.OptionsMenu;
import menus.PauseMenu;
import stores.ImageStore;
import stores.LambdaStore;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Paul Popa with code by Dominic Walters, and Paul Popa
 * @date : 09/02/16, last edited by Anh Pham on 14/03/16
 */
public class ElementsHandler {

    private static final Logger LOG = Logger.getLogger(ElementsHandler.class.getName());
    private static ButtonProperties b = new ButtonProperties();
    private static CoreEngine engine = CoreEngine.Instance();

    public static Options options = new Options();

    /**
     * Handles the events for the buttons
     *
     * @param event - the event needed to be handled
     */
    public static void handle(Event event) {
        // Elements from the Main Menu scene
        if (event.getSource() == MainMenu.newGameButton) {
            startGame();
        } else if (event.getSource() == MainMenu.optionsButton) {
            OptionsMenu.blockadeLabel.setVisible(true);
            OptionsMenu.noButtonB.setVisible(true);
            OptionsMenu.yesButtonB.setVisible(true);
            OptionsMenu.blockadeLabel.setDisable(false);
            OptionsMenu.noButtonB.setDisable(false);
            OptionsMenu.yesButtonB.setDisable(false);
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        } else if (event.getSource() == MainMenu.exitButton) {
            System.exit(0);
        }
        // End of elements from Main Menu scene

        // Elements from the Options Menu scene
        if (event.getSource() == OptionsMenu.yesButtonSearch) {
            options.setPath(false);
            ArrayList<Entity> units = engine.getEntities();
            for (int i = 0; i < units.size(); i++) {
                SpriteImage obtainedSprite = engine.getEntities().get(i).getSprite();
                pressedToNotPressed(obtainedSprite);
            }
            b.setButtonProperties(OptionsMenu.noButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSearch, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSearch);
            }
        }
        if (event.getSource() == OptionsMenu.noButtonSearch) {
            options.setPath(true);
            b.setButtonProperties(OptionsMenu.yesButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSearch, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSearch);
            }
        }

        if (event.getSource() == OptionsMenu.yesButtonSound) {
            options.setSound(false);
            b.setButtonProperties(OptionsMenu.noButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSound, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSound);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonSound) {
            options.setSound(true);
            b.setButtonProperties(OptionsMenu.yesButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSound, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSound);
            }
        }

        if (event.getSource() == OptionsMenu.yesButtonB) {
            options.setInitialBlockades(false);
            b.setButtonProperties(OptionsMenu.noButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonB, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonB);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonB) {
            options.setInitialBlockades(true);
            b.setButtonProperties(OptionsMenu.yesButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonB, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonB);
            }
        }

        if (event.getSource() == OptionsMenu.backButton && MenuHandler.lastScene == MenuHandler.MAIN_MENU) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        if (event.getSource() == OptionsMenu.backButton && MenuHandler.lastScene == MenuHandler.PAUSE_MENU) {
            MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
        }
        // End of elements from Options Menu scene

        // Elements from the Pause Menu scene
        if (event.getSource() == PauseMenu.backGameButton) {
            CoreEngine.Instance().setPaused(true);
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        }
        if (event.getSource() == PauseMenu.optionsButton) {
            OptionsMenu.blockadeLabel.setVisible(false);
            OptionsMenu.noButtonB.setVisible(false);
            OptionsMenu.yesButtonB.setVisible(false);
            OptionsMenu.blockadeLabel.setDisable(true);
            OptionsMenu.noButtonB.setDisable(true);
            OptionsMenu.yesButtonB.setDisable(true);
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        }
        if (event.getSource() == PauseMenu.backMainButton) {
            engine.setRunning(false);
            MapChooserInterface.Instance().resetChosenMap();
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }

        // Elements in the End Game Menu Scene
        if (event.getSource() == EndGameMenu.backMainButton) {
            engine.setRunning(false);
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        if (event.getSource() == GameInterface.playButton) {
            engine.setPaused(false);
        }

        if (event.getSource() == GameInterface.pauseButton) {
            engine.setPaused(true);
        }
        if (event.getSource() == MainMenu.mapEditorButton) {
            MenuHandler.switchScene(MenuHandler.MAP_EDITOR);
        }
        if (event.getSource() == MainMenu.customGameButton) {
            MapChooserInterface.Instance().showChooser();
        }
        // Adding events for the map editor interface
        if (event.getSource() == MapEditorInterface.backButton) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
    }

    public static void handleKeys(KeyEvent event) {
        CoreEngine engine = CoreEngine.Instance();
        KeyCode k = event.getCode();
        if (MenuHandler.currentScene == MenuHandler.MAIN_GAME) {
            if (k == KeyCode.ESCAPE) {
                engine.setPaused(true);
                MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
            } else if (k == KeyCode.B) {
                LambdaStore.Instance().setBlockadeClickEvent(event.isShiftDown());
            } else if (k == KeyCode.SPACE) {
                if (engine.isPaused()) {
                    engine.setPaused(false);
                } else {
                    engine.setPaused(true);
                }
            } else if (k == KeyCode.R && options.getShowPath()) {
                ((Unit) GameRunTime.Instance().getLastClicked().getEntity()).showTransition(!event.isShiftDown(), false);
                ((Unit) GameRunTime.Instance().getLastClicked().getEntity()).showTransition(!event.isShiftDown(), true);
            } else if (k == KeyCode.S) {
                ArrayList<Entity> units = engine.getEntities();
                for (int i = 0; i < units.size(); i++) {
                    SpriteImage obtainedSprite = engine.getEntities().get(i).getSprite();
                    pressedToNotPressed(obtainedSprite);
                }
                GameInterface.unitDescriptionText.clear();
            }
        }
    }

    public static void pressedToNotPressed(SpriteImage sprite) {
        Image image = sprite.getImage();
        if (image.equals(ImageStore.imagePressedDemon)) {
            sprite.setImage(ImageStore.imageDemon);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        } else if (image.equals(ImageStore.imagePressedDk)) {
            sprite.setImage(ImageStore.imageDk);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        } else if (image.equals(ImageStore.imagePressedBanshee)) {
            sprite.setImage(ImageStore.imageBanshee);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        }
    }

    /*private static void startGame() {
        // Create grid for the game we'll play
        System.out.println("Start game");
        GameRunTime gameRunTime = new GameRunTime();
        engine = CoreEngine.Instance();
        engine.setPaused(true);
        Renderer.Instance().calculateSpacing();
        System.out.println("Spacing calculated");
        gameRunTime.startGame();
        System.out.println("Game started");
        MenuHandler.setMainGameScene();
        System.out.println("Scene set");
        new GameInterface();
        System.out.println("Interface made");
        MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        Renderer.Instance().initialDraw();
        Thread unitSpawnerThread = new Thread(() -> {
            GraphNode goal = BaseSpawner.Instance().getGoal();
            while (goal == null) {
                try {
                    goal = BaseSpawner.Instance().getGoal();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.toString(), e);
                }
            }

            LOG.log(Level.INFO, "GOAL found!!!");
            UnitSpawner spawner = new UnitSpawner(2, goal);
            CoreEngine.Instance().setSpawner(spawner);
            engine.setPaused(false);
        });
        unitSpawnerThread.start();
    }*/

    public static void startGame() {
        // Create grid for the game we'll play
        System.out.println("Start game");
        GameRunTime gameRunTime = new GameRunTime();
        engine = CoreEngine.Instance();
        engine.setPaused(true);
        Renderer.Instance().calculateSpacing();
        System.out.println("Spacing calculated");
        gameRunTime.startGame();
        System.out.println("Game started");
        MenuHandler.setMainGameScene();
        System.out.println("Scene set");
        new GameInterface();
        System.out.println("Interface made");
        MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        Renderer.Instance().initialDraw();
        Thread unitSpawnerThread = new Thread(() -> {
            GraphNode goal = BaseSpawner.Instance().getGoal();
            while (goal == null) {
                try {
                    goal = BaseSpawner.Instance().getGoal();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    LOG.log(Level.SEVERE, e.toString(), e);
                }
            }

            LOG.log(Level.INFO, "GOAL found!!!");
            UnitSpawner spawner = new UnitSpawner(2, goal);
            CoreEngine.Instance().setSpawner(spawner);
            engine.setPaused(false);
        });
        unitSpawnerThread.start();
    }
}

