package maps;

import graph.Graph;
import graph.GraphNode;
import javafx.event.ActionEvent;
import menus.MenuHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by hung on 05/03/16.
 */
public class EditorParser {

    private MapEditor editor;
    private String SAVE_DIRECTORY;
    private String SEPERATOR = File.separator;

    public EditorParser(MapEditor mapEditor) {
        String dir = System.getProperty("user.home");
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR;

        this.editor = mapEditor;
    }

    public void handle(ActionEvent event) {
        if (event.getSource() == MapEditorInterface.saveButton)
        {
            saveToUserFile();
        }
        if (event.getSource() == MapEditorInterface.backButton)
        {
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        }
    }

    private void saveToUserFile() {
        String fileName = editor.getInterface().getFileName() + ".txt";
        Graph graph = editor.getGraph();
        if (fileName.equals(""))
        {
            //handle by popup or watever
        }
        else
        {
            try {

                File saveDir = new File(SAVE_DIRECTORY);

                if (!saveDir.exists()) {
                    saveDir.mkdir();
                }

                File savedFile = new File(SAVE_DIRECTORY + fileName);

                FileWriter fileWriter = new FileWriter(savedFile);

                BufferedWriter writer = new BufferedWriter(fileWriter);

                for (int y = 0; y < 20; y++)
                {
                    for (int x = 0; x < 20; x++)
                    {
                        if (x < 19) {
                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1 ");
                            } else {
                                writer.write("0 ");
                            }
                        }
                        else
                        {
                            //does it matter?
                            if (graph.nodeWith(new GraphNode(x, y)).getBlockade() != null) {
                                writer.write("1");
                            } else {
                                writer.write("0");
                            }
                        }
                    }
                    writer.newLine();// work?
                }

                writer.close();
            } catch (IOException e) {
                System.out.println("save fail");
                System.out.println(e.getMessage());
                //notify user
            }
        }
    }
}
