package gui;

import core.GameRunTime;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import menus.Menu;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.LabelProperties;
import stores.ImageStore;
import stores.LambdaStore;

import java.io.InputStream;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters on 26/02/16
 */
public class GameInterface {
    private final String SEPARATOR = "/";
    private Scene scene = GameRunTime.Instance().getScene();
    public static int bottomPaneHeight = 0;
    public static int rightPaneWidth = 424;

    public static Button playButton, pauseButton, unsortableButton, sortableButton;
    public static TextArea unitDescriptionText;
    public static Font bellotaFont;
    public static Pane unitTextPane, sortVisualisationPane, rightMenuPane, rightMenuBox;
    public static Label unitImage, namePaneLabel, searchPaneLabel, sortPaneLabel;

    private Label unitDescriptionLabel, sortVisualisationLabel, blockadesLabel, sortLabel;
    private Image playImage, playImageHovered, pauseImage, pauseImageHovered, unsortableImage, sortableImage;
    private ButtonProperties b;
    private LabelProperties l;
    private int initialPositionY = 50;
    private int heightSpacing = 30;

    public GameInterface() {
        loadFont();
        declareElements();
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
        bellotaFont = Font.loadFont(fontStream, 25);
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
        playButton = new Button();
        pauseButton = new Button();
        unsortableButton = new Button();
        sortableButton = new Button();
        b = new ButtonProperties();
        //Images
        playImage = ImageStore.playImage;
        playImageHovered = ImageStore.playImageHovered;
        pauseImage = ImageStore.pauseImage;
        pauseImageHovered = ImageStore.pauseImageHovered;
        unsortableImage = ImageStore.unsortableImage2;
        sortableImage = ImageStore.sortableImage2;

    }

    /**
     * Constructs the right Pane of the scene
     */
    public void rightPane() {
        //UNIT DESCRIPTION PANE
        unitDescriptionLabel.setText("Unit Description");
        unitDescriptionLabel.setFont(bellotaFont);
        unitDescriptionLabel.setLayoutX(rightPaneWidth / 2 - 175 / 2);
        unitDescriptionLabel.setLayoutY(initialPositionY);
        unitDescriptionLabel.setTextFill(Color.web("#FFE130"));

        unitDescriptionText.setPrefSize(300, 90);
        unitDescriptionText.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        unitDescriptionText.setLayoutY(initialPositionY + heightSpacing);
        unitDescriptionText.setEditable(false);

        // UNIT TEXT PANE
        unitTextPane.setPrefSize(300, 90);
        unitTextPane.setStyle("-fx-border-color: white");
        unitTextPane.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        unitTextPane.setLayoutY(initialPositionY + heightSpacing);

        // Sets the image and the text of the unit accordingly with the mouse pressed
        namePaneLabel.setText("");
        namePaneLabel.setFont(bellotaFont);
        namePaneLabel.setLayoutX(15);
        namePaneLabel.setLayoutY(5);
        namePaneLabel.setTextFill(Color.web("#FFE130"));

        searchPaneLabel.setText("");
        searchPaneLabel.setFont(bellotaFont);
        searchPaneLabel.setPrefSize(250, 25);
        searchPaneLabel.setLayoutX(15);
        searchPaneLabel.setLayoutY(5 + 25 + 2.5);
        searchPaneLabel.setTextFill(Color.web("#FFE130"));

        sortPaneLabel.setText("");
        sortPaneLabel.setFont(bellotaFont);
        sortPaneLabel.setPrefSize(250, 25);
        sortPaneLabel.setLayoutX(15);
        sortPaneLabel.setLayoutY(5 + 25 + 25 + 2.5);
        sortPaneLabel.setTextFill(Color.web("#FFE130"));

        unitImage.setText("");
        unitImage.setPrefSize(80, 80);
        unitImage.setLayoutX(215);
        unitImage.setLayoutY(90 / 2 - 80 / 2);
        unitTextPane.getChildren().addAll(namePaneLabel, searchPaneLabel, sortPaneLabel, unitImage);

        //SORT VISUALISATION PANE
        sortVisualisationLabel.setText("Sort Visualisation");
        sortVisualisationLabel.setFont(bellotaFont);
        sortVisualisationLabel.setLayoutX(rightPaneWidth / 2 - 197 / 2);
        sortVisualisationLabel.setLayoutY(initialPositionY + 2 * heightSpacing + 90);
        sortVisualisationLabel.setAlignment(Pos.CENTER);
        sortVisualisationLabel.setTextFill(Color.web("#FFE130"));

        sortVisualisationLabel.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                System.out.println("The new width" + newWidth);
            }
        });

        sortVisualisationPane.setPrefSize(300, 200);
        sortVisualisationPane.setStyle("-fx-border-color: white");
        sortVisualisationPane.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        sortVisualisationPane.setLayoutY(initialPositionY + 3 * heightSpacing + 90);

        // Set the properties for the play button
        b.setButtonProperties(playButton, "", rightPaneWidth / 2 - 50 - playImage.getWidth(), initialPositionY + 3.5 * heightSpacing + 290, ElementsHandler::handle, new ImageView(playImage));
        b.addHoverEffect(playButton, playImageHovered, playImage, rightPaneWidth / 2 - 50 - playImage.getWidth(), initialPositionY + 3.5 * heightSpacing + 290);
        // Set the properties for the pause button
        b.setButtonProperties(pauseButton, "", rightPaneWidth / 2 + 50 - pauseButton.getWidth(), initialPositionY + 3.5 * heightSpacing + 290, ElementsHandler::handle, new ImageView(pauseImage));
        b.addHoverEffect(pauseButton, pauseImageHovered, pauseImage, rightPaneWidth / 2 + 50 - pauseButton.getWidth(), initialPositionY + 3.5 * heightSpacing + 290);

        blockadesLabel.setText("Blockades");
        blockadesLabel.setPrefSize(120, 30);
        blockadesLabel.setLayoutX(rightPaneWidth / 2 - 120 / 2);
        blockadesLabel.setLayoutY(initialPositionY + 4 * heightSpacing + 340);
        blockadesLabel.setAlignment(Pos.CENTER);
        blockadesLabel.setFont(bellotaFont);
        blockadesLabel.setTextFill(Color.web("#FFE130"));

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

        // Set the properties for the sortable button
        b.setButtonProperties(sortableButton, "", rightPaneWidth - 150 - sortableImage.getWidth(), initialPositionY + 5 * heightSpacing + 350, ElementsHandler::handle, new ImageView(sortableImage));
        b.addHoverEffect(sortableButton, sortableImage, sortableImage, rightPaneWidth / 2 + 50 + sortableImage.getWidth(), initialPositionY + 5 * heightSpacing + 350);
        handleSort(sortableButton, "Sortable blockade");
        sortableButton.setOnMouseClicked(e -> LambdaStore.Instance().setBlockadeClickEvent(true));

        // setting background for the right pane
        BackgroundImage myBI = new BackgroundImage(ImageStore.paneBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        rightMenuPane.setBackground(new Background(myBI));
        rightMenuBox.getChildren().addAll(unitDescriptionLabel, unitTextPane, sortVisualisationLabel, sortVisualisationPane, blockadesLabel, unsortableButton, sortableButton, sortLabel);
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
    }
}
