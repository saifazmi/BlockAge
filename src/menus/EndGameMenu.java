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
import javafx.scene.text.Font;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;
import sceneElements.LabelProperties;
import stores.ImageStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Anh with code by Paul Popa
 * @date : 11/03/16, last edited by Anh on 11/03/16
 *
 * This class creates the menu with the end game state. The menu will display
 * the final score the user achieved as well as with a button to go back to the main menu.
 */
public class EndGameMenu implements Menu {

    private static final Logger LOG = Logger.getLogger(EndGameMenu.class.getName());

    public static Button backMainButton;
    public static Label scoreLabel;
    private final String SEPARATOR = "/";

    private Pane endGameMenuPane = null;
    private Scene endGameMenuScene = null;
    private ButtonProperties b = null;
    private LabelProperties l = null;
    private Image backMainImage, backMainImageHovered;

    public EndGameMenu() {

        initialiseScene();
    }

    /**
     * Declares the elements that will be placed on the pane
     */
    public void declareElements() {
        //Pane
        endGameMenuPane = new Pane();
        //Buttons
        backMainButton = new Button();
        b = new ButtonProperties();
        //Labels
        scoreLabel = new Label();
        l = new LabelProperties();
        //Images
        backMainImage = ImageStore.backMainImage;
        backMainImageHovered = ImageStore.backMainImageHovered;
    }

    /**
     * Initilizes the scene with the score and the back to main menu button
     */
    public void initialiseScene() {

        declareElements();

        l.setLabelProperties(
                scoreLabel,
                Menu.WIDTH / 5 - 210,
                Menu.HEIGHT / 3,
                null
        );

        // Loading font
        try (InputStream fontStream = EndGameMenu.class.getResourceAsStream(
                SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf")) {

            scoreLabel.setFont(Font.loadFont(fontStream, 100));
            scoreLabel.setTextFill(Color.web("#FFE130"));

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "No font found");
            LOG.log(Level.SEVERE, e.toString(), e);
        }

        int spaceBetweenImgH = 70;

        // Sets the properties for the back to main menu button
        b.setButtonProperties(
                backMainButton,
                Menu.WIDTH / 5 - backMainImage.getWidth() / 2,
                Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(backMainImage)
        );

        b.addHoverEffect(
                backMainButton,
                backMainImageHovered,
                backMainImage,
                Menu.WIDTH / 5 - backMainImage.getWidth() / 2,
                Menu.HEIGHT / 3 + 2 * spaceBetweenImgH
        );

        // Set the size and the background for the pane
        endGameMenuPane.setPrefSize(Menu.WIDTH, Menu.HEIGHT);

        BackgroundImage myBI = new BackgroundImage(
                ImageStore.pauseMenu,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        endGameMenuPane.setBackground(new Background(myBI));
        endGameMenuPane.getChildren().addAll(backMainButton, scoreLabel);

        // Create the scene where all the elements will be placed on
        Group mainMenuGroup = new Group(endGameMenuPane);
        endGameMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    /**
     * Gets the end game scene
     * @return the end game scene
     */
    public Scene getScene() {

        return this.endGameMenuScene;
    }

    /**
     * Sets the score which will be displayed on the pane
     * @param score the score to be displayed
     */
    public void setScore(double score) {

        EndGameMenu.scoreLabel.setText("Score: " + String.format("%.2f", score));
    }

}
