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
 * @author : Dominic Walters; Contributors - Dominic Walters, and Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Creates the main interface for the game. This will contain the grid
 *          where all the entities will be drawn on.
 * @date : 28/01/16
 */
public class GameRunTime {

    private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());

    private SpriteImage lastClicked = null;

    // Dependencies
    private CoreEngine engine;
    private static Scene mainGameScene = null;
    private final String SEPARATOR = File.separator;
    private final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
    private final String BACKGROUNDS = "backgrounds" + SEPARATOR;

    // Instance for singleton.
    private static GameRunTime instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the game runtime instance
     */
    public static GameRunTime Instance() {

        if (instance == null) {
            instance = new GameRunTime();
        }

        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {

        instance = null;
    }

    /**
     * Constructor for the game run time.
     * Initialises the engine, renderer, unit and unitSpawner
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

        // The pane that will be placed on the grid in order to set a background
        BorderPane mainGamePane = new BorderPane();

        // Setting width and height for this pane
        mainGamePane.setPrefWidth(CoreGUI.WIDTH - GameInterface.rightPaneWidth);
        mainGamePane.setPrefHeight(CoreGUI.HEIGHT);

        // Creating the scene that will display the pane
        Group mainGame = new Group(mainGamePane);
        mainGameScene = new Scene(mainGame, CoreGUI.WIDTH, CoreGUI.HEIGHT);

        // The image which will be displayed on the grid
        Image sandBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "SandBackground.png");
        BackgroundImage myBIF = new BackgroundImage(
                sandBackground,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        // Setting the image on the pane
        mainGamePane.setBackground(new Background(myBIF));
        mainGameScene.setOnKeyPressed(ElementsHandler::handleKeys);

        Renderer.Instance();
        // Setting the renderer on the pane which will be on top of the background
        mainGamePane.setCenter(Renderer.Instance());
    }

    // GETTER methods

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

    // SETTER methods

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

    }
}