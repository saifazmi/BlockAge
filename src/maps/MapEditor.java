package maps;

import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import gui.CoreGUI;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import menus.Menu;
import stores.ImageStore;

import java.io.File;
import java.util.List;

/**
 * Created by hung on 04/03/16.
 */
public class MapEditor implements Menu {

    private Pane mapEditorPane = null;
    private Scene mapEditorScene = null;

    private Renderer mapEditorRenderer;
    private Graph mapEditorGraph;

    private MapEditorInterface mapEditorInterface;
    private MapChooserInterface mapChooserInterface;

    private static MapEditor instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     * @return the only Map Editor to be created
     */
    public static MapEditor Instance() {

        if (instance == null) {
            instance = new MapEditor();
        }
        return instance;
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    // General Map Editor manager for scene, renderer and events

    /**
     * Creates a class that handles different aspect of map editing
     * The map editor includes a Graph and Renderer as a separate instance to the game's instances of the same classes
     * It also has its own panes
     */
    private MapEditor() {

        createGraph();

        //@todo delete after map quits
        Renderer.delete();
        mapEditorRenderer = new Renderer();
        mapEditorRenderer.calculateSpacing();

        // creating the background from resources
        final String SEPARATOR = File.separator;
        final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
        final String BACKGROUNDS = "backgrounds" + SEPARATOR;
        Image sandBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "SandBackground.png");
        BackgroundImage myBIF = new BackgroundImage(sandBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Creating the pane which will include the background
        mapEditorPane = new BorderPane();
        mapEditorPane.setBackground(new Background(myBIF));

        // Create a new scene with the previously created pane
        mapEditorScene = new Scene(mapEditorPane, CoreGUI.WIDTH, CoreGUI.HEIGHT);

        // Put the renderer onto the pane
        ((BorderPane) mapEditorScene.getRoot()).setCenter(mapEditorRenderer);
        mapEditorRenderer.initialDraw();

        // Create the interface for the scene
        mapEditorInterface = new MapEditorInterface(mapEditorScene, this);

        // define the mouse click event handler
        mapEditorScene.setOnMouseClicked(sceneClickPlaceBlockade);
    }

    /**
     * Creates new graph and add each node's neighbour appropriately
     */
    private void createGraph() {
        mapEditorGraph = new Graph();

        for (int x = 0; x < Graph.WIDTH; x++) {
            for (int y = 0; y < Graph.HEIGHT; y++) {
                GraphNode node = new GraphNode(x, y);
                mapEditorGraph.nodeWith(node);
            }
        }

        for (GraphNode aNode : mapEditorGraph.getNodes()) {
            aNode.addNeighbours(mapEditorGraph);
        }
    }

    /**
     * Returns the graph of the map editor
     * @return
     */
    public Graph getGraph() {
        return mapEditorGraph;
    }

    /**
     * Mouse click handling event.
     * Create a new blockade and place in appropriate node, logically and graphically
     */
    private final EventHandler<MouseEvent> sceneClickPlaceBlockade = e -> {
        Blockade blockadeInstance = new Blockade(0, "Blockade", new GraphNode(0, 0), null);
        ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
        Blockade blockade = Blockade.mapBlockade(e, blockadeInstance, mapEditorRenderer, mapEditorGraph);
        if (blockade != null) {
            mapEditorRenderer.drawInitialEntity(blockade);
            //System.out.println(blockade.getPosition());
        }
    };

    /**
     * Returns the object that holds the interface logic for the map editor
     * @return The interface as an object
     */
    public MapEditorInterface getInterface() {
        return mapEditorInterface;
    }

    /**
     * Gets the renderer instance of the map editor
     * @return the Map renderer Instance
     */
    public Renderer getRenderer() {
        return mapEditorRenderer;
    }

    /**
     * Returns the map editor scene
     * @return
     */
    @Override
    public Scene getScene() {
        return mapEditorScene;
    }

    /**
     * Clears all the nodes on the graph of blockades, this will remove them logically then tells the renderer to remove them graphically
     */
    public void clearNodes() {
        List<GraphNode> graph = mapEditorGraph.getNodes();
        for (GraphNode aGraph : graph) {
            Blockade blockade = aGraph.getBlockade();
            if (blockade != null) {
                mapEditorRenderer.remove(blockade.getSprite());
                aGraph.setBlockade(null);
            }
        }
    }
}
