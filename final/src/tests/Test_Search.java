package tests;

import entity.Blockade;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import sceneElements.SpriteImage;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : Evgeniy
 * @project : bestRTS
 * @date : 07/02/16
 * <p>
 * Barebones search test.
 */
public class Test_Search extends Test_Logic {
    private Graph graph = createGraph();
    private ArrayList<Blockade> testBlockades;
    private Unit testUnit;

    /**
     * function for creating a graph for testing
     *
     * @return graph
     */
    public Graph createGraph() {

        this.testBlockades = new ArrayList<>();

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

        // set blockade at (10,10), (19,17), (17, 17), (17, 18), (17, 19)
        ArrayList<Integer> positions = new ArrayList<>(Arrays.asList(10, 10, 19, 17, 17, 17, 17, 18, 17, 19));
        for (int i = 0; i < positions.size() / 2; i++) {
            SpriteImage sprite = new SpriteImage(null, null);
            GraphNode node = graph.nodeWith(new GraphNode(positions.get(2 * i), positions.get(2 * i + 1)));
            Blockade blockade = new Blockade(i, "TestBlock", node, sprite);
            sprite.setEntity(blockade);
            blockade.getPosition().setBlockade(blockade);
            this.testBlockades.add(blockade);
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
    public void createUnit(int xStart, int yStart, int xGoal, int yGoal) {

        GraphNode startNode = graph.nodeWith(new GraphNode(xStart, yStart));
        GraphNode goalNode = graph.nodeWith((new GraphNode(xGoal, yGoal)));

        SpriteImage sprite = new SpriteImage(null, null);

        Unit newUnit;
        try {
            newUnit = new Unit(11, "TestUnit", startNode, sprite, Unit.Search.A_STAR, Unit.Sort.BUBBLE, graph, goalNode);
        } catch (ExceptionInInitializerError e) {
            newUnit = new Unit(11, "TestUnit", startNode, sprite, Unit.Search.A_STAR, Unit.Sort.BUBBLE, graph, goalNode);
        }

        sprite.setEntity(newUnit);
        startNode.getUnits().add(newUnit);
        this.testUnit = newUnit;
    }

    /**
     * Testing function of output of the breadth first search mechanism
     * Check if the route is valid
     */
    @Test()
    public void breadthFirstSearchTesting() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(19, 19));

        createUnit(1, 2, 19, 19);

        List<GraphNode> route = BreadthFirstSearch.findPathFrom(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }

    @Test()
    public void breadthFirstSearchTesting2() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(1, 1));

        createUnit(6, 7, 1, 1);

        List<GraphNode> route = BreadthFirstSearch.findPathFrom(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function of output of the depth first search mechanism
     * Check if the route is valid
     */
    @Test()
    public void depthFirstSearchTesting() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(19, 19));

        createUnit(2, 2, 19, 19);

        List<GraphNode> route = DepthFirstSearch.findPathFrom(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }

    @Test()
    public void depthFirstSearchTesting2() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(1, 1));

        createUnit(6, 7, 1, 1);

        List<GraphNode> route = DepthFirstSearch.findPathFrom(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function of output of the A-Star search mechanism
     * Check if the route is valid
     */
    @Test()
    public void aStarSearchTesting() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(19, 19));

        createUnit(3, 2, 19, 19);

        List<GraphNode> route = AStar.search(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }

    @Test()
    public void aStarSearchTesting2() {
        GraphNode endPoint = graph.nodeWith(new GraphNode(1, 1));

        createUnit(6, 7, 1, 1);

        List<GraphNode> route = AStar.search(testUnit, endPoint);

        boolean passed = validRoute(route, endPoint);

        Assert.assertEquals(passed, true);
    }


}
