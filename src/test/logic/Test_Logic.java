package test.logic;

import entity.Blockade;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import sceneElements.SpriteImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 12/03/2016.
 */
public class Test_Logic {
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
        Unit unit = new Unit(11, "TestUnit", startNode, sprite, Unit.Search.A_STAR, Unit.Sort.BUBBLE, graph, goalNode);
        sprite.setEntity(unit);
        startNode.getUnits().add(unit);
        this.testUnit = unit;
    }

    /**
     * Testing function to check if the route given is valid
     * Check if the route is directing towards the goal given
     * Check if the route moving sequence of graph node is movable (move one 1 unit at a time)
     *
     * @param route
     * @param goal
     * @return valid true if the route is valid, false otherwise
     */
    public boolean validRoute(List<GraphNode> route, GraphNode goal) {
        boolean valid = true;

        // check if the route is directing a the goal
        if (route.get(route.size() - 1) != goal) {
            valid = false;
        }

        // check if the nodes are not blockade
        for (int i = 0; i < route.size() && valid; i++) {
            GraphNode node = route.get(i);
            if (node.getBlockade() != null) {
                valid = false;
            }
            // check if it is possible to move from one node to the next node in the route.
            else if (i < (route.size() - 1)) {
                List<GraphNode> successors = node.getSuccessors();
                if (!successors.contains(route.get(i + 1))) {
                    valid = false;
                }
            }
        }

        return valid;
    }
}
