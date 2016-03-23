package maps;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
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
 * Created by hung on 06/03/16.
 */

/**
 * The class that builds and handles the interface and inputs for the map editor
 */
public class MapChooserInterface {

    private static final Logger LOG = Logger.getLogger(MapChooserInterface.class.getName());

    private static final double THRESHOLD = 100;
    private static String chosenMap;
    private static String USER_MAP_DIRECTORY;
    private final String IMAGE_DIRECTORY;
    private final String SEPERATOR = "/";
    private static Stage mapChooseStage;
    private Scene mapChooseScene;
    private HBox images;
    private ArrayList<VBox> mapImages;
    private ArrayList<String> mapNames;
    private double oldHValue = 0;

    private static MapChooserInterface instance;

    /**
     * Implements singleton as there will only ever be one instance of this class
     * @return
     */
    public static MapChooserInterface Instance() {
        if (instance == null)
            instance = new MapChooserInterface();

        return instance;
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    /**
     * The constructor creates a new stage and scene for the map chooser
     * It will look for the map files in the application directory and sets up the appropriate images for the scroll pane
     * The chooser will act like a pop-up window
     */
    private MapChooserInterface()
    {
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
        //scroller.setOnScroll(e -> mapSelectScrolling(scroller, e));

        mapChooseScene = new Scene(scroller);
        mapChooseStage.setScene(mapChooseScene);
        mapChooseStage.initModality(Modality.APPLICATION_MODAL);
        mapChooseStage.hide();
    }

    /**
     * Clears all the previous images to refresh, loads images and add them to scroll pane
     */
    private void setUpMapImages() {

        images.getChildren().clear();

        getMapImages();

        for (int i = 0; i < mapImages.size(); i++) {
            images.getChildren().add(mapImages.get(i));
        }
        images.setSpacing(50);
    }


    /*private void mapSelectScrolling(ScrollPane scroller, ScrollEvent e) {
        double movedDistance = e.getTotalDeltaX();
        if (Math.abs(movedDistance - THRESHOLD) > 0) {

            if (scroller.getHvalue() < oldHValue) {
                scroller.setHvalue(scroller.getHvalue() - scroller.getHmax() / mapImages.size());
            } else {
                scroller.setHvalue(scroller.getHvalue() + scroller.getHmax() / mapImages.size());
            }

            oldHValue = scroller.getHvalue();
        }
    }*/

    /**
     * Shows the map chooser, wait for user to click on a map
     */
    public void showChooser() {
        setUpMapImages();
        mapChooseStage.showAndWait();
    }

    /**
     * Gets the map chosen by the user once they click on it
     * @return
     */
    public String getChosenMap() {
        System.out.println(USER_MAP_DIRECTORY + chosenMap);
        return USER_MAP_DIRECTORY + chosenMap;
    }

    public void resetChosenMap() {
        chosenMap = "null";
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

            for (int i = 0; i < mapFiles.length; i++) {

                // Gets the images and maps
                Image mapImage = new Image(mapFiles[i].toURI().toString());
                String mapName = getDataOf(mapFiles[i]);
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
                b.setButtonProperties(newMap, "", 0, 0, e -> chooseMap(container), mapImageView);
                b.addHoverEffect3(newMap);

                container.getChildren().addAll(mapNameLabel, newMap);
                mapImages.add(container);
            }
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, "No custom map directory");
            MenuHandler.switchScene(MenuHandler.MAP_EDITOR);
        }
    }

    /**
     * Gets the actual data of the map (know the .png file name so just gets the .map file)
     * @param mapFile The string that denotes the file to get <name>.png
     * @return The .map file of the desired map
     */
    private String getDataOf(File mapFile) {

        String[] mapNameParts = mapFile.toURI().toString().split(SEPERATOR);
        String mapFileName = mapNameParts[mapNameParts.length - 1];

        String mapName = mapFileName.replace(".png", ".map");

        return mapName;
    }

    /**
     * Function is called when a 'map' button is pressed.
     * The chosenMap variable will be set to the name of the chosen map (which the button was associated with)
     * A new game will start with the chosen map
     * @param e The VBox which contain the button
     */
    private void chooseMap(VBox e) {
        int buttonIndex = mapImages.indexOf(e);
        chosenMap = mapNames.get(buttonIndex);
        ElementsHandler.startGame();
        mapChooseStage.hide();
    }
}
