package maps;

import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import gui.CoreGUI;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Group;
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

    // General Map Editor manager for scene, renderer and events
    public MapEditor() {
        createGraph();

        mapEditorRenderer = new Renderer();
        mapEditorRenderer.calculateSpacing();
        mapEditorRenderer.initialDraw();

        //initialiseScene();
        mapEditorPane = new BorderPane();
        final String SEPARATOR = File.separator;
        final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;
        final String BACKGROUNDS = "backgrounds" + SEPARATOR;
        Image sandBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "SandBackground.png");

        BackgroundImage myBIF = new BackgroundImage(sandBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mapEditorPane.setBackground(new Background(myBIF));
        Group mapEditor = new Group(mapEditorPane);
        mapEditorScene = new Scene(mapEditor, CoreGUI.WIDTH, CoreGUI.HEIGHT);
        ((BorderPane) ((Group) mapEditorScene.getRoot()).getChildren().get(0)).setCenter(mapEditorRenderer);
        mapEditorInterface = new MapEditorInterface(mapEditorScene, this);
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
        for (int i = 0; i < mapEditorGraph.getNodes().size(); i++) {
            mapEditorGraph.getNodes().get(i).setBlockade(null);
        }

        //mapEditorRenderer.drawInitialEntity()
    }
}
