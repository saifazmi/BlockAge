package menus;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.LabelProperties;

import java.io.File;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 */
public class OptionsMenu implements Menu {
    public static Button yesButtonSearch, yesButtonSound, noButtonSearch, noButtonSound, yesButtonB, noButtonB, backButton = null;
    public static Image showSearchImage, soundImage, blockadeImage, onImage, onImageHovered, offImage, offImageHovered, backImage, backImageHovered = null;
    public static int spaceBetweenText = 100;
    public static int spaceBetweenImgH = 50;
    public static Pane optionsMenuPane = null;

    private Scene optionsMenuScene = null;
    private Label hintsLabel, soundLabel, blockadeLabel = null;
    private LabelProperties l = null;
    private ButtonProperties b = null;

    private static String SEPARATOR = File.separator;

    public OptionsMenu() {
        initialiseScene();
    }

    /**
     * Declaring the elements which will be placed on the scene
     */
    public void declareElements() {
        yesButtonSearch = new Button();
        noButtonSearch = new Button();
        yesButtonSound = new Button();
        noButtonSound = new Button();
        yesButtonB = new Button();
        noButtonB = new Button();
        backButton = new Button();

        hintsLabel = new Label();
        soundLabel = new Label();
        blockadeLabel = new Label();

        optionsMenuPane = new Pane();
        l = new LabelProperties();
        b = new ButtonProperties();

        onImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "On.png", 395, 55, true, true);
        onImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "OnSel.png", 395, 55, true, true);

        offImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Off.png", 395, 55, true, true);
        offImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "OffSel.png", 395, 55, true, true);

        showSearchImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Show-Search.png", 395, 183, true, true);
        soundImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Sound.png", 395, 55, true, true);
        blockadeImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "No-Start-Blockades.png", 550, 0, true, true);

        backImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Back.png", 395, 55, true, true);
        backImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "BackSel.png", 395, 55, true, true);
    }

    /**
     * Initialise the scene for the options menu and what will be inside the scene
     */
    public void initialiseScene() {
        declareElements();

        // Set properties for the on/off SEARCH button
        b.setButtonProperties(noButtonSearch, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3,
                e -> ElementsHandler.handle(e), new ImageView(offImage));
        b.addHoverEffect(noButtonSearch, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3);

        // Set properties for the on/off SOUND button
        b.setButtonProperties(noButtonSound, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH,
                e -> ElementsHandler.handle(e), new ImageView(offImage));
        b.addHoverEffect(noButtonSound, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH);

        // Set properties for the on/off BLOCKADES button
        b.setButtonProperties(noButtonB, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                e -> ElementsHandler.handle(e), new ImageView(offImage));
        b.addHoverEffect(noButtonB, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH);

        // Set properties for the back button
        b.setButtonProperties(backButton, "", Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.2,
                e -> ElementsHandler.handle(e), new ImageView(backImage));
        b.addHoverEffect(backButton, backImageHovered, backImage, Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.2);

        // Set labels with images
        l.setLabelProperties(hintsLabel, "", Menu.WIDTH / 1.75 - showSearchImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3, new ImageView(showSearchImage));
        l.setLabelProperties(soundLabel, "", Menu.WIDTH / 1.75 - soundImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH, new ImageView(soundImage));
        l.setLabelProperties(blockadeLabel, "", Menu.WIDTH / 1.75 - blockadeImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH, new ImageView(blockadeImage));

        // To do sound configurations for button as well
        optionsMenuPane.getChildren().addAll(hintsLabel, soundLabel, blockadeLabel, noButtonSearch, noButtonSound, noButtonB, backButton);
        BackgroundImage myBI = new BackgroundImage(new Image(SEPARATOR + "sprites" + SEPARATOR + "OptionsMenu.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        optionsMenuPane.setBackground(new Background(myBI));
        optionsMenuPane.setPrefWidth(Menu.WIDTH);
        optionsMenuPane.setPrefHeight(Menu.HEIGHT);
        final Group group = new Group(optionsMenuPane);

        optionsMenuScene = new Scene(group, Menu.WIDTH, Menu.HEIGHT, Color.WHITE);
        optionsMenuScene.setOnKeyPressed(e -> ElementsHandler.handleKeys(e));
    }

    /**
     * Gets the scene of the options menu
     *
     * @return - the scene of the options menu
     */
    public Scene getScene() {
        return optionsMenuScene;
    }
}

