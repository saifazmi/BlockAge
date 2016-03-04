package menus;

import gui.CoreGUI;
import gui.Renderer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by hung on 04/03/16.
 */
public class MapEditor implements Menu {

    /*public static Button[] nodeRep;
    public static int[] nodeLogic;
    private Image[] empty, filled;
    private String SEPERATOR = File.separator;*/

    private Pane mapEditorPane = null;
    private Scene mapEditorScene = null;
    //private ButtonProperties b = null;

    private Renderer mapEditorRenderer;

    public MapEditor()
    {
        //initialiseScene();
        mapEditorPane = new Pane();
        Group mapEditor = new Group(mapEditorPane);
        mapEditorScene = new Scene(mapEditor, CoreGUI.getWIDTH(),CoreGUI.getHEIGHT());
        //mapEditorScene.setOnKeyPressed(ElementsHandler::handleKeys);

        mapEditorRenderer = new Renderer();
        mapEditorRenderer.calculateSpacing();
        mapEditorRenderer.initialDraw();
    }

    @Override
    public Scene getScene() {
        return mapEditorScene;
    }
}
