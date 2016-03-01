package menus;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.Images;
import sceneElements.LabelProperties;

import java.io.File;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 */
public class OptionsMenu implements Menu {
    public static Button yesButtonH, yesButtonS, noButtonH, noButtonS, backButton = null;
    public static Image hintsImage, soundImage, onImage, onImageHovered, offImage, offImageHovered, backImage, backImageHovered = null;
    public static int spaceBetweenText = 100;
    public static int spaceBetweenImgH = 50;
    public static Pane optionsMenuPane = null;

    private Scene optionsMenuScene = null;
    private Label hintsLabel, soundLabel = null;
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
        yesButtonH = new Button();
        noButtonH = new Button();
        yesButtonS = new Button();
        noButtonS = new Button();
        backButton = new Button();

        hintsLabel = new Label();
        soundLabel = new Label();

        optionsMenuPane = new Pane();
        l = new LabelProperties();
        b = new ButtonProperties();

        onImage = Images.onImage;
        onImageHovered = Images.onImageHovered;
        offImage = Images.offImage;
        offImageHovered = Images.offImageHovered;
        hintsImage = Images.hintsImage;
        soundImage = Images.soundImage;
        backImage = Images.backImage;
        backImageHovered = Images.backImageHovered;
    }

    /**
     * Initialise the scene for the options menu and what will be inside the scene
     */
    public void initialiseScene() {
        declareElements();
        double xPos1 = Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText;
        double xPos2 = Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4;
        double yPos1 = Menu.HEIGHT / 3;

        b.setButtonProperties(noButtonH, "", xPos1, yPos1, ElementsHandler::handle, new ImageView(offImage));
        b.addHoverEffect(noButtonH, offImageHovered, offImage, xPos1, yPos1);

        b.setButtonProperties(noButtonS, "", xPos1, yPos1 + spaceBetweenImgH, ElementsHandler::handle, new ImageView(offImage));
        b.addHoverEffect(noButtonS, offImageHovered, offImage, xPos1, yPos1 + spaceBetweenImgH);

        b.setButtonProperties(backButton, "", xPos2, Menu.HEIGHT / 1.1, ElementsHandler::handle, new ImageView(backImage));
        b.addHoverEffect(backButton, backImageHovered, backImage, xPos2, Menu.HEIGHT / 1.1);

        l.setLabelProperties(hintsLabel, "", Menu.WIDTH / 2 - hintsImage.getWidth() - spaceBetweenText, yPos1, new ImageView(hintsImage));
        l.setLabelProperties(soundLabel, "", Menu.WIDTH / 2 - soundImage.getWidth() - spaceBetweenText, yPos1 + spaceBetweenImgH, new ImageView(soundImage));

        // To do sound configurations for button as well
        optionsMenuPane.getChildren().addAll(hintsLabel, soundLabel, noButtonH, noButtonS, backButton);
        final Group group = new Group(optionsMenuPane);

        optionsMenuScene = new Scene(group, Menu.WIDTH, Menu.HEIGHT, Color.WHITE);
        optionsMenuScene.setOnKeyPressed(ElementsHandler::handleKeys);
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

