package maps;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import sceneElements.ButtonProperties;
import sceneElements.Images;
import java.io.File;
import java.io.InputStream;

/**
 * Created by hung on 05/03/16.
 */
public class MapEditorInterface {

    private Scene scene;
    private static String SEPARATOR = File.separator;
    private HBox rightMenuSaveBack, rightMenuInstructions;
    private VBox rightMenu;
    public static Button saveButton, backButton;
    private ButtonProperties b;
    private Image saveButtonImage, backButtonImage;
    private javafx.scene.control.TextArea instructions;
    EditorParser parser = new EditorParser();

    public MapEditorInterface(Scene mapEditorScene)
    {
        scene = mapEditorScene;
        declareElements();
        rightPane();
    }

    private void rightPane() {
        b.setButtonProperties(saveButton, "", 0, 0, parser::handle, new ImageView(saveButtonImage));

        b.setButtonProperties(backButton, "", 0, 0, parser::handle, new ImageView(backButtonImage));

        instructions.setText("Click on rectangle node to create blockades. Press save to save your current configuration or back to cancel.");
        instructions.setPrefSize(300, 80);
        instructions.setEditable(false);
        instructions.setWrapText(true);

        //dunno how spacing works yet
        rightMenuInstructions.getChildren().add(instructions);

        rightMenuSaveBack.getChildren().addAll(saveButton,backButton);
        rightMenuSaveBack.setSpacing(0);

        rightMenu.getChildren().addAll(rightMenuSaveBack, rightMenuInstructions);

        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenu);
    }

    private void declareElements() {
        // HBoxes & VBoxes
        rightMenuSaveBack = new HBox();
        rightMenuInstructions = new HBox();
        rightMenu = new VBox();

        // Images
        saveButtonImage = Images.saveMapImage;
        backButtonImage = Images.backFromEditor;

        //Buttons
        saveButton = new Button();
        backButton = new Button();

        //Labels
        instructions = new TextArea();

        b = new ButtonProperties();
    }
}
