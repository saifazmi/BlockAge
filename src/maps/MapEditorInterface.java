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
import sceneElements.ButtonProperties;
import sceneElements.Images;

/**
 * Created by hung on 05/03/16.
 */
public class MapEditorInterface {

    private Scene scene;
    private HBox rightMenuSaveBack;
    private VBox rightMenu, rightMenuText;
    public static Button saveButton, backButton;
    private ButtonProperties b;
    private Image saveButtonImage, backButtonImage;
    private TextArea instructions, fileNameBox;
    private EditorParser parser;

    public MapEditorInterface(Scene mapEditorScene, MapEditor mapEditor)
    {
        parser = new EditorParser(mapEditor);
        scene = mapEditorScene;
        declareElements();
        rightPane();
    }

    private void rightPane() {
        b.setButtonProperties(saveButton, "", 0, 0, parser::handle, new ImageView(saveButtonImage));

        b.setButtonProperties(backButton, "", 0, 0, parser::handle, new ImageView(backButtonImage));

        instructions.setText("Click on rectangle node to create blockades. Enter the map's name and press save to save your current configuration or back to cancel.");
        instructions.setPrefSize(300, 60);
        instructions.setEditable(false);
        instructions.setWrapText(true);

        fileNameBox.setPrefSize(300, 13);

        //dunno how spacing works yet
        rightMenuText.getChildren().addAll(fileNameBox, instructions);

        rightMenuSaveBack.getChildren().addAll(saveButton, backButton);
        rightMenuSaveBack.setSpacing(0);

        rightMenu.getChildren().addAll(rightMenuSaveBack, rightMenuText);

        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenu);
    }

    private void declareElements() {
        // HBoxes & VBoxes
        rightMenuSaveBack = new HBox();
        rightMenuText = new VBox();
        rightMenu = new VBox();

        // Images
        saveButtonImage = Images.saveMapImage;
        backButtonImage = Images.backFromEditor;

        //Buttons
        saveButton = new Button();
        backButton = new Button();

        //Text areas
        instructions = new TextArea();
        fileNameBox = new TextArea();

        b = new ButtonProperties();
    }

    public String getFileName()
    {
        return fileNameBox.getText();
    }
}
