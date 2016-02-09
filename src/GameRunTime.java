import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.logging.Logger;

/**
 * Created by Dominic on 09/02/2016.
 */
public class GameRunTime
{
	private static final Logger LOG = Logger.getLogger(GameRunTime.class.getName());

	private Renderer renderer;
	private CoreEngine engine;
	private Stage primaryStage;

	public GameRunTime(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		Thread engThread = new Thread(() ->
		{
			this.engine = new CoreEngine();
			this.engine.startGame();
		});
		engThread.start();
		while (this.engine == null)
		{
			try
			{
				Thread.sleep(1);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		final Renderer renderer = new Renderer(primaryStage.getScene(), this.engine.getGraph(), null);
	}
	public Renderer getRenderer()
	{
		return renderer;
	}
	public CoreEngine getEngine()
	{
		return engine;
	}

	private void rendererSpecificInit()
	{
		primaryStage.getScene().widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
			renderer.redraw();
		});
		primaryStage.getScene().heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
			renderer.redraw();
		});
		((BorderPane)primaryStage.getScene().getRoot()).setCenter(renderer);
		renderer.initialDraw();
	}
}