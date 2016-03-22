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
import maps.MapEditor;
import maps.MapEditorInterface;
import menus.EndGameMenu;
import menus.MainMenu;
import menus.Menu;
import menus.MenuHandler;
import menus.Options;
import menus.OptionsMenu;
import menus.PauseMenu;
import sorts.visual.SortVisual;
import sound.SoundManager;
import stores.ImageStore;
import stores.LambdaStore;
import tutorial.Tutorial;

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
    //private static CoreEngine engine = CoreEngine.Instance();

    public static Options options = new Options();
    public static Tutorial tutorial = new Tutorial();

    /**
     * Handles the events for the buttons
     *
     * @param event - the event needed to be handled
     */
    public static void handle(Event event) {
        // Elements from the Main Menu scene
    	
    	// Start game if the new game button is pressed
        if (event.getSource() == MainMenu.newGameButton) {
            startGame();
        } 
        // Switches to the Options Menu if the options button is pressed
        else if (event.getSource() == MainMenu.optionsButton) {
        	// Sets which options will be displayed
        	// In this case all the options will be displayed
            OptionsMenu.blockadeLabel.setVisible(true);
            OptionsMenu.noButtonB.setVisible(true);
            OptionsMenu.yesButtonB.setVisible(true);
            OptionsMenu.blockadeLabel.setDisable(false);
            OptionsMenu.noButtonB.setDisable(false);
            OptionsMenu.yesButtonB.setDisable(false);
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        }
        // Switches to the Map Editor Menu if the map editor button is pressed
        else if (event.getSource() == MainMenu.mapEditorButton) {
            //MapEditor.Instance();
            MenuHandler.switchScene(MenuHandler.MAP_EDITOR);
        }
        // Opens a new window from where you can choose the map created
        else if (event.getSource() == MainMenu.customGameButton) {
            MapChooserInterface.Instance().showChooser();
        }
        // Exits the game if the exit button is pressed
        else if (event.getSource() == MainMenu.exitButton) {
            System.exit(0);
        }
        // End of elements from Main Menu scene
        
        CoreEngine engine = CoreEngine.Instance(); // gets the current running engine
        
        // Elements from the Options Menu scene
        // If the off Search button is pressed, the search for the units will not be displayed
        if (event.getSource() == OptionsMenu.yesButtonSearch) {
            options.setPath(false);
            ArrayList<Entity> units = engine.getEntities();
            for (int i = 0; i < units.size(); i++) {
                SpriteImage obtainedSprite = engine.getEntities().get(i).getSprite();
                pressedToNotPressed(obtainedSprite);
            }
            // Changes the button from on to off
            b.setButtonProperties(OptionsMenu.noButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSearch, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSearch);
            }
        }
        // If the on Search button is pressed, the search for the units will be displayed
        if (event.getSource() == OptionsMenu.noButtonSearch) {
            options.setPath(true);
            // Changes the button from off to on
            b.setButtonProperties(OptionsMenu.yesButtonSearch, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSearch, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSearch)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSearch);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSearch);
            }
        }
        // If the on Sound button is pressed, the sound is turned off
        if (event.getSource() == OptionsMenu.yesButtonSound) {
            options.setSound(false);
            // Changes the button from on to off
            b.setButtonProperties(OptionsMenu.noButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonSound, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonSound);
            }
        }
        // If the off Sound button is pressed, the sound is turned on
        if (event.getSource() == OptionsMenu.noButtonSound) {
            options.setSound(true);
            // Changes the button from off to on
            b.setButtonProperties(OptionsMenu.yesButtonSound, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonSound, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonSound)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonSound);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonSound);
            }
        }
        // If the on Tutorial button is pressed, the tutorial is turned off
        if (event.getSource() == OptionsMenu.yesButtonTutorial) {
            options.setTutorial(false);
            if (Tutorial.active == true) {
                Tutorial.reset(); // resets the tutorial
            }
            engine.setPaused(true); // pause the game state
            // Changes the button from on to off
            b.setButtonProperties(OptionsMenu.noButtonTutorial, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonTutorial, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonTutorial)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonTutorial);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonTutorial);
            }
        }
        // If the off Tutorial button is pressed, the tutorial is turned on
        if (event.getSource() == OptionsMenu.noButtonTutorial) {
            options.setTutorial(true);
            // Changes the button from off to on
            b.setButtonProperties(OptionsMenu.yesButtonTutorial, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonTutorial, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 2 * OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonTutorial)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonTutorial);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonTutorial);
            }
        }
        // If the on Initial blockades button is pressed, the map will spawn with no initial blockades
        if (event.getSource() == OptionsMenu.yesButtonB) {
            options.setInitialBlockades(false);
            // Changes the button from on to off
            b.setButtonProperties(OptionsMenu.noButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 3 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.offImage));
            b.addHoverEffect(OptionsMenu.noButtonB, OptionsMenu.offImageHovered, OptionsMenu.offImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 3 * OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.noButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.yesButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.noButtonB);
            }
        }
        // If the off Initial blockades button is pressed, the map will spawn with random initial blockades
        if (event.getSource() == OptionsMenu.noButtonB) {
            options.setInitialBlockades(true);
            // Changes the button from off to on
            b.setButtonProperties(OptionsMenu.yesButtonB, "", Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 3 * OptionsMenu.spaceBetweenImgH,
                    ElementsHandler::handle, new ImageView(OptionsMenu.onImage));
            b.addHoverEffect(OptionsMenu.yesButtonB, OptionsMenu.onImageHovered, OptionsMenu.onImage, Menu.WIDTH / 2 + OptionsMenu.onImage.getWidth() + OptionsMenu.spaceBetweenText, Menu.HEIGHT / 3 + 3 * OptionsMenu.spaceBetweenImgH);
            // Check if the pane already contains this button, if not, then add it
            if (!OptionsMenu.optionsMenuPane.getChildren().contains(OptionsMenu.yesButtonB)) {
                OptionsMenu.optionsMenuPane.getChildren().remove(OptionsMenu.noButtonB);
                OptionsMenu.optionsMenuPane.getChildren().add(OptionsMenu.yesButtonB);
            }
        }
        // If the back button is pressed and the last Scene was Main menu then it will switch to Main Menu
        if (event.getSource() == OptionsMenu.backButton && MenuHandler.lastScene == MenuHandler.MAIN_MENU) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
        // If the back button is pressed and the last Scene was Pause menu then it will switch to Pause Menu
        if (event.getSource() == OptionsMenu.backButton && MenuHandler.lastScene == MenuHandler.PAUSE_MENU) {
            MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
        }
        // End of elements from Options Menu scene

        // Elements from the Pause Menu scene
        // If the back to game button is pressed, then switch to main game
        if (event.getSource() == PauseMenu.backGameButton) {
            CoreEngine.Instance().setPaused(true); // set the state of the game to paused
            MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        }
        // Switches to the Options Menu if the options button is pressed
        if (event.getSource() == PauseMenu.optionsButton) {
        	// Displays different options
        	// In this case the initial blockades is not displayed as the map has already been generated
            OptionsMenu.blockadeLabel.setVisible(false);
            OptionsMenu.noButtonB.setVisible(false);
            OptionsMenu.yesButtonB.setVisible(false);
            OptionsMenu.blockadeLabel.setDisable(true);
            OptionsMenu.noButtonB.setDisable(true);
            OptionsMenu.yesButtonB.setDisable(true);
            MenuHandler.switchScene(MenuHandler.OPTIONS_MENU);
        }
        // Switches to the Main Menu if the back button is pressed
        if (event.getSource() == PauseMenu.backMainButton) {
            engine.setRunning(false);
            MapChooserInterface.Instance().resetChosenMap();
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
            quitGame();
            //@TODO added while new game is broken
            //System.exit(0);
        }
        // End of elements from Pause Menu scene

        // Elements in the End Game Menu Scene
        if (event.getSource() == EndGameMenu.backMainButton) {
            engine.setRunning(false);
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
            quitGame();
            //@TODO added while new game is broken
            //System.exit(0);
        }
        // End of elements in the End Game Menu Scene
        

        // Adding events for the map editor interface
        // If the back button is pressed then it will switch to the Main Menu
        if (event.getSource() == MapEditorInterface.backButton) {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
            quitGame();
        }
    }

    /**
     * Handles the events for the keys
     *
     * @param event - the event needed to be handled
     */
    public static void handleKeys(KeyEvent event) {
        CoreEngine engine = CoreEngine.Instance();
        KeyCode k = event.getCode(); // gets the key code of the event
        // if the current scene is the main game
        if (MenuHandler.currentScene == MenuHandler.MAIN_GAME) {
        	// if the escape key is pressed then switch scene to pause menu
            if (k == KeyCode.ESCAPE) {
                engine.setPaused(true);
                MenuHandler.switchScene(MenuHandler.PAUSE_MENU);
            } 
            // if the B key is pressed then enable placing unsortable blockades
            else if (k == KeyCode.B) {
                LambdaStore.Instance().setBlockadeClickEvent(event.isShiftDown());
            } 
            // if the space key is pressed and the tutorial is not active then toggle the pause state
            else if (k == KeyCode.SPACE && !Tutorial.active) {
                if (engine.isPaused()) {
                    engine.setPaused(false);
                } else {
                    engine.setPaused(true);
                }
            } 
            // if the R key is pressed then show the route from the selected unit
            else if (k == KeyCode.R && options.getShowPath()) {
                ((Unit) GameRunTime.Instance().getLastClicked().getEntity()).showTransition(!event.isShiftDown(), true);
            } 
            // if the S key is pressed then unselect the currently selected unit
            else if (k == KeyCode.S) {
            	// Change the Unit description pane to be the Key bindings one
            	GameInterface.unitDescriptionLabel.setText("Key Bindings");
            	GameInterface.unitDescriptionLabel.setLayoutX(GameInterface.rightPaneWidth/2 - 131.25/2);
            	GameInterface.namePaneLabel.setText("R-Show route");
            	GameInterface.searchPaneLabel.setText("S-Unselect unit");
            	GameInterface.sortPaneLabel.setText("B-Unsortable blockade");
                ArrayList<Entity> units = engine.getEntities();
                // Unselects all the units on the grid
                for (int i = 0; i < units.size(); i++) {
                    if (units.get(i) instanceof Unit) {
                        SpriteImage obtainedSprite = engine.getEntities().get(i).getSprite();
                        pressedToNotPressed(obtainedSprite);
                    }
                }
                if (SortVisual.rendered != null) {
                    SortVisual.rendered.display(false);
                }
                // Deletes the image from the unit description pane
                GameInterface.unitDescriptionText.clear();
                GameInterface.unitTextPane.getChildren().get(3).setVisible(false);
                Tutorial.routeShown = false;
                Tutorial.visualShown = false;
            } 
            // If the ENTER key is pressed then continue with the active tutorial
            else if (k == KeyCode.ENTER && Tutorial.active) {
                Tutorial.inc();
            }
        }
    }

    /**
     * Unselects all the the units
     * 
     * @param sprite the sprite of the unit that will be unselected
     */
    public static void pressedToNotPressed(SpriteImage sprite) {
        Image image = sprite.getImage();
        if (image.equals(ImageStore.imagePressedDemon)) {
            sprite.setImage(ImageStore.imageDemon); // sets back to demon image
            ((Unit) sprite.getEntity()).showTransition(false, false);
            sprite.setOnMouseClicked(LambdaStore.Instance().getUnitClickEvent());
        } else if (image.equals(ImageStore.imagePressedDk)) {
            sprite.setImage(ImageStore.imageDk); // sets back to death knight image
            ((Unit) sprite.getEntity()).showTransition(false, false);
            sprite.setOnMouseClicked(LambdaStore.Instance().getUnitClickEvent());
        } else if (image.equals(ImageStore.imagePressedBanshee)) {
            sprite.setImage(ImageStore.imageBanshee); // sets back to banshee image
            ((Unit) sprite.getEntity()).showTransition(false, false);
            sprite.setOnMouseClicked(LambdaStore.Instance().getUnitClickEvent());
        }
    }
    
    // What is this? Can we delete it?
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

    /**
     * Starts the game that will be played
     */
    public static void startGame() {
        // Create grid for the game we'll play
        System.out.println("Start game");
        new GameRunTime();
        CoreEngine engine = CoreEngine.Instance();
        engine.setPaused(true);
        Renderer.Instance().calculateSpacing();
        BaseSpawner.Instance();
        MenuHandler.setMainGameScene();
        GameInterface.Instance();
        MenuHandler.switchScene(MenuHandler.MAIN_GAME);
        Renderer.Instance().initialDraw();
        if (options.isTutorial()) {
            Tutorial.setup();
        }
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

            UnitSpawner spawner = new UnitSpawner(3, goal);
            CoreEngine.Instance().setSpawner(spawner);

            engine.setPaused(!Tutorial.active);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            engine.setPaused(Tutorial.active);
        });
        unitSpawnerThread.start();
    }

    /**
     * Deletes all the elements when the quit game is toggled
     */
    private static void quitGame() {

        CoreEngine.delete();
        GameRunTime.delete();
        UnitSpawner.delete();
        BaseSpawner.delete();

        MapChooserInterface.delete();
        MapEditorInterface.delete();
        MapEditor.delete();

        GameInterface.delete();
        Renderer.delete();
        SoundManager.delete();

        LambdaStore.delete();
    }
}

