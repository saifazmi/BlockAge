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

/**
 * Created by hung on 01/03/16.
 */
public class MapParser {

    private BufferedReader reader;
    private String row;

    //args is portability, teacher can just give student map files
    public MapParser(BufferedReader reader) {
        this.reader = reader;
    }

    public void generateBlockades() {

        int x = 0, y = 0;

        try {

            while ((row = reader.readLine()) != null) {
                while (row.length() > 0) {
                    int blockPresent = getNextInt();

                    if (blockPresent == 1) {
                        Blockade blockadeInstance = new Blockade(1, "Blockade", new GraphNode(x, y), null);
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
                            CoreEngine.Instance().getEntities().add(blockade);
                        }
                    }

                    //sanity check
                    if (x < 20)
                        x++;
                }

                x = 0;

                if (y < 20)
                    y++;
                else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getNextInt() {
        int i = 0;

        while (isSpace(row.charAt(i))) {
            row = row.substring(1);
        }

        int nextInt = Integer.parseInt(String.valueOf(row.charAt(i)));

        row = row.substring(1);

        return nextInt;
    }

    private boolean isSpace(char c) {
        return c == ' ';
    }
}
