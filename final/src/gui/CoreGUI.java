package gui;

import core.CoreEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import menus.MenuHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Saif Azmi; Contributors - Anh Pham, Dominic Walters, Evgeniy Kim, Hung Hoang, and Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          This is the class which will be run. It opens the initial gui window.
 * @date : 28/01/16
 */
public class CoreGUI extends Application {

    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());

    // Height of the GUI
    public static final Integer HEIGHT = 720;
    // Width of the GUI
    public static final Integer WIDTH = 1280;

    public static void main(String[] args) {

        // Start the GUI
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

        primaryStage.setOnCloseRequest(e -> {

            CoreEngine engine = CoreEngine.Instance();

            if (engine != null) {
                engine.setRunning(false);
            }

            LOG.log(Level.INFO, "Exiting...");
            System.exit(0);
        });
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
