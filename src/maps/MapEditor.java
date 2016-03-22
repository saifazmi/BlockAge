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
     *
     * @return the only game runtime to be created
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
    private MapEditor() {

        createGraph();

        //@todo delete after map quits
        Renderer.delete();
        mapEditorRenderer = new Renderer();
        mapEditorRenderer.calculateSpacing();

        //initialiseScene();
        mapEditorPane = new BorderPane();
        final String SEPARATOR = File.separator;
        final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
        final String BACKGROUNDS = "backgrounds" + SEPARATOR;
        Image sandBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "SandBackground.png");

        BackgroundImage myBIF = new BackgroundImage(sandBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mapEditorPane.setBackground(new Background(myBIF));
        //Group mapEditor = new Group(mapEditorPane);
        mapEditorScene = new Scene(mapEditorPane, CoreGUI.WIDTH, CoreGUI.HEIGHT);
        ((BorderPane) mapEditorScene.getRoot()).setCenter(mapEditorRenderer);
        mapEditorInterface = new MapEditorInterface(mapEditorScene, this);
        mapEditorRenderer.initialDraw();
        mapEditorScene.setOnMouseClicked(sceneClickPlaceBlockade);
    }

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

    public Graph getGraph() {
        return mapEditorGraph;
    }

    // think about erasing blocks as wel
    private final EventHandler<MouseEvent> sceneClickPlaceBlockade = e -> {
        Blockade blockadeInstance = new Blockade(0, "Blockade", new GraphNode(0, 0), null);
        ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
        Blockade blockade = Blockade.mapBlockade(e, blockadeInstance, mapEditorRenderer, mapEditorGraph);
        if (blockade != null) {
            mapEditorRenderer.drawInitialEntity(blockade);
            System.out.println(blockade.getPosition());
        }
    };

    public MapEditorInterface getInterface() {
        return mapEditorInterface;
    }

    public Renderer getRenderer() {
        return mapEditorRenderer;
    }

    @Override
    public Scene getScene() {
        return mapEditorScene;
    }

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
