package searches;

import entity.Unit;
import graph.GraphNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by hung on 06/02/16.
 */
public class DepthFirstSearch {
    /**
     * Finds a path from a start node to the end node using DFS, utilises a stack
     * The returned path should not include the start node
     *
     * @param unit    the unit to search for
     * @param endNode node search terminates with, the goal node, usually player's base
     * @return path from start to goal node
     */
    public static List<GraphNode> findPathFrom(Unit unit, GraphNode endNode) {

        Stack<GraphNode> frontier = new Stack<>();
        ArrayList<GraphNode> visited = new ArrayList<>();
        LinkedHashMap<GraphNode, GraphNode> possiblePath = new LinkedHashMap<>();
        ArrayList<GraphNode> path = new ArrayList<>();
        GraphNode current;
        GraphNode parent;

        frontier.push(unit.getPosition());

        while (!frontier.isEmpty()) {
            current = frontier.pop();

            if (!visited.contains(current) && (current.getBlockade() == null || current.getBlockade().isBreakable())) {

                if (current.equals(endNode)) {
                    while (possiblePath.keySet().contains(current)) {
                        path.add(current);
                        parent = possiblePath.get(current);
                        current = parent;
                    }

                    Collections.reverse(path);
                    visited.add(endNode);
                    unit.setVisited(visited);
                    unit.setRoute(path);
                    ArrayList<GraphNode> clone = (ArrayList<GraphNode>) path.clone();
                    return path;
                } else {
                    visited.add(current);

                    current.getSuccessors().stream().filter(n -> !visited.contains(n)).forEach(frontier::push);

                    for (GraphNode successor : current.getSuccessors()) {
                        if (!possiblePath.keySet().contains(successor) && !possiblePath.containsValue(successor))
                            possiblePath.put(successor, current);
                    }
                }
            }
        }

        return null;
    }
}
