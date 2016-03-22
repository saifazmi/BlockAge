package gui;

import core.CoreEngine;
import core.GameRunTime;
import entity.SortableBlockade;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import menus.Menu;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.LabelProperties;
import sorts.visual.SortVisual;
import stores.ImageStore;
import stores.LambdaStore;

import java.io.InputStream;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 * 
 * This class contains the right pane that can be seen in-game. All the elements on the pane
 * are appropriately positioned and coloured. The pane is formed by the unit description, key bindings,
 * tutorial, sort visualisation as well as blockades (sortable/unsortable) and score.
 */
public class GameInterface {
	
    private final String SEPARATOR = "/";
    private Scene scene = GameRunTime.Instance().getScene();
    public static int bottomPaneHeight = 0; // The height of the right Pane, manually set
    public static int rightPaneWidth = 424; // The width of the right Pane, manually set

    // All elements that will be placed on the right pane
    public static Button unsortableButton, sortableButton;
    public static TextArea unitDescriptionText;
    public static Font bellotaFont, bellotaFontBigger;
    public static Pane unitTextPane, sortVisualisationPane, rightMenuPane, rightMenuBox;
    public static Label unitDescriptionLabel, unitImage, namePaneLabel, searchPaneLabel, sortPaneLabel, sortableLimitLabel, unsortableLimitLabel, scoreLabel, sortVisualisationLabel;

    private Label blockadesLabel, sortLabel;
    private Image unsortableImage, sortableImage;
    private ButtonProperties b;
    
    private int initialPositionY = 50; // the initial position of the first element of the pane
    private int heightSpacing = 30; // the spacing that will be added between elements of the pane
    
    public SortVisual sortVisual = null; // the visual which will be placed on the pane
    public SortableBlockade sortableBlockade;

    private static GameInterface instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the only game interface to be created
     */
    public static GameInterface Instance() {
    	
        if (instance == null) {
            instance = new GameInterface();
        }
        return instance;
    }
    /**
     * Deleting the instance of this when called
     * 
     * @return if the instance was deleted or not
     */
    public static boolean delete() {
    	
        instance = null;
        return true;
    }
    /**
     * Constructs the pane with all the elements and events
     */
    private GameInterface() {
    	
        loadFont();
        declareElements();
        toggle();
        rightPane();
    }

    /**
     * Loads the font for labels/buttons
     */
    public void loadFont() {
    	
        InputStream fontStream = GameInterface.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFont = Font.loadFont(fontStream, 23);

        InputStream fontStream1 = GameInterface.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFontBigger = Font.loadFont(fontStream1, 50);
    }

    /**
     * Declare the elements for the scene
     */
    public void declareElements() {
        //Labels
        unitImage = new Label();
        namePaneLabel = new Label();
        searchPaneLabel = new Label();
        sortPaneLabel = new Label();
        sortableLimitLabel = new Label();
        unsortableLimitLabel = new Label();
        scoreLabel = new Label();
        unitDescriptionLabel = new Label();
        sortVisualisationLabel = new Label();
        blockadesLabel = new Label();
        sortLabel = new Label();
        //Text Areas
        unitDescriptionText = new TextArea();
        //Panes
        unitTextPane = new Pane();
        sortVisualisationPane = new Pane();
        rightMenuPane = new Pane();
        rightMenuBox = new Pane();
        //Buttons
        unsortableButton = new Button();
        sortableButton = new Button();
        b = new ButtonProperties();
        //Images
        unsortableImage = ImageStore.unsortableImage2;
        sortableImage = ImageStore.sortableImage2;
    }

    /**
     * Constructs the right Pane of the scene where all the elements
     * will be placed on. The elements will be appropriately positioned and coloured
     */
    public void rightPane() {
        //UNIT DESCRIPTION PANE
        unitDescriptionLabel.setFont(bellotaFont);
        unitDescriptionLabel.setLayoutX(rightPaneWidth / 2 - 131.25 / 2);
        unitDescriptionLabel.setLayoutY(initialPositionY);
        unitDescriptionLabel.setTextFill(Color.web("#FFE130"));

        // UNIT TEXT PANE
        unitTextPane.setPrefSize(300, 90);
        unitTextPane.setStyle("-fx-border-color: white");
        unitTextPane.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        unitTextPane.setLayoutY(initialPositionY + heightSpacing);

        // Sets the image and the text of the unit accordingly with the mouse pressed
        namePaneLabel.setFont(bellotaFont);
        namePaneLabel.setLayoutX(15);
        namePaneLabel.setLayoutY(5);
        namePaneLabel.setTextFill(Color.web("#FFE130"));

        searchPaneLabel.setFont(bellotaFont);
        searchPaneLabel.setPrefSize(250, 25);
        searchPaneLabel.setLayoutX(15);
        searchPaneLabel.setLayoutY(5 + 25 + 2.5);
        searchPaneLabel.setTextFill(Color.web("#FFE130"));

        sortPaneLabel.setFont(bellotaFont);
        sortPaneLabel.setPrefSize(325, 25);
        sortPaneLabel.setLayoutX(15);
        sortPaneLabel.setLayoutY(5 + 25 + 25 + 2.5);
        sortPaneLabel.setTextFill(Color.web("#FFE130"));

        unitImage.setText("");
        unitImage.setPrefSize(80, 80);
        unitImage.setLayoutX(215);
        unitImage.setLayoutY(90 / 2 - 80 / 2);
        unitTextPane.getChildren().addAll(namePaneLabel, searchPaneLabel, sortPaneLabel, unitImage);

        //SORT VISUALISATION LABEL
        sortVisualisationLabel.setText("Sort Visualisation");
        sortVisualisationLabel.setFont(bellotaFont);
        sortVisualisationLabel.setLayoutX(rightPaneWidth / 2 - 197 / 2);
        sortVisualisationLabel.setLayoutY(initialPositionY + 2 * heightSpacing + 90);
        sortVisualisationLabel.setAlignment(Pos.CENTER);
        sortVisualisationLabel.setTextFill(Color.web("#FFE130"));

        //SORT VISUALISATION PANE
        sortVisualisationPane.setPrefSize(300, 260);
        sortVisualisationPane.setStyle("-fx-border-color: white");
        sortVisualisationPane.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        sortVisualisationPane.setLayoutY(initialPositionY + 3 * heightSpacing + 90);
        
        //BLOCKADES LABEL
        blockadesLabel.setText("Blockades");
        blockadesLabel.setPrefSize(120, 30);
        blockadesLabel.setLayoutX(rightPaneWidth / 2 - 120 / 2);
        blockadesLabel.setLayoutY(initialPositionY + 4 * heightSpacing + 340);
        blockadesLabel.setAlignment(Pos.CENTER);
        blockadesLabel.setFont(bellotaFont);
        blockadesLabel.setTextFill(Color.web("#FFE130"));

        //SORT LABEL
        sortLabel.setText("");
        sortLabel.setPrefSize(240, 30);
        sortLabel.setLayoutX(rightPaneWidth / 2 - 240 / 2);
        sortLabel.setLayoutY(initialPositionY + 7 * heightSpacing + 370);
        sortLabel.setAlignment(Pos.CENTER);
        sortLabel.setFont(bellotaFont);
        sortLabel.setTextFill(Color.web("#FFE130"));

        // Set the properties for the unsortable button
        b.setButtonProperties(unsortableButton, "", 135, initialPositionY + 5 * heightSpacing + 350, ElementsHandler::handle, new ImageView(unsortableImage));
        b.addHoverEffect(unsortableButton, unsortableImage, unsortableImage, rightPaneWidth / 2 - 50 - unsortableImage.getWidth(), initialPositionY + 5 * heightSpacing + 350);
        handleSort(unsortableButton, "Unsortable blockade");
        unsortableButton.setOnMouseClicked(e -> LambdaStore.Instance().setBlockadeClickEvent(false));

        // Set the properties for the unsortable limit label
        unsortableLimitLabel.setText(String.valueOf(CoreEngine.Instance().getUnbreakableBlockadesLimit()));
        unsortableLimitLabel.setFont(bellotaFontBigger);
        unsortableLimitLabel.setLayoutX(90);
        unsortableLimitLabel.setLayoutY(initialPositionY + 5 * heightSpacing + 350);
        unsortableLimitLabel.setTextFill(Color.web("#FFE130"));

        // Set the properties for the sortable button
        b.setButtonProperties(sortableButton, "", rightPaneWidth - 150 - sortableImage.getWidth(), initialPositionY + 5 * heightSpacing + 350, ElementsHandler::handle, new ImageView(sortableImage));
        b.addHoverEffect(sortableButton, sortableImage, sortableImage, rightPaneWidth / 2 + 50 + sortableImage.getWidth(), initialPositionY + 5 * heightSpacing + 350);
        handleSort(sortableButton, "Sortable blockade");
        sortableButton.setOnMouseClicked(e -> LambdaStore.Instance().setBlockadeClickEvent(true));

        // Set the properties for the sortable limit label
        sortableLimitLabel.setText(String.valueOf(CoreEngine.Instance().getBreakableBlockadesLimit()));
        sortableLimitLabel.setFont(bellotaFontBigger);
        sortableLimitLabel.setLayoutX(rightPaneWidth - 150 + 15);
        sortableLimitLabel.setLayoutY(initialPositionY + 5 * heightSpacing + 350);
        sortableLimitLabel.setTextFill(Color.web("#FFE130"));

        // Set the properties for the score label
        scoreLabel.setText("Score: " + String.format("%.2f", CoreEngine.Instance().getScore().getScore()));
        scoreLabel.setFont(bellotaFontBigger);
        scoreLabel.setPrefSize(300, 30);
        scoreLabel.setLayoutX(rightPaneWidth / 2 - 250 / 2);
        scoreLabel.setLayoutY(initialPositionY + 600);
        scoreLabel.setTextFill(Color.web("#FFE130"));

        // setting background for the right pane
        BackgroundImage myBI = new BackgroundImage(ImageStore.paneBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        rightMenuPane.setBackground(new Background(myBI));
        rightMenuBox.getChildren().addAll(unitDescriptionLabel, unitTextPane, sortVisualisationLabel, sortVisualisationPane, blockadesLabel, unsortableButton, sortableButton, sortLabel, unsortableLimitLabel, sortableLimitLabel, scoreLabel);
        rightMenuPane.getChildren().add(rightMenuBox);
        rightMenuPane.setPrefSize(rightPaneWidth, Menu.HEIGHT);
        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenuPane);
    }

    /**
     * Handles the blockades appropriately
     *
     * @param button - the button to be handled
     * @param text   - the text the label will have
     */
    public void handleSort(Button button, String text) {
        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
            scene.setCursor(Cursor.DEFAULT);
            if (rightMenuBox.getChildren().contains(sortLabel))
                rightMenuBox.getChildren().remove(sortLabel);
        });
        button.setOnMouseEntered(event -> {
            button.setScaleX(1.25);
            button.setScaleY(1.25);
            sortLabel.setText(text);
            scene.setCursor(Cursor.HAND);
            if (!rightMenuBox.getChildren().contains(sortLabel))
                rightMenuBox.getChildren().add(sortLabel);
        });
        
        //Initial pane displayed on the top right. It contains the key bindings
        unitDescriptionLabel.setText("Key Bindings");
        namePaneLabel.setText("R-Show route");
        searchPaneLabel.setText("S-Unselect unit");
        sortPaneLabel.setText("B-Unsortable blockade");
    }

    /**
     * Updates the current score as well as the limits for the breakable and unbreakable blockades
     */
    public static void update() {
        scoreLabel.setText("Score: " + String.format("%.2f", CoreEngine.Instance().getScore().getScore()));
        unsortableLimitLabel.setText(String.valueOf(CoreEngine.Instance().getUnbreakableBlockadesLimit()));
        sortableLimitLabel.setText(String.valueOf(CoreEngine.Instance().getBreakableBlockadesLimit()));
    }

    /**
     * Sets up the key binding pane with the keys that can be used in game
     */
    public static void toggle() {

        // The labels will be updated every 3 seconds
        Timeline update = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(unitDescriptionLabel != null && unitDescriptionLabel.getText().equals("Key Bindings")) {
            		if(namePaneLabel.getText().equals("R-Show route")) {
            			namePaneLabel.setText("Shift+R-Show visualisation");
            	        searchPaneLabel.setText("Shift+SPACE-Pause game");
            	        sortPaneLabel.setText("Shift+B-Sortable blockade");
            		}
            		else {
            			namePaneLabel.setText("R-Show route");
            	        searchPaneLabel.setText("S-Unselect unit");
            	        sortPaneLabel.setText("B-Unsortable blockade");
            		}
            	}
            	else {
            		System.out.println("Do nothing");
            	}
            }
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.play();
    }
}
