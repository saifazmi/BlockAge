import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;

public class Renderer extends Group
{
	private Scene scene;
	private final Graph gameMap;
	private List<Entity> entities;
	
	public Renderer(Scene scene, Graph gameMap, ArrayList<Entity> entities)
	{
		super();
		this.scene = scene;
		this.gameMap = gameMap;
		this.setEntities(entities);
	}
	public List<Entity> getEntities()
	{
		return entities;
	}
	public void setEntities(List<Entity> entities)
	{
		this.entities = entities;
	}
	
	public boolean drawPointMarks()	//UNTESTED//
	{
		boolean success = false;
		double radius = 1;
		
		ArrayList<Double> results = calculateSpacing();
		int xAccumulator = (int)(double)results.get(0);
		int yAccumulator = (int)(double)results.get(1);
		double xSpacing = results.get(2);
		double ySpacing = results.get(3);
		
		for(int i = 0; i < xAccumulator; i++)
		{
			for(int j = 0; j < yAccumulator; j++)
			{
				Circle point = new Circle(xSpacing/2 + i*xSpacing, ySpacing/2 + j*ySpacing, radius);
				this.getChildren().add(point);
			}
		}
		return success;
	}
	
	public ArrayList<Double> calculateSpacing() //UNTESTED TODO @TODO//
	{
		ArrayList<Double> returnList = new ArrayList<Double>();
		double width = scene.getWidth(); //subtract the right sidebar width TODO @TODO//
		double height = scene.getHeight(); //subtract the bottom bar height TODO @TODO//
		
		int xAccumulator = 0;
		int yAccumulator = 0;
		List<GraphNode> nodes = gameMap.getNodes();
		for(int i = 0; i < nodes.size(); i++)				//Assumption: Starts at (1,1), no missing GraphNodes//
		{
			GraphNode node = nodes.get(i);
			int xCount = node.getX();
			int yCount = node.getY();
			if(xCount > xAccumulator)
			{
				xAccumulator = xCount;
			}
			if(yCount > yAccumulator)
			{
				yAccumulator = yCount;
			}
		}
		
		double xSpacing = width / xAccumulator;
		double ySpacing = width / yAccumulator;
		
		returnList.add((double)xAccumulator);
		returnList.add((double)yAccumulator);
		returnList.add(xSpacing);
		returnList.add(ySpacing);
		return returnList;							//ordered return list
	}
}