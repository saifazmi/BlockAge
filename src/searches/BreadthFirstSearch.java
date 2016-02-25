package searches;

import graph.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hung on 06/02/16.
 */
public class BreadthFirstSearch {

    private LinkedList<GraphNode> frontier;
    private ArrayList<GraphNode> visited;
    private LinkedHashMap<GraphNode, GraphNode> possiblePath;
    private ArrayList<GraphNode> path;

    private static BreadthFirstSearch instance;

    /**
     * Singleton pattern for search
     * As there is only ever 1 instance of BFS,
     * it is efficient and safe to use the singleton pattern
     */
    public BreadthFirstSearch() {
        frontier = new LinkedList<>();
        visited = new ArrayList<>();
        possiblePath = new LinkedHashMap<>();
        path = new ArrayList<>();
    }

    public static BreadthFirstSearch Instance() {
        if (instance == null)
            instance = new BreadthFirstSearch();

        return instance;
    }

    /**
     * Finds a path from a start node to the end node using BFS, this utilises a queue which is just a linked list
     * The returned path should not include the start node.
     *
     * @param startNode node search starts from
     * @param endNode   node search terminates with, the goal node, usually player's base
     * @return path from start to goal node
     */
    public List<GraphNode> findPathFrom(GraphNode startNode, GraphNode endNode) {

        GraphNode current;
        GraphNode parent;
        possiblePath.clear();
        frontier.clear();
        visited.clear();
        path.clear();

        frontier.add(startNode);

        while (!frontier.isEmpty()) {

            current = frontier.poll();

            if (!visited.contains(current) && current.getBlockade() == null) {

                if (current.equals(endNode)) {

                    while (possiblePath.keySet().contains(current)) {

                        path.add(current);
                        parent = possiblePath.get(current);
                        current = parent;
                    }

                    Collections.reverse(path);
                    //System.out.println(path);
                    return path;
                } else {
                    visited.add(current);
                    frontier.addAll(current.getSuccessors());

                    for (GraphNode successor : current.getSuccessors()) {
                        if (!possiblePath.keySet().contains(successor) && !possiblePath.containsValue(successor))
                            possiblePath.put(successor, current);
                    }
                }
            }
        }

        return null;
    }

    public ArrayList<GraphNode> returnVisited() {
        return visited;
    }

}
