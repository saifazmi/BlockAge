package menus;

import core.CoreGUI;
import core.Renderer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

import javax.annotation.processing.RoundEnvironment;
import java.io.File;

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
        mapEditorScene = new Scene(mapEditor, CoreGUI.Instance().getWIDTH(),CoreGUI.Instance().getHEIGHT());
        //mapEditorScene.setOnKeyPressed(ElementsHandler::handleKeys);

        mapEditorRenderer = new Renderer(mapEditorScene);
        mapEditorRenderer.calculateSpacing();
        mapEditorRenderer.initialDraw();
    }

    @Override
    public Scene getScene() {
        return mapEditorScene;
    }
}
