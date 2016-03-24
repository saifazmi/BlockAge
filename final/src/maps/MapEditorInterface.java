package maps;

import gui.GameInterface;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menus.Menu;
import menus.MenuHandler;
import sceneElements.ButtonProperties;
import stores.ImageStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Hung Hoang; Contributors - Paul Popa and Saif Azmi
 * @version : 23/03/2016;
 *          <p>
 *          This class holds the information for the interface of the map editor
 * @date : 28/01/16
 */
public class MapEditorInterface {

    private static final Logger LOG = Logger.getLogger(MapEditorInterface.class.getName());

    private final String SEPARATOR = "/";

    // For popup Scene
    private static Stage popUpStage;
    private Image yesImage, noImage, yesImageHover, noImageHover;

    private Scene scene;

    // scene related GUI
    public static Pane rightMenuPane, rightMenuBox;
    private HBox rightMenuSaveBack;
    private VBox rightMenuText;
    private int rightPaneWidth = GameInterface.rightPaneWidth;
    private int initialPositionY = 50;
    private int heightSpacing = 30;

    // button and its images
    public static Button saveButton, backButton, clearButton, yes, no;
    private ButtonProperties b;
    private Image saveButtonImage, backButtonImage, saveButtonImageHover,
            backButtonImageHover, clearButtonImage, clearButtonImageHover;

    // text related things
    private TextArea instructions, fileNameBox, saveStatus;
    private Label instructionLabel, instructionTextLabel, saveStatusLabel, fileNameLabel;
    private Font bellotaFont, bellotaFontSmaller;

    // Logical classes
    private static EditorParser parser;
    private static MapEditor mapEditor;

    // Instance for singleton.
    private static MapEditorInterface instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the only game runtime to be created
     */
    public static MapEditorInterface Instance() {

        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {

        instance = null;
    }

    /**
     * Creates the Map editor interface by instantiating all the GUI elements and place them appropriately
     * This is also done for the pop-up stage.
     * Constructor also sets up association between the interface and the parser
     * (to call its functions when buttons are pressed on the interface)
     *
     * @param mapEditorScene The scene of the map editor
     * @param mapEditor      The Map Editor instance of which information about the editor can be accessed
     */
    public MapEditorInterface(Scene mapEditorScene, MapEditor mapEditor) {

        instance = this;

        declareElements();
        MapEditorInterface.mapEditor = mapEditor;
        loadFont();
        parser = new EditorParser(MapEditorInterface.mapEditor);
        scene = mapEditorScene;
        rightPane();
        setUpPopUp();
    }

    // GETTER methods

    /**
     * Gets the name of the map as in the text area
     *
     * @return The map name
     */
    public String getFileName() {

        return this.fileNameBox.getText();
    }

    /**
     * Gets the status of the saving process
     *
     * @return The TextArea containing the save status
     */
    public TextArea getSaveStatusBox() {

        return this.saveStatus;
    }

    /**
     * Returns the 'overwrite' pop-up stage
     *
     * @return The pop-up stage
     */
    public Stage getPopUpStage() {

        return MapEditorInterface.popUpStage;
    }

    /**
     * Sets up the pop-up window which asks user if they want to override an already existing map (name clash)
     * The pop-up will have 2 buttons for yes and no choice, and a message to display to the user that there is a name clash
     */
    private void setUpPopUp() {

        popUpStage = new Stage();
        int sceneWidth = 300;
        int sceneHeight = 200;
        Pane messagePanel = new Pane();

        BackgroundImage myBI = new BackgroundImage(
                ImageStore.yesNoBackground,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        messagePanel.setPrefSize(sceneWidth, sceneHeight);
        messagePanel.setBackground(new Background(myBI));
        Label message = new Label();
        ButtonProperties b = new ButtonProperties();

        // button for agreeing to overwrite
        b.setButtonProperties(
                yes,
                sceneWidth / 2 + 25 - noImage.getWidth() / 2, 125,
                MapEditorInterface::handle,
                new ImageView(yesImage)
        );

        b.addHoverEffect2(
                yes,
                yesImageHover,
                yesImage,
                sceneWidth / 2 + 25 - noImage.getWidth() / 2,
                125
        );

        // button for not agreeing to overwrite
        b.setButtonProperties(
                no,
                sceneWidth / 2 - 60 - noImage.getWidth() / 2,
                125,
                MapEditorInterface::handle,
                new ImageView(noImage)
        );

        b.addHoverEffect2(
                no,
                noImageHover,
                noImage,
                sceneWidth / 2 - 60 - noImage.getWidth() / 2,
                125
        );


        // position the text area
        message.setText("That map already exists. Overwrite existing map?");
        message.setWrapText(true);
        message.setFont(bellotaFontSmaller);
        message.setPrefSize(240, 50);
        message.setAlignment(Pos.CENTER);
        message.setLayoutX(300 / 2 - 200 / 2);
        message.setLayoutY(50);
        message.setTextFill(Color.web("#FFE130"));

        Scene popUpScene = new Scene(messagePanel);
        messagePanel.getChildren().addAll(no, yes, message);

        // set up the pop-up so that it acts as a pop-up
        popUpStage.setWidth(sceneWidth);
        popUpStage.setHeight(sceneHeight);
        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.hide();
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(event -> parser.setOverwrite(false));
    }

    /**
     * Loads the chosen font to use for labels
     */
    private void loadFont() {

        try (InputStream fontStream = MapEditorInterface.class.getResourceAsStream(
                SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf")) {

            bellotaFont = Font.loadFont(fontStream, 28);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No font found");
            LOG.log(Level.SEVERE, e.toString(), e);
        }

        try (InputStream fontStream = MapEditorInterface.class.getResourceAsStream(
                SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf")) {

            bellotaFontSmaller = Font.loadFont(fontStream, 18);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No font found");
            LOG.log(Level.SEVERE, e.toString(), e);
        }

    }

    /**
     * Declares all the elements of the UI on the scene
     * This includes all the buttons, labels, text areas and panes
     */
    private void declareElements() {

        // HBoxes & VBoxes
        rightMenuSaveBack = new HBox();
        rightMenuText = new VBox();
        rightMenuPane = new Pane();
        rightMenuBox = new Pane();

        // Images
        saveButtonImage = ImageStore.saveMapImage;
        saveButtonImageHover = ImageStore.saveMapImageHovered;
        backButtonImage = ImageStore.backFromEditor;
        backButtonImageHover = ImageStore.backFromEditorHovered;
        clearButtonImage = ImageStore.clear;
        clearButtonImageHover = ImageStore.clearHovered;
        yesImage = ImageStore.overwriteYes;
        noImage = ImageStore.overwriteNo;
        yesImageHover = ImageStore.overwriteYesHovered;
        noImageHover = ImageStore.overwriteNoHovered;

        //Buttons
        saveButton = new Button();
        backButton = new Button();
        clearButton = new Button();
        yes = new Button();
        no = new Button();

        //Text areas
        instructions = new TextArea();
        fileNameBox = new TextArea();
        saveStatus = new TextArea();

        //Label
        instructionLabel = new Label();
        instructionTextLabel = new Label();
        saveStatusLabel = new Label();
        fileNameLabel = new Label();
        b = new ButtonProperties();
    }

    /**
     * Constructs the right pane
     * Gives all the buttons its properties, including click event handler, positioning and image
     * Also sets the background for the right pane and place the appropriate labels in the correct positioning
     */
    private void rightPane() {


        // Adding buttons properties for save button

        b.setButtonProperties(
                saveButton,
                rightPaneWidth / 2 - saveButtonImage.getWidth() / 2 - 80,
                initialPositionY,
                MapEditorInterface::handle,
                new ImageView(saveButtonImage)
        );

        b.addHoverEffect2(
                saveButton,
                saveButtonImageHover,
                saveButtonImage,
                rightPaneWidth / 2 - saveButtonImage.getWidth() / 2 - 80,
                initialPositionY
        );

        // Adding buttons properties for back button

        b.setButtonProperties(
                backButton,
                rightPaneWidth / 2 - backButtonImage.getWidth() / 2 + 80,
                initialPositionY,
                MapEditorInterface::handle,
                new ImageView(backButtonImage)
        );

        b.addHoverEffect2(
                backButton,
                backButtonImageHover,
                backButtonImage,
                rightPaneWidth / 2 - backButtonImage.getWidth() / 2 + 80,
                initialPositionY
        );


        // Adding buttons properties for clear button

        b.setButtonProperties(
                clearButton,
                rightPaneWidth / 2 - clearButtonImage.getWidth() / 2,
                Menu.HEIGHT - 100,
                MapEditorInterface::handle,
                new ImageView(clearButtonImage)
        );

        b.addHoverEffect2(
                clearButton,
                clearButtonImageHover,
                clearButtonImage,
                rightPaneWidth / 2 - clearButtonImage.getWidth() / 2,
                Menu.HEIGHT - 100
        );

        BackgroundImage myBI = new BackgroundImage(
                ImageStore.paneBackground,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );


        rightMenuPane.setPrefSize(GameInterface.rightPaneWidth, Menu.HEIGHT);
        rightMenuPane.setBackground(new Background(myBI));

        instructionTextLabel.setFont(bellotaFont);
        instructionTextLabel.setText(
                "Click on rectangle node to create blockades. " +
                        "Enter the map's name and press save to save your current configuration or back to cancel."
        );

        // instruction label set-up
        instructionTextLabel.setTextFill(Color.web("#FFE130"));
        instructionTextLabel.setPrefSize(300, 200);
        instructionTextLabel.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        instructionTextLabel.setLayoutY(initialPositionY + 4 * heightSpacing);
        instructionTextLabel.setWrapText(true);

        // File name and saving label set-up
        fileNameLabel.setFont(bellotaFont);
        fileNameLabel.setText("File Name");
        fileNameLabel.setTextFill(Color.web("#FFE130"));
        fileNameLabel.setPrefSize(150, 30);
        fileNameLabel.setLayoutX(61);
        fileNameLabel.setLayoutY(initialPositionY + 5 * heightSpacing + 200);

        fileNameBox.setPrefSize(300, 5);
        fileNameBox.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        fileNameBox.setStyle("-fx-background-color: #FFE130;");
        fileNameBox.setFont(bellotaFontSmaller);
        fileNameBox.setLayoutY(initialPositionY + 5 * heightSpacing + 230);

        // Save status label set up
        saveStatusLabel.setFont(bellotaFont);
        saveStatusLabel.setText("Save Status");
        saveStatusLabel.setAlignment(Pos.CENTER_RIGHT);
        saveStatusLabel.setTextFill(Color.web("#FFE130"));
        saveStatusLabel.setPrefSize(150, 30);
        saveStatusLabel.setLayoutX(47);
        saveStatusLabel.setLayoutY(initialPositionY + 8 * heightSpacing + 200);

        // save status text area set up
        saveStatus.setEditable(false);
        saveStatus.setPrefSize(300, 5);
        saveStatus.setStyle("-fx-background-color: #FFE130;");
        saveStatus.setFont(bellotaFontSmaller);
        saveStatus.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        saveStatus.setLayoutY(initialPositionY + 8 * heightSpacing + 230);

        // add all buttons, labels and text area to the right pane
        rightMenuBox.getChildren().addAll(
                saveButton,
                backButton,
                clearButton,
                instructionTextLabel,
                fileNameLabel,
                fileNameBox,
                saveStatusLabel,
                saveStatus
        );

        rightMenuPane.getChildren().add(rightMenuBox);

        ((BorderPane) scene.getRoot()).setRight(rightMenuPane);
    }

    /**
     * Handles events invoked by the Map Editing interface
     * These includes saving the map, clearing the map and exiting the map
     * To make the program more concise, also includes handling event of click yes or no for overwriting map files
     *
     * @param event the click event
     */
    public static void handle(ActionEvent event) {

        if (event.getSource() == MapEditorInterface.saveButton) {
            parser.saveToUserFile();
        }

        if (event.getSource() == MapEditorInterface.clearButton) {
            mapEditor.clearNodes();
        }

        if (event.getSource() == MapEditorInterface.backButton) {

            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
            mapEditor.clearNodes();
        }

        if (event.getSource() == MapEditorInterface.no) {

            parser.setOverwrite(false);
            popUpStage.hide();
        }

        if (event.getSource() == MapEditorInterface.yes) {

            parser.setOverwrite(true);
            popUpStage.hide();
        }
    }
}
