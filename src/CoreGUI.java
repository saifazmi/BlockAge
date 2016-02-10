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
    // made renderer a field due to need to call it
    private Renderer renderer;

    /**
     * Made CoreGUI follow Singleton Pattern because there is ever only 1 instance of it
     * Allows easier access
     */
    private static CoreGUI instance;

    public static CoreGUI Instance()
    {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.instance = this;

        try {
            BorderPane root = new BorderPane();
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setScene(scene);
            //@TODO: exit the program properly.
            primaryStage.setOnCloseRequest(e -> {
                engine.setEngineState(false);
                Platform.exit();
            });
            Thread engThread = new Thread(() -> {
                engine = new CoreEngine();
                engine.startGame();
            });
            engThread.start();
            while (engine == null) {
                Thread.sleep(1);
            }
            renderer = new Renderer(scene, engine.getGraph(), null);
            scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
                renderer.redraw();
            });
            scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
                renderer.redraw();
            });
            root.setCenter(renderer);
            renderer.initialDraw();

            engine.setEntities(renderer.getEntities());

            Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), new Circle(0, 0, 10));
            SpriteImage sprite = new SpriteImage("http://mail.rsgc.on.ca/~ldevir/ICS3U/Chapter4/Images/tux.png", null);
            //dirty code
            sprite.setOnMouseClicked(e ->
            {
                if (scene.getFocusOwner() != null && scene.getFocusOwner().equals(sprite)) {
                    System.out.println("Has focus");
                } else {
                    sprite.requestFocus();
                    if (scene.getFocusOwner().equals(sprite)) {
                        System.out.println("Gained focus");
                    }
                }
            });
            //dirty code end
            Unit unit = new Unit(0, "testUnit", new GraphNode(3, 1), sprite, null, this.engine.getGraph());
            sprite.setEntity(unit);
            unit.setCurrentPixel(sprite.getX(),sprite.getY());
            renderer.drawEntity(block);
            renderer.drawEntity(unit);
            primaryStage.show();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    public Renderer returnRenderer()
    {
        return renderer;
    }

}
