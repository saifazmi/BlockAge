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

public class MainMenu implements Menu {
	
	public static Button newGameButton, exitButton, optionsButton = null;
	
	private Pane mainMenuPane = null;
	private Scene mainMenuScene = null;
	private ButtonProperties b = null;
	private Image newGameImage, newGameImageHovered, optionsImage, optionsImageHovered, exitImage, exitImageHovered = null;
	private int spaceBetweenImgH = 50;

	public MainMenu() {
		initialiseScene();
	}
	
	/**
	 * Declaring the elements which will be placed on the scene
	 */
	public void declareElements() {
		
		mainMenuPane = new Pane();
		newGameButton = new Button();
		optionsButton = new Button();
		exitButton = new Button();
		b = new ButtonProperties();
		
		System.out.println(System.getProperty("user.dir") + "\\src\\sprites\\new_game_button.png");
		
		newGameImage = new Image(System.getProperty("user.dir") + "\\src\\sprites\\new_game_button.png");
		newGameImageHovered = new Image(getClass().getResourceAsStream("images/new_game_button_hovered.png"));
		
		optionsImage = new Image(getClass().getResourceAsStream("images/options_button.png"));
		optionsImageHovered = new Image(getClass().getResourceAsStream("images/options_button_hovered.png"));
		
		exitImage = new Image(getClass().getResourceAsStream("images/exit_button.png"));
		exitImageHovered = new Image(getClass().getResourceAsStream("images/exit_button_hovered.png"));
	}
	
	/**
	 * Initialise the scene for the main menu and what will be inside the scene
	 */
	public void initialiseScene() {
		
		declareElements();
		// NEW GAME BUTTON
		b.setButtonProperties(newGameButton, "", Menu.WIDTH/2 - newGameImage.getWidth()/2, Menu.HEIGHT/3, 
				e->ElementsHandler.handle(e), new ImageView(newGameImage));
		b.addHoverEffect(newGameButton, newGameImageHovered, newGameImage,Menu.WIDTH/2 - newGameImage.getWidth()/2, Menu.HEIGHT/3);
		
		// OPTIONS BUTTON
		b.setButtonProperties(optionsButton, "", Menu.WIDTH/2 - optionsImage.getWidth()/2, Menu.HEIGHT/3 + spaceBetweenImgH, 
				e->ElementsHandler.handle(e), new ImageView(optionsImage));
		b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage,  Menu.WIDTH/2 - optionsImage.getWidth()/2, Menu.HEIGHT/3 + spaceBetweenImgH);
		
		// EXIT BUTTON
		b.setButtonProperties(exitButton, "", Menu.WIDTH/2 - exitImage.getWidth()/2, Menu.HEIGHT/3 + spaceBetweenImgH*2,
				e->ElementsHandler.handle(e), new ImageView(exitImage));
		b.addHoverEffect(exitButton, exitImageHovered, exitImage, Menu.WIDTH/2 - exitImage.getWidth()/2, Menu.HEIGHT/3 + spaceBetweenImgH*2);
		
		// ADD ALL BUTTONS TO THE PANE
		mainMenuPane.getChildren().addAll(newGameButton, optionsButton, exitButton);
		Group mainMenuGroup = new Group(mainMenuPane);
		mainMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT); 
		//mainMenuScene.getStylesheets().add(MainMenu.class.getResource("css/testcss.css").toExternalForm());
	}

	/**
	 * Gets the scene of the main menu
	 * @return - the scene of the main menu
	 */
	public Scene getScene() {
		return mainMenuScene;
	}
}
