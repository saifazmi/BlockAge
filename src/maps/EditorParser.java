package maps;

import graph.Graph;
import graph.GraphNode;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hung on 05/03/16.
 */
public class EditorParser {

    private MapEditor editor;
    private String SAVE_DIRECTORY;
    private String IMAGE_DIRECTORY;
    private String RTS_DIRECTORY;
    private boolean overwrite = true;

    public EditorParser(MapEditor mapEditor) {
        String dir = System.getProperty("user.home");
        System.out.println(dir);
        String SEPERATOR = File.separator;
        RTS_DIRECTORY = dir + SEPERATOR + "bestRTS";
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "data" + SEPERATOR;
        IMAGE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR + "image" + SEPERATOR;
        System.out.println(SAVE_DIRECTORY);
        System.out.println(IMAGE_DIRECTORY);

        this.editor = mapEditor;
    }


    public void saveToUserFile() {
        String fileName = editor.getInterface().getFileName() + ".map";
        String imageName = editor.getInterface().getFileName() + ".png";

        Graph graph = editor.getGraph();

        if (fileName.equals("")) {
            editor.getInterface().getSaveStatusBox().setText("The map must have a name, please enter a name above");
        } else {

            if (invalidName(editor.getInterface().getFileName()))
                return;

            System.out.println("attempt save now");

            try {

                boolean newDirectory = false;

                // Create or directories for image and data if don't exist
                File rtsDir = new File(RTS_DIRECTORY);

                if (!rtsDir.exists()) {
                    newDirectory = true;
                    rtsDir.mkdir();
                }

                File saveDir = new File(SAVE_DIRECTORY);

                if (!saveDir.exists()) {
                    newDirectory = true;
                    saveDir.mkdir();
                }

                File imageDir = new File(IMAGE_DIRECTORY);

                if (!imageDir.exists()) {
                    imageDir.mkdir();
                }

                // Create actuall files, both image and map data
                File savedFile = new File(SAVE_DIRECTORY + fileName);
                File imageFile = new File(IMAGE_DIRECTORY + imageName);

                // pop up if data file already exist
                if (savedFile.exists()) {
                    editor.getInterface().getPopUpStage().showAndWait();
                }

                if (!overwrite)
                    return;

                // Begin writing data
                FileWriter fileWriter = new FileWriter(savedFile);

                BufferedWriter writer = new BufferedWriter(fileWriter);

                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 20; x++) {
                        if (x < 19) {
                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1 ");
                            } else {
                                writer.write("0 ");
                            }
                        } else {
                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1");
                            } else {
                                writer.write("0");
                            }
                        }
                    }
                    writer.newLine();
                }

                // Save Image

                WritableImage mapImage = editor.getRenderer().snapshot(new SnapshotParameters(), null);

                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(mapImage, null), "png", imageFile);
                } catch (IOException e) {
                    // just whatever
                }


                if (newDirectory) {
                    editor.getInterface().getSaveStatusBox().setText("New directory created at " + SAVE_DIRECTORY + ", map saved: " + fileName);
                }
                editor.getInterface().getSaveStatusBox().setText("map saved: " + SAVE_DIRECTORY + fileName);

                writer.close();

            } catch (IOException e) {
                editor.getInterface().getSaveStatusBox().setText("Save failed, game error");
                //notify user
            } finally {
                overwrite = true;
            }
        }
    }

    private boolean invalidName(String fileName) {
        if (fileName.contains(".")) {
            editor.getInterface().getSaveStatusBox().setText("File name can only be contain alphanumerical characters, no special symbols allowed");
            return true;
        }

        return false;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }
}
