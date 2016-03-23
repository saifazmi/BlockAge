package tests;

import graph.GraphNode;

import java.util.List;

/**
 * Created by user on 12/03/2016.
 */
public class Test_Logic {

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
