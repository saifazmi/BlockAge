package maps;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sceneElements.ButtonProperties;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hung on 06/03/16.
 */
public class MapChooserInterface {

    private static String chosenMap;
    private static String SAVE_DIRECTORY;
    private final String IMAGE_DIRECTORY;
    private final String SEPERATOR = File.separator;
    private static Stage mapChooseStage;
    private Scene mapChooseScene;
    private ArrayList<Button> mapImages;
    private ArrayList<String> mapNames;
    private double mapWidth;

    public MapChooserInterface()
    {
        mapChooseStage = new Stage();

        HBox images = new HBox();
        HBox names = new HBox();
        VBox container = new VBox();

        String dir = System.getProperty("user.home");
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "data" + SEPERATOR;
        IMAGE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "image" + SEPERATOR;

        getMapImages();

        for (int i = 0; i < mapImages.size(); i++)
        {
            images.getChildren().add(mapImages.get(i));
        }

        container.getChildren().addAll(images,names);
        container.setSpacing(50);

        ScrollPane scroller = new ScrollPane(container);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scroller.setMaxWidth(mapWidth);

        mapChooseScene = new Scene(scroller);
        mapChooseStage.setScene(mapChooseScene);
        mapChooseStage.initModality(Modality.APPLICATION_MODAL);
        mapChooseStage.hide();
    }

    public static void showChooser()
    {
        mapChooseStage.showAndWait();
    }

    public static String getChosenMap() {
        System.out.println(SAVE_DIRECTORY + chosenMap);
        return SAVE_DIRECTORY + chosenMap;
    }

    private void getMapImages() {

        mapImages = new ArrayList<>();
        mapNames = new ArrayList<>();

        ButtonProperties b = new ButtonProperties();

        File imageDir = new File(IMAGE_DIRECTORY);
        File[] mapFiles = imageDir.listFiles();

        for (int i = 0; i < mapFiles.length; i++)
        {
            Image mapImage = new Image(mapFiles[i].toURI().toString());
            mapNames.add(getDataOf(mapFiles[i]));
            setMaxWidth(mapImage);
            ImageView mapImageView = new ImageView(mapImage);
            Button newMap = new Button();
            b.setButtonProperties(newMap,"",0,0,e -> chooseMap(e), mapImageView);
            mapImages.add(newMap);
        }
    }

    private String getDataOf(File mapFile) {

        String[] mapNameParts = mapFile.toURI().toString().split(SEPERATOR);
        String mapFileName = mapNameParts[mapNameParts.length - 1];

        String mapName = mapFileName.replace(".png",".txt");

        return mapName;
    }

    private void setMaxWidth(Image mapImage) {
        if (mapImage.getWidth() > mapWidth)
            mapWidth = mapImage.getWidth();
    }

    private void chooseMap(ActionEvent e) {
        int buttonIndex = mapImages.indexOf(e.getSource());
        chosenMap = mapNames.get(buttonIndex);
        mapChooseStage.hide();
    }
}
