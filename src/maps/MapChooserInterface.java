package maps;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menus.MenuHandler;
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author : Hung Hoang; Contributors - Paul Popa, Saif Azmi
 * @version : 23/03/2016;
 *          <p>
 *          The class that builds and handles the interface and inputs for the map editor
 *
 * @date : 28/01/16
 */

public class MapChooserInterface {

    private static final Logger LOG = Logger.getLogger(MapChooserInterface.class.getName());

    private final String SEPERATOR = "/";

    private String USER_MAP_DIRECTORY;
    private final String IMAGE_DIRECTORY;

    private static String chosenMap;
    private static Stage mapChooseStage;
    private Scene mapChooseScene;
    private HBox images;
    private ArrayList<VBox> mapImages;
    private ArrayList<String> mapNames;

    // Instance for singleton.
    private static MapChooserInterface instance;

    /**
     * Implements Singleton for this class (Only one can exist).
     *
     * @return the map chooser interface instance
     */
    public static MapChooserInterface Instance() {
        if (instance == null)
            instance = new MapChooserInterface();

        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {

        instance = null;
    }

    /**
     * The constructor creates a new stage and scene for the map chooser
     * It will look for the map files in the application directory and
     * sets up the appropriate images for the scroll pane
     * The chooser will act like a pop-up window
     */
    private MapChooserInterface() {

        mapChooseStage = new Stage();
        images = new HBox();

        String dir = System.getProperty("user.home");
        USER_MAP_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "data" + SEPERATOR;
        IMAGE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "image" + SEPERATOR;

        File userMapDir = new File(USER_MAP_DIRECTORY);

        if (userMapDir.exists()) {
            setUpMapImages();
        }

        ScrollPane scroller = new ScrollPane(images);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setPrefSize(820, 650);

        mapChooseScene = new Scene(scroller);
        mapChooseStage.setScene(mapChooseScene);
        mapChooseStage.initModality(Modality.APPLICATION_MODAL);
        mapChooseStage.hide();
    }

    // GETTER methods

    /**
     * Gets the map chosen by the user once they click on it
     *
     * @return The name of the map selected by the user
     */
    public String getChosenMap() {

        LOG.log(Level.INFO, USER_MAP_DIRECTORY + chosenMap);
        return USER_MAP_DIRECTORY + chosenMap;
    }

    /**
     * Creates a new button for each map that is found in the application directory
     * Each button will have an image, which is a saved .png version of the map
     */
    private void getMapImages() {

        mapImages = new ArrayList<>();
        mapNames = new ArrayList<>();

        ButtonProperties b = new ButtonProperties();

        File imageDir = new File(IMAGE_DIRECTORY);
        File[] mapFiles = imageDir.listFiles();

        try {

            if (mapFiles != null) {
                for (File mapFile : mapFiles) {

                    // Gets the images and maps
                    Image mapImage = new Image(mapFile.toURI().toString());
                    String mapName = getDataOf(mapFile);
                    mapNames.add(mapName);

                    // Create an image view
                    ImageView mapImageView = new ImageView(mapImage);
                    mapImageView.setPreserveRatio(true);
                    mapImageView.setFitHeight(600);


                    Label mapNameLabel = new Label(mapName);
                    VBox container = new VBox();
                    container.setAlignment(Pos.CENTER);

                    // Creates the button for choosing the map
                    Button newMap = new Button();
                    b.setButtonProperties(
                            newMap,
                            0,
                            0,
                            e -> chooseMap(container),
                            mapImageView
                    );

                    b.addHoverEffect3(newMap);

                    container.getChildren().addAll(mapNameLabel, newMap);
                    mapImages.add(container);
                }
            }

        } catch (NullPointerException e) {

            LOG.log(Level.WARNING, "No custom map directory");
            MenuHandler.switchScene(MenuHandler.MAP_EDITOR);
        }
    }

    // SETTER methods

    /**
     * Clears all the previous images to refresh, loads images and add them to scroll pane
     */
    private void setUpMapImages() {

        images.getChildren().clear();

        getMapImages();

        for (VBox mapImage : mapImages) {
            images.getChildren().add(mapImage);
        }

        images.setSpacing(50);
    }

    /**
     * Resets the chosen map
     */
    public void resetChosenMap() {

        chosenMap = "null";
    }

    /**
     * Shows the map chooser, wait for user to click on a map
     */
    public void showChooser() {

        setUpMapImages();
        mapChooseStage.showAndWait();
    }


    /**
     * Gets the actual data of the map (know the .png file name so just gets the .map file)
     *
     * @param mapFile The string that denotes the file to get <name>.png
     * @return The .map file of the desired map
     */
    private String getDataOf(File mapFile) {

        String[] mapNameParts = mapFile.toURI().toString().split(SEPERATOR);
        String mapFileName = mapNameParts[mapNameParts.length - 1];

        return mapFileName.replace(".png", ".map");
    }

    /**
     * Function is called when a 'map' button is pressed.
     * The chosenMap variable will be set to the name of the chosen map (which the button was associated with)
     * A new game will start with the chosen map
     *
     * @param e The VBox which contain the button
     */
    private void chooseMap(VBox e) {

        int buttonIndex = mapImages.indexOf(e);
        chosenMap = mapNames.get(buttonIndex);
        ElementsHandler.startGame();
        mapChooseStage.hide();
    }
}
