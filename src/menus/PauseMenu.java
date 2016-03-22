package menus;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import stores.ImageStore;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 * 
 * This class is Pause Menu. This menu contains three buttons: Resume Game, Options and Back To Main Menu.
 */
public class PauseMenu implements Menu {

    public static Button backGameButton, optionsButton, backMainButton;

    private int spaceBetweenImgH = 70;
    private Pane pauseMenuPane = null;
    private Scene pauseMenuScene = null;
    private ButtonProperties b = null;
    private Image backGameImage, backGameImageHovered, optionsImage, optionsImageHovered, backMainImage, backMainImageHovered;

    public PauseMenu() {
        initialiseScene();
    }

    /** 
     * Declares the elements that will be used for the creation of the 
     * scene
     */
    public void declareElements() {
    	//Panes
        pauseMenuPane = new Pane();
        //Buttons
        backGameButton = new Button();
        optionsButton = new Button();
        backMainButton = new Button();
        b = new ButtonProperties();
        //Images
        backGameImage = ImageStore.backGameImage;
        backGameImageHovered = ImageStore.backGameImageHovered;
        optionsImage = ImageStore.optionsImage;
        optionsImageHovered = ImageStore.optionsImageHovered;
        backMainImage = ImageStore.backMainImage;
        backMainImageHovered = ImageStore.backMainImageHovered;
    }

    /**
     * Initialises the scene which will contain all the buttons and images
     */
    public void initialiseScene() {
        declareElements();

        // BACK GAME BUTTON
        b.setButtonProperties(backGameButton, "", Menu.WIDTH / 5 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3,
                ElementsHandler::handle, new ImageView(backGameImage));
        b.addHoverEffect(backGameButton, backGameImageHovered, backGameImage, Menu.WIDTH / 5 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3);

        // OPTIONS BUTTON
        b.setButtonProperties(optionsButton, "", Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH);
        
        // BACK TO MAIN MENU BUTTON
        b.setButtonProperties(backMainButton, "", Menu.WIDTH / 5 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(backMainImage));
        b.addHoverEffect(backMainButton, backMainImageHovered, backMainImage, Menu.WIDTH / 5 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH);

        // Sets the size of the pane
        pauseMenuPane.setPrefSize(Menu.WIDTH, Menu.HEIGHT);
        BackgroundImage myBI = new BackgroundImage(ImageStore.pauseMenu, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        // Sets the background on the pane
        pauseMenuPane.setBackground(new Background(myBI));
        pauseMenuPane.getChildren().addAll(backGameButton, optionsButton, backMainButton);
        Group mainMenuGroup = new Group(pauseMenuPane);
        
        // Initialises the scene with the pane
        pauseMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    /**
     * Gets the Pause menu scene
     */
    public Scene getScene() {
        return pauseMenuScene;
    }

}
