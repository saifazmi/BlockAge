package maps;

import core.CoreEngine;
import entity.Blockade;
import graph.GraphNode;
import gui.Renderer;
import javafx.scene.image.Image;
import sceneElements.SpriteImage;
import stores.ImageStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by hung on 01/03/16.
 */

/**
 * This class will parse a .map file and generate blockades for the game runtime instance to match the data in the map
 */
public class MapParser {

    private static final Logger LOG = Logger.getLogger(MapParser.class.getName());

    private BufferedReader reader;
    private String row;

    public MapParser(BufferedReader reader) {

        this.reader = reader;
    }

    /**
     * Goes through the lines in the file and get the next integer,
     * if the integer is 1, create a blockade and
     * add it to the list of entities for the game run-time's renderer and core engine
     * otherwise don't do anything
     */
    public void generateBlockades() {

        int x = 0, y = 0;

        try {

            while ((row = reader.readLine()) != null) {
                while (row.length() > 0) {

                    int blockPresent = getNextInt();

                    if (blockPresent == 1) {

                        Blockade blockadeInstance = new Blockade(
                                1,
                                "Blockade",
                                new GraphNode(x, y),
                                null
                        );

                        Image image1 = ImageStore.unsortableImage1;

                        SpriteImage spriteImage1 = new SpriteImage(image1, blockadeInstance);
                        spriteImage1.setFitWidth(Renderer.Instance().getXSpacing());
                        spriteImage1.setFitHeight(Renderer.Instance().getYSpacing());
                        spriteImage1.setPreserveRatio(false);
                        spriteImage1.setSmooth(true);

                        blockadeInstance.setSprite(spriteImage1);
                        Blockade blockade = Blockade.randomBlockade(blockadeInstance);

                        if (blockade != null) {

                            Renderer.Instance().drawInitialEntity(blockade);
                            CoreEngine.Instance().getBlockades().add(blockade);
                        }
                    }

                    //sanity check
                    if (x < 20) {
                        x++;
                    }
                }

                x = 0;

                if (y < 20) {
                    y++;
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Get the next integer by getting the character parse it as an integer.
     *
     * @return the next integer in the file
     */
    private int getNextInt() {

        int i = 0;

        while (isSpace(row.charAt(i))) {
            row = row.substring(1);
        }

        int nextInt = Integer.parseInt(String.valueOf(row.charAt(i)));
        row = row.substring(1);

        return nextInt;
    }

    /**
     * Checks if character is a space
     *
     * @param c the character
     * @return whether the character passed is a space
     */
    private boolean isSpace(char c) {

        return c == ' ';
    }
}
