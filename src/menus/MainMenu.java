package menus;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import stores.ImageStore;

/**
 * @author : First created by Paul Popa with code by Evgeniy Kim, and Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 25/02/16
 */

public class MainMenu implements Menu {


    public static Button newGameButton, exitButton, optionsButton, mapEditorButton, customGameButton;

    private Pane mainMenuPane = null;
    private Pane fadingPane = null;
    private Scene mainMenuScene = null;
    private ButtonProperties b = null;
    private Image newGameImage, newGameImageHovered, optionsImage, optionsImageHovered, exitImage, exitImageHovered, mapEditorImage, mapEditorHovered, customGameImage, customGameImageHovered;


    public MainMenu() {
        initialiseScene();
    }

    /**
     * Declaring the elements which will be placed on the scene
     */
    public void declareElements() {

        mainMenuPane = new Pane();
        fadingPane = new Pane();
        newGameButton = new Button();
        optionsButton = new Button();
        exitButton = new Button();
        mapEditorButton = new Button();
        b = new ButtonProperties();
        customGameButton = new Button();

        newGameImage = ImageStore.newGameImage;
        newGameImageHovered = ImageStore.newGameImageHovered;
        optionsImage = ImageStore.optionsImage;
        optionsImageHovered = ImageStore.optionsImageHovered;
        exitImage = ImageStore.exitImage;
        exitImageHovered = ImageStore.exitImageHovered;
        mapEditorImage = ImageStore.mapEditorImage;
        mapEditorHovered = ImageStore.mapEditorImageHovered;
        customGameImage = ImageStore.customGameImage;
        customGameImageHovered = ImageStore.customGameImageHover;
    }

    /**
     * Initialise the scene for the main menu and what will be inside the scene
     */
    /*public void initialiseScene() {

        declareElements();
        // NEW GAME BUTTON
        b.setButtonProperties(newGameButton, "", xPos(newGameImage), Menu.HEIGHT / 3, ElementsHandler::handle, new ImageView(newGameImage));
        b.addHoverEffect(newGameButton, newGameImageHovered, newGameImage, xPos(newGameImage), Menu.HEIGHT / 3);
        // OPTIONS BUTTON
        int spaceBetweenImgH = 70;
        b.setButtonProperties(optionsButton, "", xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH, ElementsHandler::handle, new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH);
        // EXIT BUTTON
        b.setButtonProperties(exitButton, "", Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 2,
                e -> ElementsHandler.handle(e), new ImageView(exitImage));
        b.addHoverEffect(exitButton, exitImageHovered, exitImage, Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 2);

        //Map Editor Button
        b.setButtonProperties(mapEditorButton, "", Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 3,
                e -> ElementsHandler.handle(e), new ImageView(mapEditorImage));
        b.addHoverEffect(mapEditorButton, mapEditorHovered, mapEditorImage, Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2,  Menu.HEIGHT / 3 + spaceBetweenImgH * 3);

        // custom map chooser
        b.setButtonProperties(customMapButton, "", Menu.WIDTH / 5 - customMapImage.getWidth() / 2, Menu.HEIGHT /3 + spaceBetweenImgH * 4,
                e ->ElementsHandler.handle(e), new ImageView(customMapImage));

        // ADD ALL BUTTONS TO THE PANE
        BackgroundImage myBIF = new BackgroundImage(ImageStore.backgroundMainMenuGlow, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        fadingPane.setBackground(new Background(myBIF));
        fadingPane.setPrefWidth(Menu.WIDTH);
        fadingPane.setPrefHeight(Menu.HEIGHT);

        // Fade transition of the main image
        FadeTransition ft = new FadeTransition(Duration.millis(4000), fadingPane);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
        ft.setOnFinished(e -> ft.play());

        mainMenuPane.getChildren().addAll(fadingPane, newGameButton, optionsButton, exitButton, mapEditorButton, customMapButton);

        Group mainMenuGroup = new Group(mainMenuPane);
        BackgroundImage myBI = new BackgroundImage(ImageStore.backgroundMainMenu, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        mainMenuPane.setBackground(new Background(myBI));
        mainMenuPane.setPrefWidth(Menu.WIDTH);
        mainMenuPane.setPrefHeight(Menu.HEIGHT);
        mainMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }*/

    /**
     * Initialise the scene for the main menu and what will be inside the scene
     */
    public void initialiseScene() {

        declareElements();
        // NEW GAME BUTTON
        b.setButtonProperties(newGameButton, "", xPos(newGameImage), Menu.HEIGHT / 3, ElementsHandler::handle, new ImageView(newGameImage));
        b.addHoverEffect(newGameButton, newGameImageHovered, newGameImage, xPos(newGameImage), Menu.HEIGHT / 3);

        int spaceBetweenImgH = 70;
        // NEW GAME CHOSEN MAP
        b.setButtonProperties(customGameButton, "", xPos(customGameImage), Menu.HEIGHT / 3 + spaceBetweenImgH, ElementsHandler::handle, new ImageView(customGameImage));
        b.addHoverEffect(customGameButton, customGameImageHovered, customGameImage, xPos(customGameImage), Menu.HEIGHT / 3 + spaceBetweenImgH);

        // OPTIONS BUTTON
        b.setButtonProperties(optionsButton, "", xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH * 2, ElementsHandler::handle, new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, xPos(optionsImage), Menu.HEIGHT / 3 + spaceBetweenImgH * 2);

        //Map Editor Button
        b.setButtonProperties(mapEditorButton, "", Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 3,
                e -> ElementsHandler.handle(e), new ImageView(mapEditorImage));
        b.addHoverEffect(mapEditorButton, mapEditorHovered, mapEditorImage, Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2,  Menu.HEIGHT / 3 + spaceBetweenImgH * 3);

        // EXIT BUTTON
        b.setButtonProperties(exitButton, "", Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 4,
                e -> ElementsHandler.handle(e), new ImageView(exitImage));
        b.addHoverEffect(exitButton, exitImageHovered, exitImage, Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 4);



        // ADD ALL BUTTONS TO THE PANE
        BackgroundImage myBIF = new BackgroundImage(ImageStore.backgroundMainMenuGlow, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        fadingPane.setBackground(new Background(myBIF));
        fadingPane.setPrefWidth(Menu.WIDTH);
        fadingPane.setPrefHeight(Menu.HEIGHT);

        // Fade transition of the main image
        FadeTransition ft = new FadeTransition(Duration.millis(2000), fadingPane);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(4);
        ft.setAutoReverse(true);
        ft.play();
        ft.setOnFinished(e -> ft.play());

        mainMenuPane.getChildren().addAll(fadingPane, newGameButton, optionsButton, exitButton, mapEditorButton, customGameButton);

        Group mainMenuGroup = new Group(mainMenuPane);
        BackgroundImage myBI = new BackgroundImage(ImageStore.backgroundMainMenu, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        mainMenuPane.setBackground(new Background(myBI));
        mainMenuPane.setPrefWidth(Menu.WIDTH);
        mainMenuPane.setPrefHeight(Menu.HEIGHT);
        mainMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    private double xPos(Image image) {
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
