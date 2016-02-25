package core;

import graph.GraphNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sceneElements.ElementsHandler;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Dominic on 09/02/2016.
 */
public class GameRunTime{
    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());
    private static String SEPARATOR = File.separator;

    private Renderer renderer;
    private CoreEngine engine;
    private Stage primaryStage;
    private boolean basePlaced = false;

    Pane mainGamePane = null;
    static Scene mainGameScene = null;
    Group mainGame = null;


    private static GameRunTime instance;

    public static GameRunTime Instance() {
        return instance;
    }

    public GameRunTime() {
        this.instance = this;
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
        //renderer.initialDraw();
    }

    public void declareElements() {
        mainGamePane = new BorderPane();
        mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.WIDTH, CoreGUI.HEIGHT);
        mainGameScene.setOnKeyPressed(e -> ElementsHandler.handleKeys(e));
    }

    public Renderer getRenderer() {
        return this.renderer;
    }

    public CoreEngine getEngine() {
        return this.engine;
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
        UnitSpawner spawner = new UnitSpawner(this, 1);
        engine.setSpawner(spawner);
    }

    // to notify the placement of the base
    public void setBasePlaced(boolean basePlaced) {
        this.basePlaced = basePlaced;
    }

    public boolean isBasePlaced() {
        return basePlaced;
    }
}