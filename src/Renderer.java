import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class Renderer extends Group implements Observer
{
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    private Scene scene;
    private List<Entity> entities;

    private double xSpacing;
    private double ySpacing;

    public Renderer(Scene scene)
	{
        super();
        this.scene = scene;
        this.entities = new ArrayList<>();
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public boolean initialDraw()
	{
        boolean success = true;
        ArrayList<Double> results = calculateSpacing();
        int width = (int) (double) results.get(0);
        int height = (int) (double) results.get(1);
        double xSpacing = results.get(2);
        this.xSpacing = xSpacing;
        double ySpacing = results.get(3);
        this.ySpacing = ySpacing;
        double pixelWidth = results.get(4);
        double pixelHeight = results.get(5);
        success = success && drawLines(xSpacing, ySpacing, pixelWidth, pixelHeight, width, height);
        return success;
    }

    public boolean drawLines(double xSpacing, double ySpacing, double width, double height, int xAccumulator, int yAccumulator)
	{
        boolean success;
        for (int i = 0; i < xAccumulator + 1; i++)
		{
            Line line = new Line(xSpacing * i, 0, xSpacing * i, height);
            line.setStroke(Color.LIGHTGRAY);
            this.getChildren().add(line);
        }
        for (int i = 0; i < yAccumulator + 1; i++)
		{
            Line line = new Line(0, ySpacing * i, width, ySpacing * i);
            line.setStroke(Color.LIGHTGRAY);
            this.getChildren().add(line);
        }
        success = true;
        return success;
    }

    public ArrayList<Double> calculateSpacing()
    {
        ArrayList<Double> returnList = new ArrayList<>();
        double pixelWidth = scene.getWidth(); //subtract the right sidebar pixelWidth TODO @TODO//
        double pixelHeight = scene.getHeight(); //subtract the bottom bar height TODO @TODO//

        int width = Graph.WIDTH;
        int height = Graph.HEIGHT;
        double xSpacing = pixelWidth / (width);
        double ySpacing = pixelHeight / (height);

        returnList.add((double) width);
        returnList.add((double) height);
        returnList.add(xSpacing);
        returnList.add(ySpacing);
        returnList.add(pixelWidth);
        returnList.add(pixelHeight);
        return returnList;                            //ordered return list, see above for order
    }

    public void redraw()
    {
        this.getChildren().clear();
        initialDraw();
        entities.forEach(this::drawEntity);
    }

    public boolean drawEntity(Entity entity)
    {
        boolean success;
        if (!this.entities.contains(entity))
		{
            this.entities.add(entity);
            entity.addObserver(this);
        }

		SpriteImage sprite = entity.getSprite();
        GraphNode node = entity.getPosition();

		sprite.setFitWidth(xSpacing);
		sprite.setFitHeight(ySpacing);
		sprite.setPreserveRatio(true);
		sprite.setX(node.getX() * xSpacing);
		sprite.setY(node.getY() * ySpacing);
		success = this.getChildren().add(sprite);
        return success;
    }

    @Override
    public void update(Observable o, Object arg)
	{
        Entity entity = (Entity) o;
        Entity oldEntity = (Entity) arg;

        entities.remove(oldEntity);
        this.getChildren().remove(oldEntity.getSprite());
        entities.add(entity);
        drawEntity(entity);
    }

    public Group produceRouteVisual(List<GraphNode> route)
	{
        //test @TODO
        Group group = new Group();
		ObservableList<Node> groupKids = group.getChildren();
        for (int i = 0; i < route.size(); i++)
		{
            GraphNode start = route.get(i);
            if (i + 1 < route.size())
			{
                GraphNode end = route.get(i + 1);
                Line line = new Line(this.xSpacing / 2 + start.getX() * xSpacing,
									 this.ySpacing / 2 + start.getY() * ySpacing,
									 this.xSpacing / 2 + end.getX() * xSpacing,
						  			 this.ySpacing / 2 + end.getY() * ySpacing);
                if(!groupKids.contains(line))
				{
					groupKids.add(line);
				}
            }
			else
			{
                i = route.size();
            }
        }
        return group;
    }
}