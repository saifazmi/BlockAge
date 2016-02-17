package sceneElements;

import core.CoreEngine;
import core.GameInterface;
import core.GameRunTime;
import javafx.event.Event;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import menus.OptionsMenu;
import menus.PauseMenu;
import test.Test;

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
            MenuHandler.setMainGameScene();
            GameInterface gameInterface = new GameInterface();
            Test.test(gameRunTime);
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
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
            CoreEngine.running = true;
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        }
        if (event.getSource() == PauseMenu.backMainButton) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
    }

    public static void handleKeys(KeyEvent event) {
        KeyCode k = event.getCode();
        if (k == KeyCode.ESCAPE && MenuHandler.currentScene == MenuHandler.MAIN_GAME) {
            CoreEngine.running = false;
            MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
        }
    }
}
