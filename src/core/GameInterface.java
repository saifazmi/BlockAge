package core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lambdastorage.LambdaStore;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

import java.io.File;
import java.io.InputStream;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 */
public class GameInterface {
    public static Button fileButton, fileExitButton, helpButton, playButton, pauseButton, unsortableButton, sortableButton;
    public static TextArea unitDescriptionText, textInfoText, algorithmVisualisationText, sortVisualisationText;
    public static int bottomPaneHeight = 0;
    public static int rightPaneWidth = 324;
    public static Font bellotaFont;
    private Label unitDescriptionLabel, textInfoLabel, algorithmVisualisationLabel, sortVisualisationLabel, blockadesLabel, sortLabel;
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
        bottomMenuBox = new HBox();
        rightMenuPlayPause = new HBox();
        sortingBox = new HBox();

        rightMenuBox = new VBox();
        unitBox = new VBox();
        textBox = new VBox();

        unitDescriptionLabel = new Label();
        textInfoLabel = new Label();
        algorithmVisualisationLabel = new Label();
        sortVisualisationLabel = new Label();
        blockadesLabel = new Label();
        sortLabel = new Label();

        unitDescriptionText = new TextArea();
        textInfoText = new TextArea();
        algorithmVisualisationText = new TextArea();
        sortVisualisationText = new TextArea();

        fileButton = new Button();
        helpButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        unsortableButton = new Button();
        sortableButton = new Button();
        b = new ButtonProperties();

        playImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button.png");
        playImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button_hovered.png");

        pauseImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button.png");
        pauseImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button_hovered.png");

        unsortableImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, false, false);
        sortableImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "sortableBlock640.png", 55, 55, false, false);

        unsortableButton.setOnMouseClicked(e ->
        {
            System.out.println("Clicked");
            LambdaStore store = new LambdaStore();
            if (GameRunTime.getScene().getOnMouseClicked() != null && GameRunTime.getScene().getOnMouseClicked().equals(store.getSceneClickPlaceUnbreakableBlockade())) {
                GameRunTime.getScene().setOnMouseClicked(null);
            } else if (GameRunTime.Instance().isBasePlaced()) {
                GameRunTime.getScene().setOnMouseClicked(store.getSceneClickPlaceUnbreakableBlockade());
            }
        });
    }

    /**
     * Constructs the bottom Pane of the scene
     */
    public void bottomPane() {
        textInfoLabel.setText("Text Info");
        textInfoLabel.setFont(bellotaFont);
        textInfoText.setPrefSize(300, 80);
        textInfoText.setEditable(false);

        // Set the properties for the play button
        b.setButtonProperties(playButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(playImage));
        b.addHoverEffect(playButton, playImageHovered, playImage, 0, 0);

        // Set the properties for the pause button
        b.setButtonProperties(pauseButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(pauseImage));
        b.addHoverEffect(pauseButton, pauseImageHovered, pauseImage, 0, 0);


        textBox.getChildren().addAll(textInfoLabel, textInfoText);
        textBox.setAlignment(Pos.TOP_LEFT);
        rightMenuPlayPause.getChildren().addAll(playButton, pauseButton);
        bottomMenuBox.setAlignment(Pos.CENTER);
        //bottomMenuBox.getChildren().addAll(textBox, rightMenuPlayPause);
        bottomMenuBox.setSpacing(20);
        //bottomMenuBox.setStyle("-fx-border-color: red;"); // testpurpose

        unitBox.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                System.out.println("ADAFAA" + newWidth);
            }
        });
        BorderPane.setMargin(bottomMenuBox, new Insets(12, 12, 12, 12));
        ((BorderPane) ((Group) GameRunTime.getScene().getRoot()).getChildren().get(0)).setBottom(bottomMenuBox);
    }

    /**
     * Constructs the right Pane of the scene
     */
    public void rightPane() {
    	//UNIT DESCRIPTION PANE
        unitDescriptionLabel.setText("Unit Description");
        unitDescriptionLabel.setFont(bellotaFont);
        unitDescriptionText.setPrefSize(300, 80);
        unitDescriptionText.setEditable(false);
        
        unitBox.getChildren().addAll(unitDescriptionLabel, unitDescriptionText);

        //SORT VISUALISATION PANE
        sortVisualisationLabel.setText("Sort Visualisation");
        sortVisualisationLabel.setFont(bellotaFont);
        sortVisualisationText.setPrefSize(200, 150);
        sortVisualisationText.setEditable(false);

        blockadesLabel.setText("Blockades");
        blockadesLabel.setFont(bellotaFont);

        sortLabel.setText("Error");
        sortLabel.setFont(bellotaFont);


        // Set the properties for the unsortable button
        b.setButtonProperties(unsortableButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(unsortableImage));
        b.addHoverEffect(unsortableButton, unsortableImage, unsortableImage, 0, 0);
        handleSort(unsortableButton, "Unsortable blockade");
        unsortableButton.setOnMouseClicked(e ->
        {
            System.out.println("Clicked");
            LambdaStore store = new LambdaStore();
            if (GameRunTime.getScene().getOnMouseClicked() != null && GameRunTime.getScene().getOnMouseClicked().equals(store.getSceneClickPlaceUnbreakableBlockade())) {
                GameRunTime.getScene().setOnMouseClicked(null);
            } else {
                GameRunTime.getScene().setOnMouseClicked(store.getSceneClickPlaceUnbreakableBlockade());
            }
        });


        // Set the properties for the sortable button
        b.setButtonProperties(sortableButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(sortableImage));
        b.addHoverEffect(sortableButton, sortableImage, sortableImage, 0, 0);
        handleSort(sortableButton, "Sortable blockade");

        // add everything to the pane
        sortingBox.getChildren().addAll(unsortableButton, sortableButton);
        rightMenuBox.getChildren().addAll(unitBox, sortVisualisationLabel, sortVisualisationText, rightMenuPlayPause, blockadesLabel, sortingBox);
        rightMenuBox.setSpacing(10);
        BorderPane.setMargin(rightMenuBox, new Insets(12, 12, 12, 12));
        ((BorderPane) ((Group) GameRunTime.getScene().getRoot()).getChildren().get(0)).setRight(rightMenuBox);
    }

    /**
     * Handles the blockades appropriately
     *
     * @param button - the button to be handled
     * @param text   - the text the label will have
     */
    public void handleSort(Button button, String text) {
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                GameRunTime.getScene().setCursor(Cursor.DEFAULT);
                if (rightMenuBox.getChildren().contains(sortLabel))
                    rightMenuBox.getChildren().remove(sortLabel);
            }
        });
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sortLabel.setText(text);
                GameRunTime.getScene().setCursor(Cursor.HAND);
                if (!rightMenuBox.getChildren().contains(sortLabel))
                    rightMenuBox.getChildren().add(sortLabel);
            }
        });
    }
}
