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
import sceneElements.ButtonProperties;
import sceneElements.ElementsHandler;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by hung on 06/03/16.
 */
public class MapChooserInterface {

    private static final double THRESHOLD = 100;
    private static String chosenMap;
    private static String SAVE_DIRECTORY;
    private final String IMAGE_DIRECTORY;
    private final String SEPERATOR = File.separator;
    private static Stage mapChooseStage;
    private Scene mapChooseScene;
    private HBox images;
    private ArrayList<VBox> mapImages;
    private ArrayList<String> mapNames;
    private double oldHValue = 0;

    private static MapChooserInterface instance;

    public static MapChooserInterface Instance()
    {
        if (instance == null)
            instance = new MapChooserInterface();

        return instance;
    }

    private MapChooserInterface()
    {
        mapChooseStage = new Stage();

        images = new HBox();

        String dir = System.getProperty("user.home");
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "data" + SEPERATOR;
        IMAGE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "image" + SEPERATOR;

        setUpMapImages();

        ScrollPane scroller = new ScrollPane(images);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroller.setPrefSize(820,650);
        //scroller.setOnScroll(e -> mapSelectScrolling(scroller, e));

        mapChooseScene = new Scene(scroller);
        mapChooseStage.setScene(mapChooseScene);
        mapChooseStage.initModality(Modality.APPLICATION_MODAL);
        mapChooseStage.hide();
    }

    private void setUpMapImages() {

        images.getChildren().clear();

        getMapImages();

        for (int i = 0; i < mapImages.size(); i++)
        {
            images.getChildren().add(mapImages.get(i));
        }

        images.setSpacing(50);
    }

    private void mapSelectScrolling(ScrollPane scroller, ScrollEvent e) {
        double movedDistance = e.getTotalDeltaX();
        if (Math.abs(movedDistance - THRESHOLD) > 0)
        {

            if (scroller.getHvalue() < oldHValue)
            {
                scroller.setHvalue(scroller.getHvalue() - scroller.getHmax()/mapImages.size());
            }
            else
            {
                scroller.setHvalue(scroller.getHvalue() + scroller.getHmax()/mapImages.size());
            }

            oldHValue = scroller.getHvalue();
        }
    }

    public void showChooser()
    {
        setUpMapImages();
        mapChooseStage.showAndWait();
    }

    public String getChosenMap() {
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
            String mapName = getDataOf(mapFiles[i]);
            mapNames.add(mapName);

            ImageView mapImageView = new ImageView(mapImage);
            mapImageView.setPreserveRatio(true);
            mapImageView.setFitHeight(600);


            Label mapNameLabel = new Label(mapName);
            VBox container = new VBox();
            container.setAlignment(Pos.CENTER);

            Button newMap = new Button();
            b.setButtonProperties(newMap,"",0,0,e -> chooseMap(container), mapImageView);
            b.addHoverEffect3(newMap);

            container.getChildren().addAll(mapNameLabel,newMap);
            mapImages.add(container);
        }
    }

    private String getDataOf(File mapFile) {

        String[] mapNameParts = mapFile.toURI().toString().split(SEPERATOR);
        String mapFileName = mapNameParts[mapNameParts.length - 1];

        String mapName = mapFileName.replace(".png",".txt");

        return mapName;
    }

    private void chooseMap(VBox e) {
        int buttonIndex = mapImages.indexOf(e);
        chosenMap = mapNames.get(buttonIndex);
        ElementsHandler.startGame();
        mapChooseStage.hide();
    }
}
