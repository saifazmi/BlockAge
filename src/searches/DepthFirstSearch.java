package searches;

import entity.Unit;
import graph.GraphNode;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

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
        ArrayList<Pair<GraphNode, GraphNode>> nodeAssociations = new ArrayList<>();

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
                    unit.setNodeAssociations(nodeAssociations);

                    return path;

                } else {

                    visited.add(current);
                    current.getSuccessors().stream().filter(n -> !visited.contains(n)).forEach(frontier::push);

                    for (GraphNode successor : current.getSuccessors()) {

                        if (!possiblePath.containsValue(successor)) {

                            dfsSpecificNodeAssociation(nodeAssociations, successor, current);
                            possiblePath.put(successor, current);
                        }

                    }
                }
            }
        }

        return null;
    }

    /**
     * Adds the pair of graphnodes to the association list. If a pair exists with the same value, they are removed and replaced by a new one.
     *
     * @param nodeAssociations the list of all node association pairs
     * @param to the node to go to
     * @param from the node to come from
     */
    private static void dfsSpecificNodeAssociation(ArrayList<Pair<GraphNode, GraphNode>> nodeAssociations, GraphNode to, GraphNode from) {

        ArrayList<Pair<GraphNode, GraphNode>> toRemove = nodeAssociations.stream().filter(pair -> to.equals(pair.getValue())).collect(Collectors.toCollection(ArrayList::new));
        nodeAssociations.removeAll(toRemove);
        nodeAssociations.add(new Pair<>(from, to));
    }
}
