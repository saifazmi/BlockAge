import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Renderer extends Group {
    private static final Logger LOG = Logger.getLogger(Renderer.class.getName());

    private Scene scene;
    private final Graph gameMap;
    private List<Entity> entities;

    private double xSpacing = 0;
    private double ySpacing = 0;

    public Renderer(Scene scene, Graph gameMap, ArrayList<Entity> entities) {
        super();
        this.scene = scene;
        this.gameMap = gameMap;
        this.entities = new ArrayList<Entity>();
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public boolean draw() {
        boolean success = true;
        ArrayList<Double> results = calculateSpacing();
        int xAccumulator = (int) (double) results.get(0);
        int yAccumulator = (int) (double) results.get(1);
        double xSpacing = results.get(2);
        this.xSpacing = xSpacing;
        double ySpacing = results.get(3);
        this.ySpacing = ySpacing;
        double width = results.get(4);
        double height = results.get(5);
        success = success && drawLines(xSpacing, ySpacing, width, height, xAccumulator, yAccumulator);
        //success = success && drawPointMarks(xSpacing, ySpacing, width, height, xAccumulator, yAccumulator);
        return success;
    }

    public boolean drawPointMarks(double xSpacing, double ySpacing, double width, double height, int xAccumulator, int yAccumulator)    //UNTESTED//
    {
        boolean success = false;
        double radius = 1;
        for (int i = 0; i < xAccumulator; i++) {
            for (int j = 0; j < yAccumulator; j++) {
                Circle point = new Circle(xSpacing / 2 + i * xSpacing, ySpacing / 2 + j * ySpacing, radius);
                // LOG.log(Level.INFO, "Circle: " + point.getCenterX() + ", " + point.getCenterY());
                this.getChildren().add(point);
            }
        }
        success = true;
        return success;
    }

    public boolean drawLines(double xSpacing, double ySpacing, double width, double height, int xAccumulator, int yAccumulator) {
        boolean success = false;
        for (int i = 0; i < xAccumulator + 1; i++) {
            Line line = new Line(xSpacing * i, 0, xSpacing * i, height);
            line.setStroke(Color.LIGHTGRAY);
            this.getChildren().add(line);
        }
        for (int i = 0; i < yAccumulator + 1; i++) {
            Line line = new Line(0, ySpacing * i, width, ySpacing * i);
            line.setStroke(Color.LIGHTGRAY);
            this.getChildren().add(line);
        }
        success = true;
        return success;
    }

    public ArrayList<Double> calculateSpacing() //UNTESTED TODO @TODO//
    {
        ArrayList<Double> returnList = new ArrayList<Double>();
        double width = scene.getWidth(); //subtract the right sidebar width TODO @TODO//
        double height = scene.getHeight(); //subtract the bottom bar height TODO @TODO//
        // LOG.log(Level.INFO, "Screen Sizes: " + width + ", " + height);

        int xAccumulator = 0;
        int yAccumulator = 0;
        List<GraphNode> nodes = gameMap.getNodes();
        for (int i = 0; i < nodes.size(); i++)                //Assumption: Starts at (0,0), no missing GraphNodes//
        {
            GraphNode node = nodes.get(i);
            int xCount = node.getX();
            int yCount = node.getY();
            if (xCount > xAccumulator) {
                xAccumulator = xCount;
            }
            if (yCount > yAccumulator) {
                yAccumulator = yCount;
            }
        }

        double xSpacing = width / (xAccumulator + 1);
        double ySpacing = height / (yAccumulator + 1);

        returnList.add((double) xAccumulator + 1);
        returnList.add((double) yAccumulator + 1);
        returnList.add(xSpacing);
        returnList.add(ySpacing);
        returnList.add(width);
        returnList.add(height);
        return returnList;                            //ordered return list, see above for order
    }

    public void redraw() {
        this.getChildren().clear();
        draw();
        for (Entity entity : entities) {
            drawEntity(entity);
        }
    }

    public boolean drawEntity(Entity entity) {
        boolean success = false;
        if (!this.entities.contains(entity)) {
            this.entities.add(entity);
        }
        Node sprite = entity.getSprite();
        if (sprite instanceof Circle) {
            Circle circle = (Circle) sprite;
            GraphNode node = entity.getPosition();
            int xCoord = node.getX();
            int yCoord = node.getY();
            circle.setCenterX(this.xSpacing / 2 + this.xSpacing * (xCoord));
            circle.setCenterY(this.ySpacing / 2 + this.ySpacing * (yCoord));
            if (xSpacing > ySpacing) {
                circle.setRadius(ySpacing / 2);
            } else {
                circle.setRadius(xSpacing / 2);
            }

            this.getChildren().add(circle);
        } else if (sprite instanceof Rectangle) {

        } else if (sprite instanceof ImageView) {

        } else if (sprite instanceof StackPane) {

        }

        return success;
    }
}