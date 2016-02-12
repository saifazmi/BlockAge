package core;

/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import menus.MenuHandler;
import test.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public static Stage primaryStage;
    /**
     * Made CoreGUI follow Singleton Pattern because there is ever only 1 instance of it
     * Allows easier access
     */
    private static CoreGUI instance;

    public static CoreGUI Instance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.instance = this;

        try {
            CoreGUI.primaryStage = primaryStage;
            BorderPane root = new BorderPane();

            @SuppressWarnings("unused")
            MenuHandler menuHandler = new MenuHandler(primaryStage);
            MenuHandler.switchScene(MenuHandler.MAIN_MENU);

            primaryStage.setOnCloseRequest(e -> {
                CoreEngine.setEngineState(false);
                Platform.exit();
            });

            primaryStage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }
}
