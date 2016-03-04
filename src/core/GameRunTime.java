package core;

import gui.CoreGUI;
import gui.Renderer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;

import java.io.File;
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
    private SpriteImage lastClicked = null;

    public static GameRunTime Instance() {
        if (instance == null) {
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

        mainGamePane = new BorderPane();
        mainGamePane.setPrefWidth(CoreGUI.getWIDTH() - 324);
        mainGamePane.setPrefHeight(CoreGUI.getHEIGHT());

        String SEPARATOR = File.separator;
        //BackgroundImage myBI = new BackgroundImage(new Image(SEPARATOR + "sprites" + SEPARATOR + "background" + SEPARATOR + "Main.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        //mainGamePane.setBackground(new Background(myBI));

        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.getWIDTH(), CoreGUI.getHEIGHT());
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys);
        new Renderer();
        ((BorderPane) ((Group) mainGameScene.getRoot()).getChildren().get(0)).setCenter(Renderer.Instance());
    }

    /**
     * Returns the main game scene where the game will be played
     *
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

    public SpriteImage getLastClicked() {
        return lastClicked;
    }

    public void setLastClicked(SpriteImage lastClicked) {
        this.lastClicked = lastClicked;
    }
}