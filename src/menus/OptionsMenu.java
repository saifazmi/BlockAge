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
import stores.ImageStore;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 * 
 * This class is the Options Menu. There are four options that can be turned on or off: Show Search,
 * Sound, Tutorial, No initial blockades.
 */
public class OptionsMenu implements Menu {
    public static Button yesButtonSearch, yesButtonSound, noButtonSearch, noButtonSound, yesButtonTutorial, noButtonTutorial, yesButtonB, noButtonB, backButton = null;
    public static Image showSearchImage, soundImage, tutorialImage, blockadeImage, onImage, onImageHovered, offImage, offImageHovered, backImage, backImageHovered = null;
    public static int spaceBetweenText = 100;
    public static int spaceBetweenImgH = 50;
    public static Label searchLabel, soundLabel, tutorialLabel, blockadeLabel = null;
    public static Pane optionsMenuPane = null;

    private Scene optionsMenuScene = null;

    private LabelProperties l = null;
    private ButtonProperties b = null;

    /**
     * Initialises the scene which will contain the pane with all
     * the buttons, labels and images placed accordingly
     */
    public OptionsMenu() {
        initialiseScene();
    }

    /**
     * Declaring the elements which will be placed on the scene
     */
    public void declareElements() {
    	//Buttons
        yesButtonSearch = new Button();
        noButtonSearch = new Button();
        yesButtonSound = new Button();
        noButtonSound = new Button();
        yesButtonTutorial = new Button();
        noButtonTutorial = new Button();
        yesButtonB = new Button();
        noButtonB = new Button();
        backButton = new Button();
        //Labels
        searchLabel = new Label();
        soundLabel = new Label();
        tutorialLabel = new Label();
        blockadeLabel = new Label();
        //Pane
        optionsMenuPane = new Pane();
        l = new LabelProperties();
        b = new ButtonProperties();
        //Images
        onImage = ImageStore.onImage;
        onImageHovered = ImageStore.onImageHovered;
        offImage = ImageStore.offImage;
        offImageHovered = ImageStore.offImageHovered;
        showSearchImage = ImageStore.showSearchImage;
        soundImage = ImageStore.soundImage;
        tutorialImage = ImageStore.tutorialImage;
        blockadeImage = ImageStore.blockadeImage;
        backImage = ImageStore.backImage;
        backImageHovered = ImageStore.backImageHovered;
    }

    /**
     * Initialise the scene for the options menu and what will be inside the scene
     */
    public void initialiseScene() {
        declareElements();
        // Set properties for the on/off SEARCH button
        b.setButtonProperties(yesButtonSearch, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3,
                ElementsHandler::handle, new ImageView(onImage));
        b.addHoverEffect(yesButtonSearch, onImageHovered, onImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3);

        // Set properties for the on/off SOUND button
        b.setButtonProperties(noButtonSound, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(offImage));
        b.addHoverEffect(noButtonSound, offImageHovered, offImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH);

        // Set properties for the on/off SOUND button
        b.setButtonProperties(yesButtonTutorial, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(onImage));
        b.addHoverEffect(yesButtonTutorial, onImageHovered, onImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH);

        // Set properties for the on/off BLOCKADES button
        b.setButtonProperties(yesButtonB, "", Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 3 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(onImage));
        b.addHoverEffect(yesButtonB, onImageHovered, onImage, Menu.WIDTH / 2 + onImage.getWidth() + spaceBetweenText, Menu.HEIGHT / 3 + 3 * spaceBetweenImgH);

        // Set properties for the back button
        b.setButtonProperties(backButton, "", Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.2,
                ElementsHandler::handle, new ImageView(backImage));
        b.addHoverEffect(backButton, backImageHovered, backImage, Menu.WIDTH / 2 - backImage.getWidth() - spaceBetweenText * 4, Menu.HEIGHT / 1.2);

        // Set labels with images
        l.setLabelProperties(searchLabel, "", Menu.WIDTH / 1.75 - showSearchImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3, new ImageView(showSearchImage));
        l.setLabelProperties(soundLabel, "", Menu.WIDTH / 1.75 - soundImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + spaceBetweenImgH, new ImageView(soundImage));
        l.setLabelProperties(tutorialLabel, "", Menu.WIDTH / 1.75 - tutorialImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH, new ImageView(tutorialImage));
        l.setLabelProperties(blockadeLabel, "", Menu.WIDTH / 1.75 - blockadeImage.getWidth() - spaceBetweenText, Menu.HEIGHT / 3 + 3 * spaceBetweenImgH, new ImageView(blockadeImage));

        // Adds everything to the pane
        optionsMenuPane.getChildren().addAll(searchLabel, soundLabel, tutorialLabel, blockadeLabel, yesButtonSearch, noButtonSound, yesButtonTutorial, yesButtonB, backButton);
        BackgroundImage myBI = new BackgroundImage(ImageStore.backgroundOptionsMenu, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        optionsMenuPane.setBackground(new Background(myBI));
        optionsMenuPane.setPrefWidth(Menu.WIDTH);
        optionsMenuPane.setPrefHeight(Menu.HEIGHT);
        final Group group = new Group(optionsMenuPane);

        // Create the scene that will be placed on the primary stage
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

