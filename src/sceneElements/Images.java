package sceneElements;

import core.Renderer;
import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by dominic on 26/02/16.
 */
public class Images {
    private static final Renderer renderer = Renderer.Instance();
    public static final String SEPARATOR = File.separator;
    //unit images
    public static final Image imageDemon = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageDk = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 3.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageBanshee = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 4.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDemon = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 2.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDk = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 3.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedBanshee = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unit Sprite 4.0s.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //button images
    public static final Image playImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button.png");
    public static final Image playImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "play_button_hovered.png");
    public static final Image pauseImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button.png");
    public static final Image pauseImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "pause_button_hovered.png");
    //blockade images
    public static final Image unsortableImage1 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image sortableImage1 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 2.0.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //base images
    public static final Image base = new Image(SEPARATOR + "sprites" + SEPARATOR + "Blockade_sprite.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //menu images
    public static final Image newGameImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "NewGame.png");
    public static final Image newGameImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "NewGamSel.png");
    public static final Image optionsImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Options.png");
    public static final Image optionsImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "OptionsSel.png");
    public static final Image exitImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "Quit.png");
    public static final Image exitImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "QuitSel.png");
    public static final Image unsortableImage2 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 1.0.png", 55, 55, true, true);
    public static final Image sortableImage2 = new Image(SEPARATOR + "sprites" + SEPARATOR + "Unsortable blokage 2.0.png", 55, 55, true, true);
    //background images
    public static final Image backgroundDarkPortal = new Image(SEPARATOR + "sprites" + SEPARATOR + "MainMenu.png");
    //options images
    public static final Image onImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "on_button.png");
    public static final Image onImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "on_button_hovered.png");
    public static final Image offImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "off_button.png");
    public static final Image offImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "off_button_hovered.png");
    public static final Image hintsImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "hints_label.png");
    public static final Image soundImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "sound_label.png");
    public static final Image backImage = new Image(SEPARATOR + "sprites" + SEPARATOR + "back_button.png");
    public static final Image backImageHovered = new Image(SEPARATOR + "sprites" + SEPARATOR + "back_button.hovered.png");
}
