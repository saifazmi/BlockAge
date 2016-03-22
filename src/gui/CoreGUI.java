package gui;

import core.CoreEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menus.MenuHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : First created by Saif Amzi with code by Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, and Paul Popa
 * @date : 28/01/16, last edited by Dominic Walters and Paul Popa on 19/02/16
 */
public class CoreGUI extends Application {

    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());
    public static final Integer HEIGHT = 720;
    public static final Integer WIDTH = 1280;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This is the main stage that everything will be added on. 
     * Including: Main menu,Options Menu, Map Editor and the game itself.
     */
    @Override
    public void start(Stage primaryStage) {
        new MenuHandler(primaryStage);
        MenuHandler.switchScene(MenuHandler.MAIN_MENU);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(e -> {
            CoreEngine engine = CoreEngine.Instance();
            if (engine != null) {
                engine.setRunning(false);
            }
            LOG.log(Level.INFO, "Exiting...");
            System.exit(0);
        });
        primaryStage.show();
    }
}
