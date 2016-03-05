package maps;

import entity.Blockade;
import graph.Graph;
import graph.GraphNode;
import gui.CoreGUI;
import gui.Renderer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import menus.Menu;
import sceneElements.Images;

/**
 * Created by hung on 04/03/16.
 */
public class MapEditor implements Menu {

    private Pane mapEditorPane = null;
    private Scene mapEditorScene = null;

    private Renderer mapEditorRenderer;
    private Graph mapEditorGraph;

    // General Map Editor manager for scene, renderer and events
    public MapEditor()
    {
        createGraph();

        mapEditorRenderer = new Renderer();
        mapEditorRenderer.calculateSpacing();
        mapEditorRenderer.initialDraw();

        //initialiseScene();
        mapEditorPane = new BorderPane();
        Group mapEditor = new Group(mapEditorPane);
        mapEditorScene = new Scene(mapEditor, CoreGUI.getWIDTH(),CoreGUI.getHEIGHT());
        ((BorderPane) ((Group) mapEditorScene.getRoot()).getChildren().get(0)).setCenter(mapEditorRenderer);
        new MapEditorInterface(mapEditorScene);
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

    // think about erasing blocks as wel
    private final EventHandler<MouseEvent> sceneClickPlaceBlockade = e -> {
        Blockade blockadeInstance = new Blockade(0, "Blockade", new GraphNode(0, 0), null);
        Images.setSpriteProperties(blockadeInstance, Images.unsortableImage1);
        Blockade blockade = Blockade.mapBlockade(e, blockadeInstance, mapEditorRenderer, mapEditorGraph);
        if (blockade != null) {
            mapEditorRenderer.drawInitialEntity(blockade);
            System.out.println(blockade.getPosition());
        }
    };


    @Override
    public Scene getScene() {
        return mapEditorScene;
    }
}
