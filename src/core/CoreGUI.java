package core;

import java.util.logging.Logger;

/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menus.MenuHandler;

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());
    private static CoreGUI instance;

    public static CoreGUI Instance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        @SuppressWarnings("unused")
        MenuHandler menuHandler = new MenuHandler(primaryStage);
        MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        primaryStage.setOnCloseRequest(e -> {
            CoreEngine engine = CoreEngine.Instance();
            if (engine != null) {
                CoreEngine.Instance().setRunning(false);
            }
            Platform.exit();
        });
        primaryStage.show();
    }

    public int getHEIGHT() {
        return 720;
    }

    public int getWIDTH() {
        return 1280;
    }
}
