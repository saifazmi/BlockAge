package core;

import java.io.File;
import java.io.InputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
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
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

public class GameInterface {
    public static Button fileButton, fileExitButton, helpButton, playButton, pauseButton, unsortableButton, sortableButton;
    public static TextArea unitDescriptionText, textInfoText, algorithmVisualisationText;
    public static int topPaneHeight = 35;
    public static int rightPaneWidth = 224;

    private Font bellotaFont;
    private Label unitDescriptionLabel, textInfoLabel, algorithmVisualisationLabel, blockadesLabel, sortLabel;
    private HBox topMenuBox, rightMenuPlayPause, sortingBox;
    private VBox rightMenuBox;
    private Image fileImage, fileImageHovered, helpImage, helpImageHovered, unitDescriptionImage, textInfoImage, playImage, playImageHovered, pauseImage, pauseImageHovered, unsortableImage, sortableImage;
    private ButtonProperties b;
    private static String SEPARATOR = File.separator;

    public GameInterface() {
    	loadFont();
        declareElements();
        topPane();
        rightPane();
        bottomPane();
    }
    
    public void loadFont() {
    	InputStream fontStream = GameInterface.class.getResourceAsStream(".." + SEPARATOR + "sprites" + SEPARATOR + "Bellota-Bold.otf");
    	if(fontStream == null) {
    		System.out.println("No font at that path");
    	}
    	bellotaFont = Font.loadFont(fontStream, 17);
    }

    /**
     * Declare the elements for the scene
     */
    public void declareElements() {
        topMenuBox = new HBox();
        rightMenuPlayPause = new HBox();
        sortingBox = new HBox();

        rightMenuBox = new VBox();

        unitDescriptionLabel = new Label();
        textInfoLabel = new Label();
        algorithmVisualisationLabel = new Label();
        blockadesLabel = new Label();
        sortLabel = new Label();

        unitDescriptionText = new TextArea();
        textInfoText = new TextArea();
        algorithmVisualisationText = new TextArea();

        fileButton = new Button();
        helpButton = new Button();
        playButton = new Button();
        pauseButton = new Button();
        unsortableButton = new Button();
        sortableButton = new Button();
        b = new ButtonProperties();

        fileImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "file_button.png");
        fileImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "file_button_hovered.png");

        helpImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "help_button.png");
        helpImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "help_button_hovered.png");

        playImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button.png");
        playImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button_hovered.png");

        pauseImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button.png");
        pauseImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button_hovered.png");
        
        unsortableImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, false, false);
        sortableImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 2.0.png", 55, 55, false, false);
        
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
    	unitDescriptionLabel.setText("Unit Description");
    	unitDescriptionLabel.setFont(bellotaFont);
        unitDescriptionText.setPrefSize(200, 100);
        unitDescriptionText.setEditable(false);

        textInfoLabel.setText("Text Info");
        textInfoLabel.setFont(bellotaFont);
        textInfoText.setPrefSize(200, 100);
        textInfoText.setEditable(false);
        
        algorithmVisualisationLabel.setText("Algorithm Visualisation");
        algorithmVisualisationLabel.setFont(bellotaFont);
        algorithmVisualisationText.setPrefSize(200, 175);
        algorithmVisualisationText.setEditable(false);
        
        blockadesLabel.setText("Blockades");
        blockadesLabel.setFont(bellotaFont);
        
        sortLabel.setText("Error");
        sortLabel.setFont(bellotaFont);

        // Set the properties for the play button
        b.setButtonProperties(playButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(playImage));
        b.addHoverEffect(playButton, playImageHovered, playImage, 0, 0);

        // Set the properties for the pause button
        b.setButtonProperties(pauseButton, "", 0, 0,
                e -> ElementsHandler.handle(e), new ImageView(pauseImage));
        b.addHoverEffect(pauseButton, pauseImageHovered, pauseImage, 0, 0);
        
        // Set the properties for the unsortable button
        b.setButtonProperties(unsortableButton, "", 0, 0, 
        		e -> ElementsHandler.handle(e), new ImageView(unsortableImage));
        b.addHoverEffect(unsortableButton, unsortableImage, unsortableImage, 0, 0);
        handleSort(unsortableButton, "Unsortable blockade");
        
        // Set the properties for the sortable button
        b.setButtonProperties(sortableButton, "", 0, 0, 
        		e -> ElementsHandler.handle(e), new ImageView(sortableImage));
        b.addHoverEffect(sortableButton, sortableImage, sortableImage, 0, 0);
        handleSort(sortableButton, "Sortable blockade");

        // add everything to the pane
        rightMenuPlayPause.getChildren().addAll(playButton, pauseButton);
        sortingBox.getChildren().addAll(unsortableButton, sortableButton);

        rightMenuBox.getChildren().addAll(unitDescriptionLabel, unitDescriptionText, textInfoLabel, textInfoText, algorithmVisualisationLabel, algorithmVisualisationText, blockadesLabel, sortingBox); //rightMenuPlayPause);
        rightMenuBox.setSpacing(10);
        BorderPane.setMargin(rightMenuBox, new Insets(12, 12, 12, 12));
        int insetsWidth = 12 + 12;
        ((BorderPane) ((Group) GameRunTime.getScene().getRoot()).getChildren().get(0)).setRight(rightMenuBox);
    }
    
    /**
     * Handles the blockades appropriately
     * @param button - the button to be handled
     * @param text - the text the label will have
     */
    public void handleSort(Button button, String text) {
    	 button.setOnMouseExited(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
             	GameRunTime.getScene().setCursor(Cursor.DEFAULT);
             	if(rightMenuBox.getChildren().contains(sortLabel))
             		rightMenuBox.getChildren().remove(sortLabel);
             }
         });
    	 button.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
             	sortLabel.setText(text);
             	GameRunTime.getScene().setCursor(Cursor.HAND);
             	if(!rightMenuBox.getChildren().contains(sortLabel))
             		rightMenuBox.getChildren().add(sortLabel);
             }
         });
    }
}
