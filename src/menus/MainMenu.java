package menus;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.io.File;


/**
 * @author : First created by Paul Popa with code by Evgeniy Kim, and Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 25/02/16
 */

public class MainMenu implements Menu {

    public static Button newGameButton, exitButton, optionsButton, mapEditorButton = null;

    private Pane mainMenuPane = null;
    private Scene mainMenuScene = null;
    private ButtonProperties b = null;
    private Image newGameImage, newGameImageHovered, optionsImage, optionsImageHovered, exitImage, exitImageHovered, mapEditorImage, mapEditorHovered = null;
    private int spaceBetweenImgH = 70;
    String SEPARATOR = File.separator;

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
        mapEditorButton = new Button();
        b = new ButtonProperties();


        newGameImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "NewGame.png");
        newGameImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "NewGamSel.png");

        optionsImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Options.png");
        optionsImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "OptionsSel.png");

        exitImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Quit.png");
        exitImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "QuitSel.png");

        mapEditorImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "MapEditor.png");
        mapEditorHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "MapEditorSel.png");
    }

    /**
     * Initialise the scene for the main menu and what will be inside the scene
     */
    public void initialiseScene() {

        declareElements();
        // NEW GAME BUTTON

        b.setButtonProperties(newGameButton, "", Menu.WIDTH / 5 - newGameImage.getWidth() / 2, Menu.HEIGHT / 3,
                e -> ElementsHandler.handle(e), new ImageView(newGameImage));
        b.addHoverEffect(newGameButton, newGameImageHovered, newGameImage, Menu.WIDTH / 5 - newGameImage.getWidth() / 2, Menu.HEIGHT / 3);

        // OPTIONS BUTTON
        b.setButtonProperties(optionsButton, "", Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH,
                e -> ElementsHandler.handle(e), new ImageView(optionsImage));
        b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH);

        // EXIT BUTTON
        b.setButtonProperties(exitButton, "", Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 2,
                e -> ElementsHandler.handle(e), new ImageView(exitImage));
        b.addHoverEffect(exitButton, exitImageHovered, exitImage, Menu.WIDTH / 5 - exitImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 2);

        //Map Editor Button
        b.setButtonProperties(mapEditorButton, "", Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH * 3,
                e -> ElementsHandler.handle(e), new ImageView(mapEditorImage));
        b.addHoverEffect(mapEditorButton, mapEditorHovered, mapEditorImage, Menu.WIDTH / 5 - mapEditorImage.getWidth() / 2,  Menu.HEIGHT / 3 + spaceBetweenImgH * 3);

        // ADD ALL BUTTONS TO THE PANE
        mainMenuPane.getChildren().addAll(newGameButton, optionsButton, exitButton, mapEditorButton);
        Group mainMenuGroup = new Group(mainMenuPane);
        BackgroundImage myBI = new BackgroundImage(new Image(SEPARATOR + "sprites" + SEPARATOR + "MainMenu.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        ;
        mainMenuPane.setBackground(new Background(myBI));
        mainMenuPane.setPrefWidth(Menu.WIDTH);
        mainMenuPane.setPrefHeight(Menu.HEIGHT);
        mainMenuPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldWidth, Number newWidth) {
                System.out.println(newWidth);
            }
        });
        mainMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
        //mainMenuScene.getStylesheets().add(MainMenu.class.getResource("css/testcss.css").toExternalForm());
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
