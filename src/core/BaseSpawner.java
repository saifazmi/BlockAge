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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, and Hung Hoang
 * @date : 25/02/16, last edited by ___ on
 */
public class BaseSpawner {

    private static final Logger LOG = Logger.getLogger(BaseSpawner.class.getName());

    // Map directory
    private final String SEPARATOR = "/";
    private final String MAP_RESOURCES = SEPARATOR + "resources" + SEPARATOR + "maps" + SEPARATOR;
    // Number of preset maps
    private final int MAP_PRESETS_QTY = 3;

    private Renderer renderer = Renderer.Instance();
    private GameRunTime runTime = GameRunTime.Instance();
    private GraphNode goal;

    // Instance for singleton.
    private static BaseSpawner instance = null;

    /**
     * Implements Singleton for this class (Only one can exist).
     *
     * @return the only base spawner to be created.
     */
    public static BaseSpawner Instance() {

        if (instance == null) {
            instance = new BaseSpawner();
        }
        return instance;
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    /**
     * Builds a base for the player
     */
    private BaseSpawner() {

        runTime.getScene().setOnMouseClicked(e -> {

            this.goal = Blockade.calcGraphNode(e);
            if (goal != null) {
                Base base = new Base(9999, "Base", goal, null);
                Image image = ImageStore.base;

                ImageStore.setSpriteProperties(base, image);
                this.renderer.drawInitialEntity(base);
                this.goal.setBase(base);
                this.runTime.setBasePlaced(true);

                protectBase(goal);
                generateBlockades();

                this.runTime.getScene().setOnMouseClicked(null);
            }
        });
    }

    /**
     * Getter function for base location.
     *
     * @return GraphNode location of base.
     */
    public GraphNode getGoal() {
        return goal;
    }

    /**
     * Checks if a given coordinate in on the grid.
     *
     * @param row index value of row.
     * @param col index value of column.
     * @return true if the coordinates are on board else false.
     */
    private boolean isOnGrid(int row, int col) {

        return !((row < 0 || col < 0 ||
                row >= Graph.HEIGHT || col >= Graph.WIDTH));
    }

    /**
     * Generates initial blockades based on a random preset map.
     */
    private void generateBlockades() {

        InputStream in = null;
        File mapFile = null;
        String chosenMap = MapChooserInterface.Instance().getChosenMap();

        //assured because map will always end with .map
        if (chosenMap.endsWith("null")) {

            Random mapRndGen = new Random();
            String map = MAP_RESOURCES + mapRndGen.nextInt(MAP_PRESETS_QTY) + ".map";
            in = MapEditor.class.getResourceAsStream(map);

        } else {

            mapFile = new File(chosenMap);

            try {
                in = new FileInputStream(mapFile);
            } catch (FileNotFoundException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
        }

        Reader fReader = null;
        if (in != null) {
            fReader = new InputStreamReader(in);
        }

        BufferedReader reader = null;
        if (fReader != null) {
            reader = new BufferedReader(fReader);
        }

        MapParser parser = new MapParser(reader);
        parser.generateBlockades();
    }

    /**
     * Surrounds the base with sortable blockades.
     *
     * @param base location of the base.
     */
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
                    //System.out.println(blockade.arrayToString(blockade.getToSortArray()));
                    if (blockade != null) {
                        renderer.drawInitialEntity(blockade);
                        blockade.getSprite().setOnMouseClicked(f -> blockade.getSortVisual().display(true));
                        CoreEngine.Instance().getEntities().add(blockade);
                    }
                }
            }
        }
    }

    /**
     * Generates random initial blockades.
     *
     * @param count number of random blockades.
     */
    @Deprecated
    private void spawnBlockades(int count) {

        if (ElementsHandler.options.getInitialBlockades()) {

            while (count > 0) {

                LOG.log(Level.INFO, "Blockade " + count);
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
