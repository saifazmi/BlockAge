package sceneElements;

import core.CoreEngine;
import core.GameInterface;
import core.GameRunTime;
import entity.Entity;
import entity.Unit;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lambdastorage.LambdaStore;
import menus.*;

import java.util.ArrayList;

public class ElementsHandler {

    public static ButtonProperties b = new ButtonProperties();

    /**
     * Handles the events for the buttons
     *
     * @param event - the event needed to be handled
     */
    public static void handle(Event event) {
        // Elements from the Main Menu scene

        if (event.getSource() == MainMenu.newGameButton) {
            // Create grid for the game we'll play
            GameRunTime gameRunTime = new GameRunTime();
            gameRunTime.getEngine().paused = false;
            gameRunTime.getRenderer().calculateSpacing();
            gameRunTime.startGame();
            MenuHandler.setMainGameScene();
            GameInterface gameInterface = new GameInterface();
            //Test.test(gameRunTime);
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
            gameRunTime.getRenderer().initialDraw();
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
                    e -> handle(e), new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonH, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonH)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonH);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonH);
            }
        }
        if (event.getSource() == OptionsMenu.noButtonH) {
            b.setButtonProperties(OptionsMenu.yesButtonH, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    e -> handle(e), new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonH, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonH)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonH);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonH);
            }
        }

        if (event.getSource() == OptionsMenu.yesButtonS) {
            b.setButtonProperties(OptionsMenu.noButtonS, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonS, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonS)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonS);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonS);
            }
        }

        if (event.getSource() == OptionsMenu.noButtonS) {
            b.setButtonProperties(OptionsMenu.yesButtonS, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    e -> handle(e), new ImageView(OptionsMenu.onImage));
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
            CoreEngine.paused = false;
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        }
        if (event.getSource() == PauseMenu.backMainButton) {
            CoreEngine.running = false;
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        if(event.getSource() == GameInterface.playButton)
        {
            CoreEngine.paused = false;
        }
        if(event.getSource() == GameInterface.pauseButton)
        {
            CoreEngine.paused = true;
        }

    }

    public static void handleKeys(KeyEvent event) {
        KeyCode k = event.getCode();
        if(MenuHandler.currentScene == MenuHandler.MAIN_GAME)
        {
            if (k == KeyCode.ESCAPE) {
                CoreEngine.paused = true;
                MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
            }
            else if (k == KeyCode.B)
            {
                LambdaStore store = new LambdaStore();
                if(GameRunTime.getScene().getOnMouseClicked() != null && GameRunTime.getScene().getOnMouseClicked().equals(store.getSceneClickPlaceUnbreakableBlockade()))
                {
                    GameRunTime.getScene().setOnMouseClicked(null);
                }
                else
                {
                    GameRunTime.getScene().setOnMouseClicked(store.getSceneClickPlaceUnbreakableBlockade());
                }
            }
            else if (k == KeyCode.P)
            {
                if(CoreEngine.paused == true)
                {
                    CoreEngine.paused = false;
                }
                else
                {
                    CoreEngine.paused = true;
                }
            }
            else if (k == KeyCode.R)
            {
                Node node = GameRunTime.getScene().getFocusOwner();
                if(node instanceof SpriteImage)
                {
                    ((Unit) ((SpriteImage) node).getEntity()).showTransition();
                }
            }
        }
    }
}
