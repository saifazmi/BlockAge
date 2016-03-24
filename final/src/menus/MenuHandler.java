package menus;

import core.CoreEngine;
import core.GameRunTime;
import javafx.scene.Scene;
import javafx.stage.Stage;
import maps.MapEditor;

/**
 * @author : Paul Popa; Contributors - Evgeniy Kim
 * @version : 23/03/2016;
 *          <p>
 *          Handles the menus so that we can easily switch through them
 * @date : 09/02/16
 */
public class MenuHandler {

    private static Stage primaryStage;

    private MainMenu mainMenu = new MainMenu();
    private OptionsMenu optionsMenu = new OptionsMenu();
    private PauseMenu pauseMenu = new PauseMenu();
    private static EndGameMenu endGameMenu = new EndGameMenu();

    private static Scene[] sceneList = new Scene[20];

    //Declaring indexes for the menus so that we know how to access them easily in the scene array
    public final static int MAIN_MENU = 0;
    public final static int OPTIONS_MENU = 1;
    public final static int MAIN_GAME = 2;
    public final static int PAUSE_MENU = 3;
    public final static int MAP_EDITOR = 4;
    public final static int END_GAME_MENU = 5;

    // the first initialisation the current scene
    public static int currentScene = MAIN_MENU;
    // the first initialisation the last scene accessed
    public static int lastScene = MAIN_MENU;

    /**
     * Initialises the stage to be the same used for all scenes
     *
     * @param primaryStage - our primary stage
     */
    public MenuHandler(Stage primaryStage) {

        MenuHandler.primaryStage = primaryStage;
        setScenes();
    }

    /**
     * Places all the scenes into an array of scenes
     */
    public void setScenes() {

        sceneList[MAIN_MENU] = mainMenu.getScene();
        sceneList[OPTIONS_MENU] = optionsMenu.getScene();
        sceneList[PAUSE_MENU] = pauseMenu.getScene();
        sceneList[MAP_EDITOR] = MapEditor.Instance().getScene();
        sceneList[END_GAME_MENU] = endGameMenu.getScene();
    }

    /**
     * Sets the main game scene where the game will happen
     */
    public static void setMainGameScene() {

        sceneList[MAIN_GAME] = GameRunTime.Instance().getScene();
    }

    /**
     * Switches the current scene with another scene
     *
     * @param scene - the scene that we want to switch to
     */
    public static void switchScene(int scene) {

        sceneList[currentScene].getRoot().setVisible(false);
        sceneList[currentScene].getRoot().setDisable(true);

        sceneList[scene].getRoot().setVisible(true);
        sceneList[scene].getRoot().setDisable(false);

        lastScene = currentScene;
        currentScene = scene;

        primaryStage.setScene(sceneList[scene]);

        if (scene == END_GAME_MENU) {
            endGameMenu.setScore(CoreEngine.Instance().getScore().getScore());
        }
    }
}