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
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lambdastorage.LambdaStore;
import maps.MapEditorInterface;
import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import menus.Options;
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
    private static Scene scene = GameRunTime.Instance().getScene();
    private static ButtonProperties b = new ButtonProperties();

    public static Options options = new Options();

    /**
     * Handles the events for the buttons
     *
     * @param event - the event needed to be handled
     */
    public static void handle(Event event) {
        // Elements from the Main Menu scene
        CoreEngine engine = CoreEngine.Instance();

        if (event.getSource() == MainMenu.newGameButtonF) {
            // Create grid for the game we'll play
            System.out.println("Start game");
            GameRunTime gameRunTime = new GameRunTime();
            engine = CoreEngine.Instance();
            engine.setPaused(false);
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
            });
            unitSpawnerThread.start();

        }
        if (event.getSource() == MainMenu.optionsButtonF) {
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        }
        if (event.getSource() == MainMenu.exitButtonF) {
            System.exit(0);
        }
        // End of elements from Main Menu scene

        // Elements from the Options Menu scene
        if (event.getSource() == OptionsMenu.yesButtonSearch) {
            options.setPath(false);
            b.setButtonProperties(OptionsMenu.noButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    e -> handle(e), new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSearch, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSearch);
            }
        }
        if (event.getSource() == OptionsMenu.noButtonSearch) {
            options.setPath(true);
            b.setButtonProperties(OptionsMenu.yesButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    e -> handle(e), new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSearch, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSearch);
            }
        }

        if (event.getSource() == OptionsMenu.yesButtonSound) {
            b.setButtonProperties(OptionsMenu.noButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSound, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSound);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonSound) {
            b.setButtonProperties(OptionsMenu.yesButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSound, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSound);
            }
        }


        if (event.getSource() == OptionsMenu.yesButtonB) {
            options.setInitialBlockades(false);
            b.setButtonProperties(OptionsMenu.noButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonB, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonB);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonB) {
            options.setInitialBlockades(true);
            b.setButtonProperties(OptionsMenu.yesButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonB, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonB);
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


        if (event.getSource() == MainMenu.mapEditorButton)
        {
            MenuHandler.switchScene(MenuHandler.MAP_EDITOR);
        }
        if (event.getSource() == MapEditorInterface.saveButton)
        {

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
                LambdaStore.Instance().setBlockadeClickEvent();
            } else if (k == KeyCode.SPACE) {
                if (engine.isPaused()) {
                    engine.setPaused(false);
                } else {
                    engine.setPaused(true);
                }
            } else if (k == KeyCode.R) {
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
        if (image.equals(Images.imagePressedDemon)) {
            sprite.setImage(Images.imageDemon);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        } else if (image.equals(Images.imagePressedDk)) {
            sprite.setImage(Images.imageDk);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        } else if (image.equals(Images.imagePressedBanshee)) {
            sprite.setImage(Images.imageBanshee);
            ((Unit) sprite.getEntity()).showTransition(false, false);
        }
    }
}

