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
import sceneElements.ElementsHandler;
import sceneElements.SpriteImage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Dominic Walters with code by Dominic Walters, and Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 */
public class GameRunTime {

    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());

    // States
    private boolean basePlaced = false;
    private SpriteImage lastClicked = null;

    // Dependencies
    private CoreEngine engine;
    private static Scene mainGameScene = null;

    // Instance for singleton.
    private static GameRunTime instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the only game runtime to be created
     */
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

        Thread engThread = new Thread(() -> {

            this.engine = new CoreEngine();
            this.engine.startGame();
        });
        engThread.start();

        while (this.engine == null) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
        }

        BorderPane mainGamePane = new BorderPane();
        mainGamePane.setPrefWidth(CoreGUI.WIDTH - GameInterface.rightPaneWidth);
        mainGamePane.setPrefHeight(CoreGUI.HEIGHT);

        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.WIDTH, CoreGUI.HEIGHT);

        //@TODO: why are these here?
        final String SEPARATOR = File.separator;
        final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
        final String BACKGROUNDS = "backgrounds" + SEPARATOR;
        Image sandBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "SandBackground.png");
        Image grassBackground = new Image(SPRITE_RESOURCES + "misc" + SEPARATOR + "sand_background.png");

        BackgroundImage myBIF = new BackgroundImage(sandBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainGamePane.setBackground(new Background(myBIF));
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys);

        new Renderer();
        mainGamePane.setCenter(Renderer.Instance());

        mainGameScene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            Renderer.Instance().redraw();
        });

        mainGameScene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            Renderer.Instance().redraw();
        });
    }

    /**
     * Starts the game by instantiating the BaseSpawner
     */
    public void startGame() {

        new BaseSpawner();
    }

    /**
     * Checks if the player has placed the base
     *
     * @return true if the base has been placed else false
     */
    public boolean isBasePlaced() {

        return this.basePlaced;
    }

    /**
     * Returns the main game scene where the game will be played
     *
     * @return the main game scene
     */
    public Scene getScene() {

        return mainGameScene;
    }

    /**
     * Returns the last clicked sprite
     *
     * @return the last clicked SpriteImage object
     */
    public SpriteImage getLastClicked() {

        return this.lastClicked;
    }

    /**
     * Changes the last clicked sprite
     *
     * @param lastClicked the sprite image last clicked
     */
    public void setLastClicked(SpriteImage lastClicked) {

        this.lastClicked = lastClicked;
    }

    /**
     * Set the base existence
     *
     * @param basePlaced the base placement state
     */
    public void setBasePlaced(boolean basePlaced) {

        this.basePlaced = basePlaced;
    }
}