package menus;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import stores.ImageStore;

/**
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 12/02/16
 */
public class PauseMenu implements Menu {

    public static Button backGameButton, optionsButton, backMainButton;

    private Pane pauseMenuPane = null;
    private Scene pauseMenuScene = null;
    private ButtonProperties b = null;
    private Image backGameImage, backGameImageHovered, optionsImage, optionsImageHovered, backMainImage, backMainImageHovered;

    public PauseMenu() {
        initialiseScene();
    }

    public void declareElements() {
        pauseMenuPane = new Pane();
        backGameButton = new Button();
        optionsButton = new Button();
        backMainButton = new Button();
        b = new ButtonProperties();

        backGameImage = ImageStore.backGameImage;
        backGameImageHovered = ImageStore.backGameImageHovered;
        optionsImage = ImageStore.optionsImage;
        optionsImageHovered = ImageStore.optionsImageHovered;
        backMainImage = ImageStore.backMainImage;
        backMainImageHovered = ImageStore.backMainImageHovered;
    }

    public void initialiseScene() {
        declareElements();

        // BACK GAME BUTTON
        b.setButtonProperties(backGameButton, "", Menu.WIDTH / 2 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3,
                ElementsHandler::handle, new ImageView(backGameImage));
        b.addHoverEffect(backGameButton, backGameImageHovered, backGameImage, Menu.WIDTH / 2 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3);

        int spaceBetweenImgH = 70;
        b.setButtonProperties(optionsButton, "", Menu.WIDTH / 2 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, Menu.WIDTH / 2 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH);
        b.setButtonProperties(backMainButton, "", Menu.WIDTH / 2 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(backMainImage));
        b.addHoverEffect(backMainButton, backMainImageHovered, backMainImage, Menu.WIDTH / 2 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH);

        pauseMenuPane.getChildren().addAll(backGameButton, optionsButton, backMainButton);
        Group mainMenuGroup = new Group(pauseMenuPane);
        pauseMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    public Scene getScene() {
        return pauseMenuScene;
    }

}
