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

import java.io.InputStream;

/**
 * Created by hung on 05/03/16.
 */
public class MapEditorInterface {

    //for popup Scene
    private static Stage popUpStage;
    private Image yesImage, noImage, yesImageHover, noImageHover;

    //For the Map Editing scene itself
    private Scene scene;
    public static Pane rightMenuPane, rightMenuBox;
    private HBox rightMenuSaveBack;
    private VBox rightMenuText;
    public static Button saveButton, backButton, clearButton, yes, no;
    private ButtonProperties b;
    private Image saveButtonImage, backButtonImage, saveButtonImageHover, backButtonImageHover, clearButtonImage, clearButtonImageHover;
    private TextArea instructions, fileNameBox, saveStatus;
    private Label instructionLabel, instructionTextLabel, saveStatusLabel, fileNameLabel;
    private static EditorParser parser;
    private Font bellotaFont, bellotaFontSmaller;
    private String SEPARATOR = "/";
    private static MapEditor mapEditor;
    private int rightPaneWidth = GameInterface.rightPaneWidth;
    private int initialPositionY = 50;
    private int heightSpacing = 30;

    private static MapEditorInterface instance = null;

    /**
     * Implements Singleton for this class (Only one can exist)
     *
     * @return the only game runtime to be created
     */
    public static MapEditorInterface Instance() {

        return instance;
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    public MapEditorInterface(Scene mapEditorScene, MapEditor mapEditor) {
        instance = this;
        declareElements();
        MapEditorInterface.mapEditor = mapEditor;
        loadFont();
        loadFont2();
        parser = new EditorParser(MapEditorInterface.mapEditor);
        scene = mapEditorScene;
        rightPane();
        setUpPopUp();
    }


    private void loadFont() {
        InputStream fontStream = MapEditorInterface.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFont = Font.loadFont(fontStream, 28);
    }

    private void loadFont2() {
        InputStream fontStream = MapEditorInterface.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }
        bellotaFontSmaller = Font.loadFont(fontStream, 18);
    }

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

    private void rightPane() {
        // Adding buttons properties
        b.setButtonProperties(saveButton, "", rightPaneWidth / 2 - saveButtonImage.getWidth() / 2 - 80, initialPositionY, e -> handle(e), new ImageView(saveButtonImage));
        b.addHoverEffect2(saveButton, saveButtonImageHover, saveButtonImage, rightPaneWidth / 2 - saveButtonImage.getWidth() / 2 - 80, initialPositionY);

        b.setButtonProperties(backButton, "", rightPaneWidth / 2 - backButtonImage.getWidth() / 2 + 80, initialPositionY, e -> handle(e), new ImageView(backButtonImage));
        b.addHoverEffect2(backButton, backButtonImageHover, backButtonImage, rightPaneWidth / 2 - backButtonImage.getWidth() / 2 + 80, initialPositionY);

        b.setButtonProperties(clearButton, "", rightPaneWidth / 2 - clearButtonImage.getWidth() / 2, Menu.HEIGHT - 100, e -> handle(e), new ImageView(clearButtonImage));
        b.addHoverEffect2(clearButton, clearButtonImageHover, clearButtonImage, rightPaneWidth / 2 - clearButtonImage.getWidth() / 2, Menu.HEIGHT - 100);

        BackgroundImage myBI = new BackgroundImage(ImageStore.paneBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        rightMenuPane.setPrefSize(GameInterface.rightPaneWidth, Menu.HEIGHT);
        rightMenuPane.setBackground(new Background(myBI));

        instructionTextLabel.setFont(bellotaFont);
        instructionTextLabel.setText("Click on rectangle node to create blockades. Enter the map's name and press save to save your current configuration or back to cancel.");
        instructionTextLabel.setTextFill(Color.web("#FFE130"));
        instructionTextLabel.setPrefSize(300, 200);
        instructionTextLabel.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        instructionTextLabel.setLayoutY(initialPositionY + 4 * heightSpacing);
        instructionTextLabel.setWrapText(true);

        // File name and saving
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

        saveStatusLabel.setFont(bellotaFont);
        saveStatusLabel.setText("Save Status");
        saveStatusLabel.setAlignment(Pos.CENTER_RIGHT);
        saveStatusLabel.setTextFill(Color.web("#FFE130"));
        saveStatusLabel.setPrefSize(150, 30);
        saveStatusLabel.setLayoutX(47);
        saveStatusLabel.setLayoutY(initialPositionY + 8 * heightSpacing + 200);

        saveStatus.setEditable(false);
        saveStatus.setPrefSize(300, 5);
        saveStatus.setStyle("-fx-background-color: #FFE130;");
        saveStatus.setFont(bellotaFontSmaller);
        saveStatus.setLayoutX(rightPaneWidth / 2 - 300 / 2);
        saveStatus.setLayoutY(initialPositionY + 8 * heightSpacing + 230);

        rightMenuBox.getChildren().addAll(saveButton, backButton, clearButton, instructionTextLabel, fileNameLabel, fileNameBox, saveStatusLabel, saveStatus);
        rightMenuPane.getChildren().add(rightMenuBox);

        ((BorderPane) scene.getRoot()).setRight(rightMenuPane);
    }

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
            //Renderer.delete();
            //MapEditorInterface.delete();
            //MapEditor.delete();
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

    public String getFileName() {
        return fileNameBox.getText();
    }

    public TextArea getSaveStatusBox() {
        return saveStatus;
    }

    private void setUpPopUp() {
        popUpStage = new Stage();
        int sceneWidth = 300;
        int sceneHeight = 200;
        Pane messagePanel = new Pane();
        BackgroundImage myBI = new BackgroundImage(ImageStore.yesNoBackground, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        messagePanel.setPrefSize(sceneWidth, sceneHeight);
        messagePanel.setBackground(new Background(myBI));
        Label message = new Label();
        ButtonProperties b = new ButtonProperties();

        b.setButtonProperties(yes, "", sceneWidth / 2 + 25 - noImage.getWidth() / 2, 125, e -> handle(e), new ImageView(yesImage));
        b.addHoverEffect2(yes, yesImageHover, yesImage, sceneWidth / 2 + 25 - noImage.getWidth() / 2, 125);

        b.setButtonProperties(no, "", sceneWidth / 2 - 60 - noImage.getWidth() / 2, 125, e -> handle(e), new ImageView(noImage));
        b.addHoverEffect2(no, noImageHover, noImage, sceneWidth / 2 - 60 - noImage.getWidth() / 2, 125);

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

        popUpStage.setWidth(sceneWidth);
        popUpStage.setHeight(sceneHeight);
        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.hide();
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(event -> {
            parser.setOverwrite(false);
        });
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }
}
