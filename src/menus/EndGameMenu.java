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

import java.io.InputStream;

/**
 * @author : First created by Anh with code by Paul Popa
 * @date : 11/03/16, last edited by Anh on 11/03/16
 */
public class EndGameMenu implements Menu {

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

    public void declareElements() {
        endGameMenuPane = new Pane();
        backMainButton = new Button();
        b = new ButtonProperties();

        scoreLabel = new Label();
        l = new LabelProperties();

        backMainImage = ImageStore.backMainImage;
        backMainImageHovered = ImageStore.backMainImageHovered;
    }

    public void initialiseScene() {
        declareElements();
        l.setLabelProperties(scoreLabel, "", Menu.WIDTH / 5 - 210, Menu.HEIGHT / 3, null);

        // Loading font
        InputStream fontStream = EndGameMenu.class.getResourceAsStream(SEPARATOR + "resources" + SEPARATOR + "fonts" + SEPARATOR + "basis33.ttf");
        if (fontStream == null) {
            System.out.println("No font at that path");
        }

        scoreLabel.setFont(Font.loadFont(fontStream, 100));
        scoreLabel.setTextFill(Color.web("#FFE130"));

        int spaceBetweenImgH = 70;
        /**
         b.setButtonProperties(optionsButton, "", Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH,
         ElementsHandler::handle, new ImageView(optionsImage));
         b.addHoverEffect(optionsButton, optionsImageHovered, optionsImage, Menu.WIDTH / 5 - optionsImage.getWidth() / 2, Menu.HEIGHT / 3 + spaceBetweenImgH);
         */
        b.setButtonProperties(backMainButton, "", Menu.WIDTH / 5 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH,
                ElementsHandler::handle, new ImageView(backMainImage));
        b.addHoverEffect(backMainButton, backMainImageHovered, backMainImage, Menu.WIDTH / 5 - backMainImage.getWidth() / 2, Menu.HEIGHT / 3 + 2 * spaceBetweenImgH);

        endGameMenuPane.setPrefSize(Menu.WIDTH, Menu.HEIGHT);
        BackgroundImage myBI = new BackgroundImage(ImageStore.pauseMenu, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        endGameMenuPane.setBackground(new Background(myBI));
        endGameMenuPane.getChildren().addAll(backMainButton, scoreLabel);
        Group mainMenuGroup = new Group(endGameMenuPane);
        endGameMenuScene = new Scene(mainMenuGroup, Menu.WIDTH, Menu.HEIGHT);
    }

    public Scene getScene() {
        return endGameMenuScene;
    }

    public void setScore(double score) {
        this.scoreLabel.setText("Score: " + String.format("%.2f", score));
    }

}
