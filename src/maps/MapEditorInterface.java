package maps;

import gui.GameInterface;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private Stage popUpStage;
    private Image yesImage, noImage, yesImageHover, noImageHover;

    //For the Map Editing scene itself
    private Scene scene;
    public static Pane rightMenuPane, rightMenuBox;
    private HBox rightMenuSaveBack;
    private VBox rightMenuText;
    public static Button saveButton, backButton, clearButton;
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

    public MapEditorInterface(Scene mapEditorScene, MapEditor mapEditor) {
        declareElements();
        this.mapEditor = mapEditor;
        loadFont();
        loadFont2();
        parser = new EditorParser(this.mapEditor);
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
        //Buttons
        saveButton = new Button();
        backButton = new Button();
        clearButton = new Button();
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


        //dunno how spacing works yet
        //rightMenuText.getChildren().addAll(fileNameLabel, fileNameBox, saveStatusLabel, saveStatus, instructionLabel,instructions);

        //rightMenuSaveBack.getChildren().addAll(saveButton, backButton);
        rightMenuSaveBack.setSpacing(0);

        //rightMenu.getChildren().addAll(rightMenuSaveBack, rightMenuText,clearButton);

        ((BorderPane) ((Group) scene.getRoot()).getChildren().get(0)).setRight(rightMenuPane);
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

        GridPane messagePanel = new GridPane();
        VBox layout = new VBox();
        HBox buttons = new HBox();

        Label message = new Label();
        Button yes = new Button();
        Button no = new Button();
        yesImage = ImageStore.overwriteYes;
        noImage = ImageStore.overwriteNo;
        yesImageHover = ImageStore.overwriteYesHovered;
        noImageHover = ImageStore.overwriteNoHovered;

        ButtonProperties b = new ButtonProperties();

        b.setButtonProperties(yes, "", 0, 0, event -> {
            parser.setOverwrite(true);
            popUpStage.hide();
        }, new ImageView(yesImage));
        //b.addHoverEffect2(yes,yesImageHover,yesImage,0,0);

        b.setButtonProperties(no, "", 0, 0, event -> {
            parser.setOverwrite(false);
            popUpStage.hide();
        }, new ImageView(noImage));
        //b.addHoverEffect2(no,noImageHover,noImage,0,0);

        buttons.getChildren().addAll(yes, no);
        layout.getChildren().addAll(message, buttons);

        message.setText("that map already exist, overwrite existing map?");

        messagePanel.getChildren().addAll(layout);
        Scene popUpScene = new Scene(messagePanel);

        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.hide();
        popUpStage.setOnCloseRequest(event -> {
            parser.setOverwrite(false);
        });
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }
}
