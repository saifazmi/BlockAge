package core;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import lambdastorage.LambdaStore;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.Images;

import java.io.File;
import java.io.InputStream;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 */
public class GameInterface {
    private Scene scene = GameRunTime.Instance().getScene();
    public static Button fileButton, fileExitButton, helpButton, playButton, pauseButton, unsortableButton, sortableButton;
    public static TextArea unitDescriptionText, textInfoText, algorithmVisualisationText, searchVisualisationText;
    public static int bottomPaneHeight = 130;
    public static int rightPaneWidth = 224;
    public static Font bellotaFont;
    private Label unitDescriptionLabel, textInfoLabel, algorithmVisualisationLabel, searchVisualisationLabel, blockadesLabel, sortLabel;
    private HBox bottomMenuBox, rightMenuPlayPause, sortingBox;
    private VBox rightMenuBox, unitBox, textBox;
    private Image playImage, playImageHovered, pauseImage, pauseImageHovered, unsortableImage, sortableImage;
    private ButtonProperties b;
    private static String SEPARATOR = File.separator;

    public GameInterface() {
        loadFont();
        declareElements();
        rightPane();
        bottomPane();
    }

    /**
     * Loads the font for labels/buttons
     */
    public void loadFont() {
        InputStream fontStream = GameInterface.class.getResourceAsStream(".." + SEPARATOR + "sprites" + SEPARATOR + "Bellota-Bold.otf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFont = Font.loadFont(fontStream, 17);
    }

    /**
     * Declare the elements for the scene
     */
    public void declareElements() {
        //HBoxes
        bottomMenuBox = new HBox();
        rightMenuPlayPause = new HBox();
        sortingBox = new HBox();
        //VBoxes
        rightMenuBox = new VBox();
        unitBox = new VBox();
        textBox = new VBox();
        //Labels
        unitDescriptionLabel = new Label();
        textInfoLabel = new Label();
        algorithmVisualisationLabel = new Label();
        searchVisualisationLabel = new Label();
        blockadesLabel = new Label();
        sortLabel = new Label();
        //Text Areas
        unitDescriptionText = new TextArea();
        textInfoText = new TextArea();
        algorithmVisualisationText = new TextArea();
        searchVisualisationText = new TextArea();
        //Buttons
        fileButton = new Button();
        helpButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        unsortableButton = new Button();
        sortableButton = new Button();
        b = new ButtonProperties();
        //Images
        playImage = Images.playImage;
        playImageHovered = Images.playImageHovered;
        pauseImage = Images.pauseImage;
        pauseImageHovered = Images.pauseImageHovered;
        unsortableImage = Images.unsortableImage2;
        sortableImage = Images.sortableImage2;
    }

    /**
     * Constructs the bottom Pane of the scene
     */
    public void bottomPane() {
        unitDescriptionLabel.setText("Unit Description");
        unitDescriptionLabel.setFont(bellotaFont);
        unitDescriptionText.setPrefSize(300, 80);
        unitDescriptionText.setEditable(false);

        textInfoLabel.setText("Text Info");
        textInfoLabel.setFont(bellotaFont);
        textInfoText.setPrefSize(300, 80);
        textInfoText.setEditable(false);

        // Set the properties for the play button
        b.setButtonProperties(playButton, "", 0, 0, ElementsHandler::handle, new ImageView(playImage));
        b.addHoverEffect(playButton, playImageHovered, playImage, 0, 0);

        // Set the properties for the pause button
        b.setButtonProperties(pauseButton, "", 0, 0, ElementsHandler::handle, new ImageView(pauseImage));
        b.addHoverEffect(pauseButton, pauseImageHovered, pauseImage, 0, 0);

        unitBox.getChildren().addAll(unitDescriptionLabel, unitDescriptionText);
        unitBox.setAlignment(Pos.TOP_LEFT);
        textBox.getChildren().addAll(textInfoLabel, textInfoText);
        textBox.setAlignment(Pos.TOP_LEFT);
        rightMenuPlayPause.getChildren().addAll(playButton, pauseButton);
        rightMenuPlayPause.setAlignment(Pos.BOTTOM_CENTER);
        bottomMenuBox.setAlignment(Pos.CENTER);
        bottomMenuBox.getChildren().addAll(unitBox, textBox, rightMenuPlayPause);
        bottomMenuBox.setSpacing(20);

        bottomMenuBox.heightProperty().addListener((observable, oldWidth, newWidth) -> {
            System.out.println("ADAFAA" + newWidth);
        });
        BorderPane.setMargin(bottomMenuBox, new Insets(12, 12, 12, 12));
        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setBottom(bottomMenuBox);
    }

    /**
     * Constructs the right Pane of the scene
     */
    public void rightPane() {
        algorithmVisualisationLabel.setText("Search Visualisation");
        algorithmVisualisationLabel.setFont(bellotaFont);
        algorithmVisualisationText.setPrefSize(200, 170);
        algorithmVisualisationText.setEditable(false);

        searchVisualisationLabel.setText("Sort Visualisation");
        searchVisualisationLabel.setFont(bellotaFont);
        searchVisualisationText.setPrefSize(200, 170);
        searchVisualisationText.setEditable(false);

        blockadesLabel.setText("Blockades");
        blockadesLabel.setFont(bellotaFont);

        sortLabel.setText("Error");
        sortLabel.setFont(bellotaFont);

        // Set the properties for the unsortable button
        b.setButtonProperties(unsortableButton, "", 0, 0, ElementsHandler::handle, new ImageView(unsortableImage));
        b.addHoverEffect(unsortableButton, unsortableImage, unsortableImage, 0, 0);
        handleSort(unsortableButton, "Unsortable blockade");
        unsortableButton.setOnMouseClicked(e -> LambdaStore.Instance().setBlockadeClickEvent());

        // Set the properties for the sortable button
        b.setButtonProperties(sortableButton, "", 0, 0, ElementsHandler::handle, new ImageView(sortableImage));
        b.addHoverEffect(sortableButton, sortableImage, sortableImage, 0, 0);
        handleSort(sortableButton, "Sortable blockade");

        // add everything to the pane
        sortingBox.getChildren().addAll(unsortableButton, sortableButton);
        rightMenuBox.getChildren().addAll(algorithmVisualisationLabel, algorithmVisualisationText, searchVisualisationLabel, searchVisualisationText, blockadesLabel, sortingBox);
        rightMenuBox.setSpacing(10);
        BorderPane.setMargin(rightMenuBox, new Insets(12, 12, 12, 12));
        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenuBox);
    }

    /**
     * Handles the blockades appropriately
     * @param button - the button to be handled
     * @param text   - the text the label will have
     */
    public void handleSort(Button button, String text) {
        button.setOnMouseExited(event -> {
            scene.setCursor(Cursor.DEFAULT);
            if (rightMenuBox.getChildren().contains(sortLabel))
                rightMenuBox.getChildren().remove(sortLabel);
        });
        button.setOnMouseEntered(event -> {
            sortLabel.setText(text);
            scene.setCursor(Cursor.HAND);
            if (!rightMenuBox.getChildren().contains(sortLabel))
                rightMenuBox.getChildren().add(sortLabel);
        });
    }
}
