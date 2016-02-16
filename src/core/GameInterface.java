package core;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

import java.io.File;

public class GameInterface {
    public static Button fileButton, fileExitButton, helpButton, playButton, pauseButton;
    public static TextArea unitDescriptionText, textInfoText;

    private Label unitDescriptionLabel, textInfoLabel;
    private HBox topMenuBox, rightMenuPlayPause;
    private VBox rightMenuBox;
    private Image fileImage, fileImageHovered, helpImage, helpImageHovered, unitDescriptionImage, textInfoImage, playImage, playImageHovered, pauseImage, pauseImageHovered;
    private ButtonProperties b;
    private static String SEPARATOR = File.separator;

    public GameInterface() {
        declareElements();
        topPane();
        rightPane();
        bottomPane();
    }

    /**
     * Declare the elements for the scene
     */
    public void declareElements() {
        topMenuBox = new HBox();
        rightMenuPlayPause = new HBox();

        rightMenuBox = new VBox();

        unitDescriptionLabel = new Label();
        textInfoLabel = new Label();

        unitDescriptionText = new TextArea();
        textInfoText = new TextArea();

        fileButton = new Button();
        helpButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        b = new ButtonProperties();

        fileImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "file_button.png");
        fileImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "file_button_hovered.png");

        helpImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "help_button.png");
        helpImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "help_button_hovered.png");

        playImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button.png");
        playImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button_hovered.png");

        pauseImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button.png");
        pauseImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button_hovered.png");


        unitDescriptionImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "unit_description_label.png");
        textInfoImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "text_log_label.png");
    }

    /**
     * Construct the top Pane of the scene
     */
    public void topPane() {
        // Set properties for the file button
        b.setButtonProperties(fileButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(fileImage));
        b.addHoverEffect(fileButton, fileImageHovered, fileImage, 0, 0);

        // Set properties for the help button
        b.setButtonProperties(helpButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(helpImage));
        b.addHoverEffect(helpButton, helpImageHovered, helpImage, 0, 0);

        topMenuBox.getChildren().addAll(fileButton, helpButton);

        ((BorderPane) ((Group) GameRunTime.getScene().getRoot()).getChildren().get(0)).setTop(topMenuBox);
    }

    /**
     * Constructs the bottom Pane of the scene
     */
    public void bottomPane() {

    }

    /**
     * Constructs the right Pane of the scene
     */
    public void rightPane() {
        unitDescriptionLabel.setGraphic(new ImageView(unitDescriptionImage));
        unitDescriptionText.setPrefSize(200, 200);
        unitDescriptionText.setEditable(false);

        textInfoLabel.setGraphic(new ImageView(textInfoImage));
        textInfoText.setPrefSize(200, 200);
        textInfoText.setEditable(false);

        b.setButtonProperties(playButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(playImage));
        b.addHoverEffect(playButton, playImageHovered, playImage, 0, 0);

        b.setButtonProperties(pauseButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(pauseImage));
        b.addHoverEffect(pauseButton, pauseImageHovered, pauseImage, 0, 0);

        rightMenuPlayPause.getChildren().addAll(playButton, pauseButton);

        rightMenuBox.getChildren().addAll(unitDescriptionLabel, unitDescriptionText, textInfoLabel, textInfoText, rightMenuPlayPause);
        rightMenuBox.setSpacing(10);
        BorderPane.setMargin(rightMenuBox, new Insets(12, 12, 12, 12));

        ((BorderPane) ((Group) GameRunTime.getScene().getRoot()).getChildren().get(0)).setRight(rightMenuBox);
    }

}
