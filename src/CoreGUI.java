/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
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
            while (engine == null) {
                System.out.println("IN THE WHILE!!!!!");
            }
            Renderer renderer = new Renderer(scene, engine.getGraph(), null);
            scene.widthProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                	renderer.redraw();
                }
            });
            scene.heightProperty().addListener(new ChangeListener<Number>() {
                @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                    renderer.redraw();
                }
            });
            root.setCenter(renderer);
            renderer.draw();
            Blockade block = new Blockade(0, "block", "desc", new GraphNode(2,2) , new Circle(0,0, 10));
            renderer.drawEntity(block);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
