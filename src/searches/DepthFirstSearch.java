package searches;

import graph.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * @author : First created by Hung Hoang with code by Hung Hoang
 * @date : 06/02/16, last edited by Hung Hoang on 25/02/16
 */
public class DepthFirstSearch {

    private Stack<GraphNode> frontier;
    private ArrayList<GraphNode> visited;

    private static DepthFirstSearch instance = null;

    /**
     * Singleton pattern for search
     * As there is only ever 1 instance of DFS,
     * it is efficient and safe to use the singleton pattern
     */
    public DepthFirstSearch() {
        visited = new ArrayList<>();
        frontier = new Stack<>();
    }

    public static DepthFirstSearch Instance() {
        if (instance == null)
            instance = new DepthFirstSearch();

        return instance;
    }

    /**
     * Finds a path from a start node to the end node using DFS, utilises a stack
     * The returned path should not include the start node
     *
     * @param startNode node search starts from
     * @param endNode   node search terminates with, the goal node, usually player's base
     * @return path from start to goal node
     */
    public List<GraphNode> findPathFrom(GraphNode startNode, GraphNode endNode) {

        GraphNode current;
        visited.clear();
        frontier.clear();

        frontier.push(startNode);

        ArrayList<GraphNode> path = new ArrayList<>();

        while (!frontier.isEmpty()) {
            current = frontier.pop();

            if (!visited.contains(current) && current.getBlockade() == null) {
                if (current.equals(endNode)) {
                    path.add(current);
                    path.remove(0);
                    return path;
                } else {
                    path.add(current);
                    visited.add(current);
                    frontier.clear();

                    current.getSuccessors().stream().filter(n -> !visited.contains(n)).forEach(n -> frontier.push(n));
                    /*
                        equivalent to:
                    for (GraphNode n : current.getSuccessors()) {
                        if (!visited.contains(n)) {
                            frontier.push(n);
                        }
                    }
                     */
                }
            }
        }
        visited.clear();

        return null;
    }

    public ArrayList<GraphNode> returnVisited() {
        return visited;
    }
}
