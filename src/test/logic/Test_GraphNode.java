package test.logic;

import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import sceneElements.SpriteImage;

import java.util.ArrayList;

/**
 * Created by user on 12/03/2016.
 */
public class Test_GraphNode extends Test_Logic {

    private Graph graph = createGraph();

    /**
     * function for creating a graph for testing
     *
     * @return graph
     */
    public Graph createGraph() {


        Graph graph = new Graph();

        for (int x = 0; x < Graph.WIDTH; x++) {
            for (int y = 0; y < Graph.HEIGHT; y++) {
                GraphNode node = new GraphNode(x, y);
                graph.nodeWith(node);
            }
        }

        for (GraphNode aNode : graph.getNodes()) {
            aNode.addNeighbours(graph);
        }

        return graph;
    }

    /**
     * function for making a testing unit for the purpose of testing
     *
     * @param xStart the X-coordinate of the starting position of the unit
     * @param yStart the Y-coordinate of the starting position of the unit
     * @param xGoal  the X-coordinate of the goal of the unit
     * @param yGoal  the Y-coordinate of the goal of the unit
     */
    public void createUnit(Unit unit, int xStart, int yStart, int xGoal, int yGoal) {

        GraphNode startNode = graph.nodeWith(new GraphNode(xStart, yStart));
        GraphNode goalNode = graph.nodeWith((new GraphNode(xGoal, yGoal)));

        SpriteImage sprite = new SpriteImage(null, null);
        Unit newUnit = new Unit(11, "TestUnit", startNode, sprite, Unit.Search.A_STAR, Unit.Sort.BUBBLE, graph, goalNode);
        sprite.setEntity(unit);
        startNode.getUnits().add(unit);
        unit = newUnit;
    }

    /**
     * Testing function to check the successors getting mechanism of graph node
     * Check if the list of successors got from the mechanism is correct
     */
    @Test
    public void graphNodeGetSuccessors() {
        GraphNode node = graph.nodeWith(new GraphNode(4, 5));
        ArrayList<GraphNode> successors = (ArrayList<GraphNode>) node.getSuccessors();
        ArrayList<GraphNode> successors_check = new ArrayList<GraphNode>();
        successors_check.add(graph.nodeWith(new GraphNode(3, 5)));
        successors_check.add(graph.nodeWith(new GraphNode(4, 4)));
        successors_check.add(graph.nodeWith(new GraphNode(4, 6)));
        successors_check.add(graph.nodeWith(new GraphNode(5, 5)));

        Assert.assertEquals(successors, successors_check);
    }
}
