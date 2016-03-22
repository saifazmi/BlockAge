package tests;

import entity.Blockade;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anh on 26/02/2016.
 * Testing class for the purpose of testing the output of different functions of different class
 */
public class Test_Unit extends Test_Logic {
    //@TODO: Fix the tests with proper methods.

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
     * Testing function of the block checking mechanism of unit class
     */
    @Test
    public void unitBlockCheck() {

        createUnit(3, 2, 19, 19);

        boolean blockade = true;

        for (int i = 0; i < testBlockades.size() && blockade; i++) {
            blockade = !testUnit.blockCheck(testBlockades.get(i).getPosition());
        }
        Assert.assertEquals(blockade, true);
    }

    /**
     * Testing function of the finding route mechanism of unit class
     * Check if the route created is valid
     */
    @Test
    public void unitDecideRoute() {

        createUnit(0, 0, 19, 19);
        testUnit.setRoute(null);
        testUnit.decideRoute();
        List<GraphNode> route = testUnit.getRoute();

        boolean passed = validRoute(route, graph.nodeWith(new GraphNode(19, 19)));

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function to check the moving left mechanism of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitMoveLeftNoBlock() {

        createUnit(5, 5, 19, 19);
        testUnit.moveLeft();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(4, 5)));
    }

    /**
     * Testing function to check the moving right mechanism of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitMoveRightNoBlock() {

        createUnit(5, 5, 19, 19);
        testUnit.moveRight();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(6, 5)));
    }

    /**
     * Testing function to check the moving up mechanism of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitMoveUpNoBlock() {

        createUnit(5, 5, 19, 19);
        testUnit.moveUp();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(5, 4)));
    }

    /**
     * Testing function to check the moving down mechanism of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitMoveDownNoBlock() {

        createUnit(5, 5, 19, 19);
        testUnit.moveDown();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(5, 6)));
    }

    /**
     * Testing function to check the moving left mechanism of the unit with block
     * Check if the unit react correctly (do not move and stay in the same place
     */
    @Test()
    public void unitMoveLeftWithBlock() {
        createUnit(11, 10, 19, 19);
        testUnit.moveLeft();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(11, 10)));
    }

    /**
     * Testing function to check the moving right mechanism of the unit with block
     * Check if the unit react correctly (do not move and stay in the same place
     */
    @Test()
    public void unitMoveRightWithBlock() {
        createUnit(9, 10, 19, 19);
        testUnit.moveRight();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(9, 10)));
    }

    /**
     * Testing function to check the moving up mechanism of the unit with block
     * Check if the unit react correctly (do not move and stay in the same place
     */
    @Test()
    public void unitMoveUpWithBlock() {
        createUnit(10, 11, 19, 19);
        testUnit.moveUp();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(10, 11)));
    }

    /**
     * Testing function to check the moving down mechanism of the unit with block
     * Check if the unit react correctly (do not move and stay in the same place
     */
    @Test()
    public void unitMoveDownWithBlock() {
        createUnit(10, 9, 19, 19);
        testUnit.moveDown();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(10, 9)));
    }

    /**
     * Testing function to check the update function of moving left of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitUpdateMoveLeftNoBlock() {
        createUnit(5, 5, 19, 19);
        GraphNode nextNode = graph.nodeWith(new GraphNode(4, 5));
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(nextNode)));
        testUnit.update();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(4, 5)));
    }

    /**
     * Testing function to check the update function of moving right of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitUpdateMoveRightNoBlock() {
        createUnit(5, 5, 19, 19);
        GraphNode nextNode = graph.nodeWith(new GraphNode(6, 5));
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(nextNode)));
        testUnit.update();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(6, 5)));
    }

    /**
     * Testing function to check the update function of moving up of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitUpdateMoveUpNoBlock() {
        createUnit(5, 5, 19, 19);
        GraphNode nextNode = graph.nodeWith(new GraphNode(5, 4));
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(nextNode)));
        testUnit.update();
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(5, 4)));
    }

    /**
     * Testing function to check the update function of moving down of the unit without block
     * Check if the position of the unit changes correctly
     */
    @Test()
    public void unitUpdateMoveDownNoBlock() {
        createUnit(5, 5, 19, 19);
        GraphNode nextNode = graph.nodeWith(new GraphNode(5, 6));
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(nextNode)));
        try {
            testUnit.update();
        } catch (ExceptionInInitializerError e) {
            e.printStackTrace();
        }
        GraphNode position = testUnit.getPosition();

        Assert.assertEquals(position, graph.nodeWith(new GraphNode(5, 6)));
    }

    /**
     * Testing function to check the update function of moving left of the unit with block
     * Check if the unit react correctly (do not move and recalculate a valid route)
     */
    @Test()
    public void unitUpdateMoveLeftWithBlock() {
        createUnit(11, 10, 19, 19);
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(testBlockades.get(0).getPosition())));
        testUnit.update();
        List<GraphNode> route = testUnit.getRoute();

        boolean passed = validRoute(route, graph.nodeWith(new GraphNode(19, 19)));

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function to check the update function of moving right of the unit with block
     * Check if the unit react correctly (do not move and recalculate a valid route)
     */
    @Test()
    public void unitUpdateMoveRightWithBlock() {
        createUnit(9, 10, 19, 19);
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(testBlockades.get(0).getPosition())));
        testUnit.update();
        List<GraphNode> route = testUnit.getRoute();

        boolean passed = validRoute(route, graph.nodeWith(new GraphNode(19, 19)));

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function to check the update function of moving up of the unit with block
     * Check if the unit react correctly (do not move and recalculate a valid route)
     */
    @Test()
    public void unitUpdateMoveUpWithBlock() {
        createUnit(10, 11, 19, 19);
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(testBlockades.get(0).getPosition())));
        testUnit.update();
        List<GraphNode> route = testUnit.getRoute();

        boolean passed = validRoute(route, graph.nodeWith(new GraphNode(19, 19)));

        Assert.assertEquals(passed, true);
    }

    /**
     * Testing function to check the update function of moving down of the unit with block
     * Check if the unit react correctly (do not move and recalculate a valid route)
     */
    @Test()
    public void unitUpdateMoveDownWithBlock() {
        createUnit(10, 9, 19, 19);
        testUnit.setRoute(new ArrayList<GraphNode>(Arrays.asList(testBlockades.get(0).getPosition())));
        testUnit.update();
        List<GraphNode> route = testUnit.getRoute();

        boolean passed = validRoute(route, graph.nodeWith(new GraphNode(19, 19)));

        Assert.assertEquals(passed, true);
    }
}

