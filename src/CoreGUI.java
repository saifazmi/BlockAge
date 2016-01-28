/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CoreGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 1280, 720);
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
            e.printStackTrace();
        }
    }

}
