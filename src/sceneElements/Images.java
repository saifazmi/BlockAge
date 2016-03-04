package sceneElements;

import core.Renderer;
import entity.Entity;
import javafx.scene.image.Image;

import javax.net.ssl.SSLProtocolException;
import java.io.File;

/**
 * Created by dominic on 26/02/16.
 */
public class Images {
    private static final Renderer renderer = Renderer.Instance();
    public static final String SEPARATOR = File.separator;
    private static final String sprites = SEPARATOR + "sprites" + SEPARATOR;
    private static final String entities = "entities" + SEPARATOR;
    private static final String units = "units" + SEPARATOR;
    private static final String blockades = "blockades" + SEPARATOR;
    private static final String buttons = "buttons" + SEPARATOR;
    private static final String backgrounds = "backgrounds" + SEPARATOR;
    private static final String labels = "labels" + SEPARATOR;
    //unit images
    public static final Image imageDemon = new Image(sprites + entities + units + "BFS_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageDk = new Image(sprites + entities + units + "AStar_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageBanshee = new Image(sprites + entities + units + "DFS_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDemon = new Image(sprites + entities + units + "BFS_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDk = new Image(sprites + entities + units + "AStar_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedBanshee = new Image(sprites + entities + units + "DFS_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //button images
    public static final Image playImage = new Image(sprites + buttons + "Play_Idle.png");
    public static final Image playImageHovered = new Image(sprites + buttons + "Play_Hover.png");
    public static final Image pauseImage = new Image(sprites + buttons + "Pause_Idle.png");
    public static final Image pauseImageHovered = new Image(sprites + buttons + "Pause_Hover.png");
    //blockade images
    public static final Image unsortableImage1 = new Image(sprites + entities + blockades + "Blockade_UnSortable.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image sortableImage1 = new Image(sprites + entities + blockades + "Blockade_Sortable.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //base images
    public static final Image base = new Image(sprites + entities + "Base.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //menu images
    public static final Image newGameImage = new Image(sprites + buttons + "NewGame_Idle.png");
    public static final Image newGameImageHovered = new Image(sprites + buttons + "NewGame_Hover.png");
    public static final Image optionsImage = new Image(sprites + buttons + "Options_Idle.png");
    public static final Image optionsImageHovered = new Image(sprites + buttons + "Options_Hover.png");
    public static final Image exitImage = new Image(sprites + buttons + "Quit_Idle.png");
    public static final Image exitImageHovered = new Image(sprites + buttons + "Quit_Hover.png");
    public static final Image unsortableImage2 = new Image(sprites + entities + blockades + "Blockade_UnSortable.png", 55, 55, false, false);
    public static final Image sortableImage2 = new Image(sprites + entities + blockades + "Blockade_Sortable.png", 55, 55, true, true);
    //background images
    public static final Image backgroundMainMenu = new Image(sprites + backgrounds + "MainMenu_Idle.png");
    public static final Image backgroundMainMenuGlow = new Image(sprites + backgrounds + "MainMenu_Glow.png");
    public static final Image backgroundOptionsMenu = new Image(sprites + backgrounds + "OptionsMenu.png");
    //options images
    public static final Image onImage = new Image(sprites + buttons + "On_Idle.png", 395, 55, true, true);
    public static final Image onImageHovered = new Image(sprites + buttons + "On_Hover.png", 395, 55, true, true);
    public static final Image offImage = new Image(sprites + buttons + "Off_Idle.png", 395, 55, true, true);
    public static final Image offImageHovered = new Image(sprites + buttons + "Off_Hover.png", 395, 55, true, true);
    public static final Image showSearchImage = new Image(sprites + labels + "ShowSearch.png", 395, 183, true, true);
    public static final Image soundImage = new Image(sprites + labels + "Sound.png", 395, 55, true, true);
    public static final Image blockadeImage = new Image(sprites + labels + "NoStartBlockade.png", 550, 0, true, true);
    public static final Image backImage = new Image(sprites + buttons + "Back_Idle.png", 395, 55, true, true);
    public static final Image backImageHovered = new Image(sprites + buttons + "Back_Hover.png", 395, 55, true, true);
    //in game pause menu
    public static final Image backGameImage = new Image(sprites + buttons + "ResumeGame_Idle.png");
    public static final Image backGameImageHovered = new Image(sprites + buttons + "ResumeGame_Hover.png");
    public static final Image backMainImage = new Image(sprites + buttons + "Quit_Idle.png");
    public static final Image backMainImageHovered = new Image(sprites + buttons + "Quit_Hover.png");

    public static void setSpriteProperties(Entity entity, Image image) {
        SpriteImage spriteImage = new SpriteImage(image, entity);
        spriteImage.setFitWidth(renderer.getXSpacing());
        spriteImage.setFitHeight(renderer.getYSpacing());
        spriteImage.setPreserveRatio(false);
        spriteImage.setSmooth(true);
        entity.setSprite(spriteImage);
    }
}
