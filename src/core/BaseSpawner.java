package core;

import entity.Base;
import entity.Blockade;
import entity.SortableBlockade;
import graph.Graph;
import graph.GraphNode;
import gui.Renderer;
import javafx.scene.image.Image;
import maps.MapChooserInterface;
import maps.MapEditor;
import maps.MapParser;
import sceneElements.ElementsHandler;
import stores.ImageStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, and Hung Hoang
 * @date : 25/02/16, last edited by ___ on
 */
public class BaseSpawner {

    private static final Logger LOG = Logger.getLogger(BaseSpawner.class.getName());

    private Renderer renderer = Renderer.Instance();
    private GameRunTime runTime = GameRunTime.Instance();

    // may not be need
    private final String SEPARATOR = "/";
    private final String MAP_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "maps" + SEPARATOR;

    private GraphNode goal;

    private static BaseSpawner instance = null;

    public static BaseSpawner Instance() {
        if (instance == null) {
            instance = new BaseSpawner();
        }
        return instance;
    }

    public BaseSpawner() {
        instance = this;
        runTime.getScene().setOnMouseClicked(e -> {
            goal = Blockade.calcGraphNode(e);
            Base base = new Base(9999, "Base", goal, null);
            Image image = ImageStore.base;
            ImageStore.setSpriteProperties(base, image);
            renderer.drawInitialEntity(base);
            goal.setBase(base);
            runTime.setBasePlaced(true);
            // surround the base with sortable blockades.
            //protectBase(goal);

            //spawnBlockades(100);
            generateBlockades();

            runTime.getScene().setOnMouseClicked(null);
        });

    }

    private void generateBlockades() {

        InputStream in = null;
        File mapFile = null;

        String chosenMap = MapChooserInterface.Instance().getChosenMap();

        //assured because map will always end with .txt
        if (chosenMap.endsWith("null")) {
            Random mapRndGen = new Random();
            String map = MAP_RESOURCES + "map" + mapRndGen.nextInt(1) + ".txt";
            in = MapEditor.class.getResourceAsStream(map);
        } else {
            mapFile = new File(chosenMap);

            try {
                in = new FileInputStream(mapFile);
            } catch (FileNotFoundException e) {
                //error out
            }
        }

        Reader fReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(fReader);

        MapParser parser = new MapParser(reader);
        parser.generateBlockades();
    }

    private void protectBase(GraphNode base) {

        int row = base.getX();
        int col = base.getY();

        for (int i = (row - 1); i <= (row + 1); i++) {
            for (int j = (col - 1); j <= (col + 1); j++) {
                // coordinate should be on grid and not the same as the base
                if (isOnGrid(i, j) && !(i == row && j == col)) {

                    SortableBlockade sortableBlockadeInstance = new SortableBlockade(
                            0,
                            "Sortable Blockade",
                            new GraphNode(i, j),
                            null,
                            null
                    );

                    ImageStore.setSpriteProperties(sortableBlockadeInstance, ImageStore.sortableImage1);
                    SortableBlockade blockade = SortableBlockade.create(sortableBlockadeInstance);
                    if (blockade != null) {
                        renderer.drawInitialEntity(blockade);
                    }
                }
            }
        }
    }

    private boolean isOnGrid(int row, int col) {

        return !((row < 0 || col < 0 ||
                row >= Graph.HEIGHT || col >= Graph.WIDTH));
    }

    public GraphNode getGoal() {
        return goal;
    }

    private void spawnBlockades(int count) {
        if (ElementsHandler.options.getInitialBlockades()) {
            while (count > 0) {
                System.out.println("Blockade " + count);
                Random rand = new Random();
                int randomX = rand.nextInt(Graph.HEIGHT);
                int randomY = rand.nextInt(Graph.WIDTH);

                Blockade blockadeInstance = new Blockade(
                        1,
                        "Blockade",
                        new GraphNode(randomX, randomY),
                        null
                );

                ImageStore.setSpriteProperties(blockadeInstance, ImageStore.unsortableImage1);
                Blockade blockade = Blockade.randomBlockade(blockadeInstance);
                if (blockade != null) {
                    renderer.drawInitialEntity(blockade);
                    count--;
                }
            }
        }
    }
}
