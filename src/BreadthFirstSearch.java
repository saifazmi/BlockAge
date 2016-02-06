import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by hung on 06/02/16.
 */
public class BreadthFirstSearch {

    LinkedList<GraphNode> frontier;
    TreeSet<GraphNode> visited;

    public ArrayList<GraphNode> findPathFrom(GraphNode startNode, GraphNode endNode)
    {
        GraphNode current;
        ArrayList<GraphNode> path = new ArrayList<GraphNode>();

        frontier.clear();
        visited.clear();

        frontier.add(startNode);

        while (!frontier.isEmpty()) {

            current = frontier.poll();

            if (!visited.contains(current))
            {
                if (current.equals(endNode))
                {
                    path.add(current);
                    path.remove(0);
                    return path;
                }
                else
                {
                    visited.add(current);
                    frontier.addAll(current.getSuccessors());
                }
            }
        }


        return new Nothing<IList<GraphNode>>();
    }
}
