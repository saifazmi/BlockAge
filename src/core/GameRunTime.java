package core;

import gui.CoreGUI;
import gui.GameInterface;
import gui.Renderer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import menus.Menu;
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;
import stores.ImageStore;

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
        BorderPane mainGamePane = new BorderPane();
        mainGamePane.setPrefWidth(CoreGUI.WIDTH - GameInterface.rightPaneWidth);
        mainGamePane.setPrefHeight(CoreGUI.HEIGHT);

        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.WIDTH, CoreGUI.HEIGHT);

        final String SEPARATOR = File.separator;
        final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
        final String BACKGROUNDS = "backgrounds" + SEPARATOR;
        Image grassBackground = new Image(SPRITE_RESOURCES + "misc" + SEPARATOR + "sand_background.png");

        BackgroundImage myBIF = new BackgroundImage(grassBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainGamePane.setBackground(new Background(myBIF));
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys);
        new Renderer();
        mainGamePane.setCenter(Renderer.Instance());

        mainGameScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) ->
        {
            Renderer.Instance().redraw();
        });
        mainGameScene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) ->
        {
            Renderer.Instance().redraw();
        });
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