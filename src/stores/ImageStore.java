package stores;

import entity.Entity;
import gui.Renderer;
import javafx.scene.image.Image;
import sceneElements.SpriteImage;

import java.io.File;

/**
 * Created by dominic on 26/02/16.
 */
public final class ImageStore {

    private static final Renderer renderer = Renderer.Instance();

    private static final String SEPARATOR = File.separator;
    private static final String SPRITE_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "sprites" + SEPARATOR;

    private static final String ENTITIES = "entities" + SEPARATOR;
    private static final String UNITS = "units" + SEPARATOR;
    private static final String BLOCKADES = "blockades" + SEPARATOR;
    private static final String BUTTONS = "buttons" + SEPARATOR;
    private static final String BACKGROUNDS = "backgrounds" + SEPARATOR;
    private static final String LABELS = "labels" + SEPARATOR;

    private ImageStore() {
        // To prevent instantiation.
    }

    //unit images
    public static final Image imageDemon = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "BFS_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageDk = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "AStar_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imageBanshee = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "DFS_Idle.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDemon = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "BFS_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedDk = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "AStar_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image imagePressedBanshee = new Image(SPRITE_RESOURCES + ENTITIES + UNITS + "DFS_Selected.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //button images
    public static final Image playImage = new Image(SPRITE_RESOURCES + BUTTONS + "Play_Idle.png");
    public static final Image playImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Play_Hover.png");
    public static final Image pauseImage = new Image(SPRITE_RESOURCES + BUTTONS + "Pause_Idle.png");
    public static final Image pauseImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Pause_Hover.png");
    //blockade images
    public static final Image unsortableImage1 = new Image(SPRITE_RESOURCES + ENTITIES + BLOCKADES + "UnSortable_Blockade.jpg", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image sortableImage1 = new Image(SPRITE_RESOURCES + ENTITIES + BLOCKADES + "sortableBlock.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    public static final Image sortableBiggerImage = new Image(SPRITE_RESOURCES + ENTITIES + BLOCKADES + "sortableBlockSorting.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //base images
    public static final Image base = new Image(SPRITE_RESOURCES + ENTITIES + "Base.png", renderer.getXSpacing(), renderer.getYSpacing(), true, true);
    //menu images
    public static final Image newGameImage = new Image(SPRITE_RESOURCES + BUTTONS + "NewGame_Idle.png");
    public static final Image newGameImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "NewGame_Hover.png");
    public static final Image optionsImage = new Image(SPRITE_RESOURCES + BUTTONS + "Options_Idle.png");
    public static final Image optionsImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Options_Hover.png");
    public static final Image exitImage = new Image(SPRITE_RESOURCES + BUTTONS + "Quit_Idle.png");
    public static final Image exitImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Quit_Hover.png");
    public static final Image unsortableImage2 = new Image(SPRITE_RESOURCES + ENTITIES + BLOCKADES + "UnSortable_Blockade.jpg", 55, 55, false, true);
    public static final Image sortableImage2 = new Image(SPRITE_RESOURCES + ENTITIES + BLOCKADES + "sortableBlock.png", 55, 55, false, true);
    //background images
    public static final Image backgroundMainMenu = new Image(SPRITE_RESOURCES + BACKGROUNDS + "MainMenu_Idle.png");
    public static final Image backgroundMainMenuGlow = new Image(SPRITE_RESOURCES + BACKGROUNDS + "MainMenu_Glow.png");
    public static final Image backgroundOptionsMenu = new Image(SPRITE_RESOURCES + BACKGROUNDS + "OptionsMenu.png");
    public static final Image pauseMenu = new Image(SPRITE_RESOURCES + BACKGROUNDS + "PauseMenu.png");
    public static final Image paneBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "Pane.png");
    public static final Image grassBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "GrassBackground.png");
    public static final Image yesNoBackground = new Image(SPRITE_RESOURCES + BACKGROUNDS + "yesNoPane.png");
    //options images
    public static final Image onImage = new Image(SPRITE_RESOURCES + BUTTONS + "On_Idle.png", 395, 55, true, true);
    public static final Image onImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "On_Hover.png", 395, 55, true, true);
    public static final Image offImage = new Image(SPRITE_RESOURCES + BUTTONS + "Off_Idle.png", 395, 55, true, true);
    public static final Image offImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Off_Hover.png", 395, 55, true, true);
    public static final Image showSearchImage = new Image(SPRITE_RESOURCES + LABELS + "ShowSearch.png", 395, 183, true, true);
    public static final Image soundImage = new Image(SPRITE_RESOURCES + LABELS + "Sound.png", 395, 55, true, true);
    public static final Image tutorialImage = new Image(SPRITE_RESOURCES + LABELS + "Tutorial.png", 395, 55, true, true);
    public static final Image blockadeImage = new Image(SPRITE_RESOURCES + LABELS + "NoStartBlockade.png", 550, 0, true, true);
    public static final Image backImage = new Image(SPRITE_RESOURCES + BUTTONS + "Back_Idle.png", 395, 55, true, true);
    public static final Image backImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Back_Hover.png", 395, 55, true, true);
    //in game pause menu
    public static final Image backGameImage = new Image(SPRITE_RESOURCES + BUTTONS + "ResumeGame_Idle.png");
    public static final Image backGameImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "ResumeGame_Hover.png");
    public static final Image backMainImage = new Image(SPRITE_RESOURCES + BUTTONS + "Quit_Idle.png");
    public static final Image backMainImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Quit_Hover.png");
    // map editor related
    public static final Image mapEditorImage = new Image(SPRITE_RESOURCES + BUTTONS + "MapEditor.png", 410, 130, true, true);
    public static final Image mapEditorImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "MapEditor_Hover.png", 410, 130, true, true);
    public static final Image saveMapImage = new Image(SPRITE_RESOURCES + BUTTONS + "Save.png");
    public static final Image saveMapImageHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Save_Hovered.png");
    public static final Image backFromEditor = new Image(SPRITE_RESOURCES + BUTTONS + "Back-small.png");
    public static final Image backFromEditorHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Back-small_Hovered.png");
    public static final Image overwriteYes = new Image(SPRITE_RESOURCES + BUTTONS + "Yes.png");
    public static final Image overwriteYesHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Yes_Hovered.png");
    public static final Image overwriteNo = new Image(SPRITE_RESOURCES + BUTTONS + "No.png");
    public static final Image overwriteNoHovered = new Image(SPRITE_RESOURCES + BUTTONS + "No_Hovered.png");
    public static final Image clear = new Image(SPRITE_RESOURCES + BUTTONS + "Clear.png");
    public static final Image clearHovered = new Image(SPRITE_RESOURCES + BUTTONS + "Clear_Hovered.png");
    public static final Image customGameImage = new Image(SPRITE_RESOURCES + BUTTONS + "CustomGame_Idle.png");
    public static final Image customGameImageHover = new Image(SPRITE_RESOURCES + BUTTONS + "CustomGame_Hover.png");

    public static void setSpriteProperties(Entity entity, Image image) {
        SpriteImage spriteImage = new SpriteImage(image, entity);
        spriteImage.setFitWidth(renderer.getXSpacing());
        spriteImage.setFitHeight(renderer.getYSpacing());
        spriteImage.setPreserveRatio(false);
        spriteImage.setSmooth(true);
        entity.setSprite(spriteImage);
    }
}
