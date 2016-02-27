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
import sceneElements.Images;

/**
 * @author : First created by Paul Popa with code by Evgeniy Kim, and Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 25/02/16
 */

public class MainMenu implements Menu {
    public static Button newGameButton, exitButton, optionsButton = null;
    private Pane mainMenuPane = null;
    private Scene mainMenuScene = null;
    private ButtonProperties b = null;
    private Image newGameImage, newGameImageHovered, optionsImage, optionsImageHovered, exitImage, exitImageHovered = null;

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

        newGameImage = Images.newGameImage;
        newGameImageHovered = Images.newGameImageHovered;
        optionsImage = Images.optionsImage;
        optionsImageHovered = Images.optionsImageHovered;
        exitImage = Images.exitImage;
        exitImageHovered = Images.exitImageHovered;
    }

    /**
     * Initialise the scene for the main menu and what will be inside the scene
     */
    public void initialiseScene() {

        declareElements();
        // NEW GAME BUTTON
        b.setButtonProperties(newGameButton, "", xPos(newGameImage), Menu.HEIGHT / 3, ElementsHandler::handle, new ImageView(newGameImage));
        b.addHoverEffect(newGameButton, newGameImageHovered, newGameImage, xPos(newGameImage), Menu.HEIGHT / 3);
        // OPTIONS BUTTON
        int spaceBetweenImgH = 70;
        b.setButtonProperties(optionsButton, "", xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH, ElementsHandler::handle, new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH);
        // EXIT BUTTON
        b.setButtonProperties(exitButton, "", xPos(exitImage), Menu.HEIGHT / 3 + spaceBetweenImgH * 2, ElementsHandler::handle, new ImageView(exitImage));
        b.addHoverEffect(exitButton, exitImageHovered, exitImage, xPos(exitImage), Menu.HEIGHT / 3 + spaceBetweenImgH * 2);
        // ADD ALL BUTTONS TO THE PANE
        mainMenuPane.getChildren().addAll(newGameButton, optionsButton, exitButton);
        Group mainMenuGroup = new Group(mainMenuPane);
        BackgroundImage myBI = new BackgroundImage(Images.backgroundDarkPortal, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainMenuPane.setBackground(new Background(myBI));
        mainMenuPane.setPrefWidth(Menu.WIDTH);
        mainMenuPane.setPrefHeight(Menu.HEIGHT);
        mainMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    private double xPos(Image image)
    {
        return Menu.WIDTH / 5 - image.getWidth() / 2;
    }

    /**
     * Gets the scene of the main menu
     *
     * @return - the scene of the main menu
     */
    public Scene getScene() {
        return mainMenuScene;
    }
}
