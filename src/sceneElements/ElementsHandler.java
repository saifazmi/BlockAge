package sceneElements;

import core.BaseSpawner;
import core.CoreEngine;
import core.GameInterface;
import core.GameRunTime;
import core.Renderer;
import core.UnitSpawner;
import entity.Entity;
import entity.Unit;
import graph.GraphNode;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lambdastorage.LambdaStore;
import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import menus.OptionsMenu;
import menus.PauseMenu;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Paul Popa with code by Dominic Walters, and Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 25/02/16
 */
public class ElementsHandler {

    private static final Logger LOG = Logger.getLogger(ElementsHandler.class.getName());
    private static CoreEngine engine = CoreEngine.Instance();
    public static ButtonProperties b = new ButtonProperties();
    private static Scene scene = GameRunTime.Instance().getScene();

    /**
     * Handles the events for the buttons
     *
     * @param event - the event needed to be handled
     */
    public static void handle(Event event) {
        // Elements from the Main Menu scene
        CoreEngine engine = CoreEngine.Instance();

        if (event.getSource() == MainMenu.newGameButton) {
            // Create grid for the game we'll play
            GameRunTime gameRunTime = new GameRunTime();
            engine = CoreEngine.Instance();
            engine.setPaused(false);
            Renderer.Instance().calculateSpacing();
            gameRunTime.startGame();
            MenuHandler.setMainGameScene();
            new GameInterface();
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
            });
            unitSpawnerThread.start();

        }
        if (event.getSource() == MainMenu.optionsButton) {
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        }
        if (event.getSource() == MainMenu.exitButton) {
            System.exit(0);
        }
        // End of elements from Main Menu scene

        // Elements from the Options Menu scene
        if (event.getSource() == OptionsMenu.yesButtonH) {
            b.setButtonProperties(OptionsMenu.noButtonH, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonH, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonH)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonH);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonH);
            }
        }
        if (event.getSource() == OptionsMenu.noButtonH) {
            b.setButtonProperties(OptionsMenu.yesButtonH, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonH, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonH)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonH);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonH);
            }
        }

        if (event.getSource() == OptionsMenu.yesButtonS) {
            b.setButtonProperties(OptionsMenu.noButtonS, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonS, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonS)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonS);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonS);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonS) {
            b.setButtonProperties(OptionsMenu.yesButtonS, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonS, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonS)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonS);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonS);
            }
        }

        if (event.getSource() == OptionsMenu.backButton) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        // End of elements from Options Menu scene

        // Elements from the Pause Menu scene
        if (event.getSource() == PauseMenu.backGameButton) {
            //engine.setPaused(false);
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        }
        if (event.getSource() == PauseMenu.backMainButton) {
            engine.setRunning(false);
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        if (event.getSource() == GameInterface.playButton) {
            engine.setPaused(false);
        }
        if (event.getSource() == GameInterface.pauseButton) {
            engine.setPaused(true);
        }

    }

    public static void handleKeys(KeyEvent event) {
        KeyCode k = event.getCode();
        if (MenuHandler.currentScene == MenuHandler.MAIN_GAME) {
            if (k == KeyCode.ESCAPE) {
                engine.setPaused(true);
                MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
            } else if (k == KeyCode.B) {
                LambdaStore.Instance().setBlockadeClickEvent();
            } else if (k == KeyCode.SPACE) {
                if (engine.isPaused()) {
                    engine.setPaused(false);
                } else {
                    engine.setPaused(true);
                }
            } else if (k == KeyCode.R) {
                Node node = scene.getFocusOwner();
                if (node instanceof SpriteImage) {
                    ((Unit) ((SpriteImage) node).getEntity()).showTransition();
                }
            } else if (k == KeyCode.S) {
                ArrayList<Entity> units = engine.getEntities();
                for (Entity unit : units) {
                    pressedToNotPressed(unit.getSprite(), false);
                }
                GameInterface.unitDescriptionText.clear();
            }
        }
    }

    public static void pressedToNotPressed(SpriteImage sprite, boolean showTransition)
    {
        Image image = sprite.getImage();
        boolean set = true;
        if (image.equals(Images.imagePressedDemon)) {
            sprite.setImage(Images.imageDemon);
        } else if (image.equals(Images.imagePressedDk)) {
            sprite.setImage(Images.imageDk);
        } else if (image.equals(Images.imagePressedBanshee)) {
            sprite.setImage(Images.imageBanshee);
        } else {
            set = false;
        }
        if(showTransition && set)
        {
            ((Unit)sprite.getEntity()).showTransition();
        }
    }
}
