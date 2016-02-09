import javafx.scene.shape.Circle;

/**
 * Created by Dominic on 05/02/2016.
 */
public class Test
{
	public static void test(Renderer renderer, CoreEngine engine)
	{
		Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), new Circle(0, 0, 10));
		//unit//
		SpriteImage sprite = new SpriteImage("http://mail.rsgc.on.ca/~ldevir/ICS3U/Chapter4/Images/tux.png", null);
		sprite.setOnMouseClicked(e ->
		{
			sprite.requestFocus();
		});
		Unit unit = new Unit(0, "testUnit", new GraphNode(3, 1), sprite, null, engine.getGraph());
		sprite.setEntity(unit);
		//unit end//
		renderer.drawEntity(block);
		renderer.drawEntity(unit);
	}
}