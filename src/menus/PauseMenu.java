package menus;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

public class PauseMenu implements Menu {
	
	public static Button backGameButton, backMainButton;
	
	private Pane pauseMenuPane = null;
    private Scene pauseMenuScene = null;
    private ButtonProperties b = null;
    private Image backGameImage, backGameImageHovered, backMainImage, backMainImageHovered;
    private int spaceBetweenImgH = 50;
    
    private static String SEPARATOR = File.separator;
    
	public PauseMenu() {
		initialiseScene();
	}
	
	public void declareElements() {
		pauseMenuPane = new Pane();
		backGameButton = new Button();
		backMainButton = new Button();
		b = new ButtonProperties();
		
		backGameImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "resume_game_button.png");
		backGameImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "resume_game_button_hovered.png");
		
		backMainImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "quit_button.png");
		backMainImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "quit_button_hovered.png");
	}
	
	public void initialiseScene() {
		declareElements();
		
		// NEW GAME BUTTON
        b.setButtonProperties(backGameButton, "", Menu.WIDTH / 2 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3,
                e -> ElementsHandler.handle(e), new ImageView(backGameImage));
        b.addHoverEffect(backGameButton, backGameImageHovered, backGameImage, Menu.WIDTH / 2 - backGameImage.getWidth() / 2, Menu.HEIGHT / 3);
        
        b.setButtonProperties(backMainButton, "", Menu.WIDTH / 2 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH,
        		e -> ElementsHandler.handle(e), new ImageView(backMainImage));
        b.addHoverEffect(backMainButton, backMainImageHovered, backMainImage, Menu.WIDTH / 2 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH);
        
        pauseMenuPane.getChildren().addAll(backGameButton, backMainButton);
        Group mainMenuGroup = new Group(pauseMenuPane);
        pauseMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
	}

	public Scene getScene() {
		return pauseMenuScene;
	}

}
