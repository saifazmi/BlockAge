/**
 * @project : bestRTS
 * @author : saif
 * @date : 28/01/16
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
			primaryStage.setOnCloseRequest(e -> {
				this.engine.setEngineState(false);
				Platform.exit();
			});

			Thread engThread = new Thread(() -> {
				this.engine = new CoreEngine();
				this.engine.startGame();
			});
			engThread.start();
			while (this.engine == null) {
				Thread.sleep(1);
			}

			final Renderer renderer = new Renderer(scene, this.engine.getGraph(), null);
			scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
				renderer.redraw();
			});
			scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
				renderer.redraw();
			});
			root.setCenter(renderer);
			renderer.initialDraw();

			Test.test(renderer, this.engine);
            primaryStage.show();
        }
		catch (Exception e)
		{
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

}
