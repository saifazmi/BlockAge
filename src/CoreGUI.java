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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
			BorderPane root = new BorderPane();
			final Scene scene = new Scene(root, WIDTH, HEIGHT);
			primaryStage.setScene(scene);

			GameRunTime runTime = new GameRunTime(primaryStage);
			Test.test(runTime.getRenderer(), runTime.getEngine());

			primaryStage.setOnCloseRequest(e -> {
				runTime.getEngine().setEngineState(false);
				Platform.exit();
			});
            primaryStage.show();
        }
		catch (Exception e)
		{
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

}
