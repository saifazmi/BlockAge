/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setScene(scene);
            Thread engThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    CoreEngine engine = new CoreEngine();
                    engine.startGame();
                }
            });
            engThread.start();
            primaryStage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

}
