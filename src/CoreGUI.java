/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreGUI extends Application {
    private static final Logger LOG = Logger.getLogger(CoreGUI.class.getName());

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
            //@TODO: exit the program properly.
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            Thread engThread = new Thread(() -> {
                engine = new CoreEngine();
                engine.startGame();
            });
            engThread.start();
            while (engine == null) {
                Thread.sleep(1);
            }
            final Renderer renderer = new Renderer(scene, engine.getGraph(), null);
            scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
                renderer.redraw();
            });
            scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
                renderer.redraw();
            });
            root.setCenter(renderer);
            renderer.draw();
            Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), new Circle(0, 0, 10));
            Unit unit = new Unit(0, "testUnit", new GraphNode(3, 1), new Circle(10), null, this.engine.getGraph());
            renderer.drawEntity(block);
            renderer.drawEntity(unit);
            primaryStage.show();
//            unit.moveUp();
//            unit.moveLeft();
//            unit.moveRight();
//            unit.moveDown();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

}
