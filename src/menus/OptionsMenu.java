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
import sceneElements.LabelProperties;

import java.io.File;

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

        onImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "on_button.png");
        onImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "on_button_hovered.png");

        offImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "off_button.png");
        offImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "off_button_hovered.png");

        hintsImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "hints_label.png");
        soundImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "sound_label.png");

        backImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "back_button.png");
        backImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "back_button.hovered.png");
    }

    /**
     * Initialise the scene for the options menu and what will be inside the scene
     */
    public void initialiseScene() {
        declareElements();

        b.setButtonProperties(noButtonH, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3,
                e -> ElementsHandler.handle(e), new ImageView(offImage));
        b.addHoverEffect(noButtonH, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3);

        b.setButtonProperties(noButtonS, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH,
                e -> ElementsHandler.handle(e), new ImageView(offImage));
        b.addHoverEffect(noButtonS, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH);

        b.setButtonProperties(backButton, "", Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.1,
                e -> ElementsHandler.handle(e), new ImageView(backImage));
        b.addHoverEffect(backButton, backImageHovered, backImage, Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.1);

        l.setLabelProperties(hintsLabel, "", Menu.WIDTH / 2 - hintsImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3, new ImageView(hintsImage));
        l.setLabelProperties(soundLabel, "", Menu.WIDTH / 2 - soundImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH, new ImageView(soundImage));

        // To do sound configurations for button as well
        optionsMenuPane.getChildren().addAll(hintsLabel, soundLabel, noButtonH, noButtonS, backButton);
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

