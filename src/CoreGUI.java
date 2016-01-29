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

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    private CoreEngine engine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            BorderPane root = new BorderPane();
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setScene(scene);
            Thread engThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    engine = new CoreEngine();
                    engine.startGame();
                }
            });
            engThread.start();
            //System.out.println("Before WHILE");
            while (engine == null) {
                System.out.println("IN THE WHILE!!!!!");
            }
            //System.out.println("After WHILE");

            Renderer renderer = new Renderer(scene, engine.getGraph(), null);
            root.setCenter(renderer);
            renderer.drawPointMarks();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
