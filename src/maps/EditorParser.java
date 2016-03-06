package maps;

import graph.Graph;
import graph.GraphNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import menus.MenuHandler;
import sceneElements.ButtonProperties;
import sceneElements.Images;

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
    private String SEPERATOR = File.separator;
    private Stage popUpStage;
    private boolean overwrite = true;
    private Image yesImage, noImage, yesImageHover, noImageHover;

    public EditorParser(MapEditor mapEditor) {

        setUpPopUp();

        String dir = System.getProperty("user.home");
        SAVE_DIRECTORY = dir + SEPERATOR + "bestRTS" + SEPERATOR;

        this.editor = mapEditor;
    }

    private void setUpPopUp() {
        popUpStage = new Stage();

        GridPane messagePanel = new GridPane();
        VBox layout = new VBox();
        HBox buttons = new HBox();

        Label message = new Label();
        Button yes = new Button();
        Button no = new Button();
        yesImage = Images.overwriteYes;
        noImage = Images.overwriteNo;
        yesImageHover = Images.overwriteYesHovered;
        noImageHover = Images.overwriteNoHovered;

        ButtonProperties b = new ButtonProperties();

        b.setButtonProperties(yes, "", 0, 0, event -> {
            overwrite = true;
            popUpStage.hide();
        }, new ImageView(yesImage));
        //b.addHoverEffect2(yes,yesImageHover,yesImage,0,0);

        b.setButtonProperties(no, "", 0, 0, event -> {
            overwrite = false;
            popUpStage.hide();
        }, new ImageView(noImage));
        //b.addHoverEffect2(no,noImageHover,noImage,0,0);

        buttons.getChildren().addAll(yes, no);
        layout.getChildren().addAll(message, buttons);

        message.setText("Overwrite existing map?");

        messagePanel.getChildren().addAll(layout);
        Scene popUpScene = new Scene(messagePanel);

        popUpStage.setScene(popUpScene);
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.hide();
        popUpStage.setOnCloseRequest(event -> {
            overwrite = false;
        });
    }

    public void saveToUserFile() {
        String fileName = editor.getInterface().getFileName() + ".txt";
        Graph graph = editor.getGraph();
        if (fileName.equals(""))
        {
            //handle by popup or watever
        }
        else
        {
            try {

                boolean newDirectory = false;

                File saveDir = new File(SAVE_DIRECTORY);

                if (!saveDir.exists()) {
                    newDirectory = true;
                    saveDir.mkdir();
                }

                File savedFile = new File(SAVE_DIRECTORY + fileName);

                if (savedFile.exists())
                {
                    popUpStage.showAndWait();
                }

                if (!overwrite)
                    return;


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
                    writer.newLine();
                }

                if (newDirectory)
                {
                    editor.getInterface().getSaveStatusBox().setText("New directory created at " + SAVE_DIRECTORY + ", map saved: " + fileName);
                }
                editor.getInterface().getSaveStatusBox().setText("map saved: " + SAVE_DIRECTORY + fileName);

                writer.close();

            } catch (IOException e) {
                editor.getInterface().getSaveStatusBox().setText("Save failed, game error");
                //notify user
            }
        }
    }
}
