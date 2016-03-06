package maps;

import gui.GameInterface;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import menus.MenuHandler;
import sceneElements.ButtonProperties;
import sceneElements.Images;

import java.io.File;
import java.io.InputStream;

/**
 * Created by hung on 05/03/16.
 */
public class MapEditorInterface {

    private Scene scene;
    private HBox rightMenuSaveBack;
    private VBox rightMenu, rightMenuText;
    public static Button saveButton, backButton;
    private ButtonProperties b;
    private Image saveButtonImage, backButtonImage, saveButtonImageHover, backButtonImageHover;
    private TextArea instructions, fileNameBox, saveStatus;
    private Label instructionLabel, saveStatusLabel, fileNameLabel;
    private static EditorParser parser;
    private Font bellotaFont;
    private String SEPARATOR = File.separator;


    public MapEditorInterface(Scene mapEditorScene, MapEditor mapEditor)
    {
        loadFont();
        parser = new EditorParser(mapEditor);
        scene = mapEditorScene;
        declareElements();
        rightPane();
    }

    private void rightPane() {

        b.setButtonProperties(saveButton, "", 0, 0, e -> handle(e), new ImageView(saveButtonImage));
        b.addHoverEffect2(saveButton, saveButtonImageHover, saveButtonImage, 0, 0);

        b.setButtonProperties(backButton, "", 0, 0, e -> handle(e), new ImageView(backButtonImage));
        b.addHoverEffect2(backButton, backButtonImageHover, backButtonImage, 0, 0);

        instructions.setText("Click on rectangle node to create blockades. Enter the map's name and press save to save your current configuration or back to cancel.");
        instructions.setPrefSize(300, 60);
        instructions.setEditable(false);
        instructions.setWrapText(true);

        instructionLabel.setFont(bellotaFont);
        instructionLabel.setText("Instructions");

        fileNameBox.setPrefSize(300, 13);

        fileNameLabel.setFont(bellotaFont);
        fileNameLabel.setText("File Name");

        saveStatus.setPrefSize(300, 13);
        saveStatus.setEditable(false);

        saveStatusLabel.setFont(bellotaFont);
        saveStatusLabel.setText("Save Status");
        saveStatus.setEditable(false);

        //dunno how spacing works yet
        rightMenuText.getChildren().addAll(fileNameLabel, fileNameBox, saveStatusLabel, saveStatus, instructionLabel,instructions);

        rightMenuSaveBack.getChildren().addAll(saveButton, backButton);
        rightMenuSaveBack.setSpacing(0);

        rightMenu.getChildren().addAll(rightMenuSaveBack, rightMenuText);

        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenu);
    }

    public static void handle(ActionEvent event) {
        if (event.getSource() == MapEditorInterface.saveButton)
        {
            parser.saveToUserFile();
        }
        if (event.getSource() == MapEditorInterface.backButton)
        {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
    }

    private void declareElements() {
        // HBoxes & VBoxes
        rightMenuSaveBack = new HBox();
        rightMenuText = new VBox();
        rightMenu = new VBox();

        // Images
        saveButtonImage = Images.saveMapImage;
        saveButtonImageHover = Images.saveMapImageHovered;
        backButtonImage = Images.backFromEditor;
        backButtonImageHover = Images.backFromEditorHovered;

        //Buttons
        saveButton = new Button();
        backButton = new Button();

        //Text areas
        instructions = new TextArea();
        fileNameBox = new TextArea();
        saveStatus = new TextArea();

        //Label
        instructionLabel = new Label();
        saveStatusLabel = new Label();
        fileNameLabel = new Label();

        b = new ButtonProperties();
    }

    public String getFileName()
    {
        return fileNameBox.getText();
    }

    public TextArea getSaveStatusBox()
    {
        return saveStatus;
    }

    private void loadFont() {
        InputStream fontStream = MapEditorInterface.class.getResourceAsStream("" + SEPARATOR + "fonts" + SEPARATOR + "Bellota-Bold.otf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFont = Font.loadFont(fontStream, 17);
    }
}
