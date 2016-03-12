package test.logic;

import entity.Blockade;
import entity.Unit;
import graph.Graph;
import graph.GraphNode;
import org.testng.Assert;
import org.testng.annotations.Test;
import searches.AStar;
import searches.BreadthFirstSearch;
import searches.DepthFirstSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Evgeniy
 * @project : bestRTS
 * @date : 07/02/16
 * <p>
 * Barebones search test.
 */
public class Test_Search extends Test_Logic{
    private Graph graph = createGraph();
    private ArrayList<Blockade> testBlockades;
    private Unit testUnit;
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

}
