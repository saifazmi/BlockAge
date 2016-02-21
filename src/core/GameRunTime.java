package core;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sceneElements.ElementsHandler;

import java.util.logging.Logger;

/**
 * Created by Dominic on 09/02/2016.
 */
public class GameRunTime {
    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());

    private Renderer renderer;
    private CoreEngine engine;

    private static Scene mainGameScene = null;
    private static GameRunTime instance;
    public static GameRunTime Instance() {
        return instance;
    }

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
        declareElements();
        this.renderer = new Renderer(mainGameScene);
        rendererSpecificInit();
    }

    public void declareElements() {
        Pane mainGamePane = new BorderPane();
        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.Instance().getWIDTH(), CoreGUI.Instance().getHEIGHT());
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys); //equivalent to e -> ElementsHandler.handleKeys(e)//
    }

    private void rendererSpecificInit() {
        mainGameScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) ->
        {
            this.renderer.redraw();
        });
        mainGameScene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) ->
        {
            this.renderer.redraw();
        });
        ((BorderPane) ((Group) mainGameScene.getRoot()).getChildren().get(0)).setCenter(this.renderer);
    }

    /**
     * Returns the main game scene where the game will be played
     *
     * @return - the main game scene
     */
    public static Scene getScene() {
        return mainGameScene;
    }

    public void startGame()
    {
        UnitSpawner spawner = new UnitSpawner();
        //dirty setting
        spawner.setSpawnlimit(1);
        engine.setSpawner(spawner);
    }
}