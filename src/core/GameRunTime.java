package core;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sceneElements.ElementsHandler;

import java.util.logging.Logger;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters, and Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 */
public class GameRunTime {
    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());
    private CoreEngine engine;

    private boolean basePlaced = false;
    private static Scene mainGameScene = null;
    private static GameRunTime instance = null;

    public static GameRunTime Instance() {
        if(instance == null)
        {
            instance = new GameRunTime();
        }
        return instance;
    }

    /**
     * Constructor for the game run time.
     * Initialises the engine, renderer, and unit unitSpawner
     */
    public GameRunTime() {
        instance = this;
        Thread engThread = new Thread(() ->
        {
            this.engine = new CoreEngine();
            this.engine.startGame();
        });
        engThread.start();
        while (this.engine == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Pane mainGamePane = new BorderPane();
        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.getWIDTH(), CoreGUI.getHEIGHT());
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys);
        new Renderer();
        ((BorderPane) ((Group) mainGameScene.getRoot()).getChildren().get(0)).setCenter(Renderer.Instance());
    }

    /**
     * Returns the main game scene where the game will be played
     * @return - the main game scene
     */
    public Scene getScene() {
        return mainGameScene;
    }

    public void startGame() {
        new BaseSpawner();
    }
    // to notify the placement of the base
    public void setBasePlaced(boolean basePlaced) {
        this.basePlaced = basePlaced;
    }

    public boolean isBasePlaced() {
        return basePlaced;
    }
}