import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by hung on 06/02/16.
 */
public class DepthFristSearch {

    private Stack<GraphNode> frontier;
    private TreeSet<GraphNode> visited;

    static private DepthFristSearch instance;

    /**
     * Singleton pattern for search
     * As there is only ever 1 instance of DFS,
     * it is efficient and safe to use the singleton pattern
     */
    public DepthFristSearch()
    {
        instance = this;
    }

    public DepthFristSearch Instance()
    {
        return instance;
    }

    /**
     * Finds a path from a start node to the end node using DFS
     * The returned path should not include the start node
     * @param startNode node search starts from
     * @param endNode node search terminates with, the goal node, usually enemy base
     * @return path from start to goal node
     */
    public ArrayList<GraphNode> findPathFrom(GraphNode startNode,GraphNode endNode) {

        GraphNode current;
        visited.clear();
        frontier.clear();

        frontier.push(startNode);

        ArrayList<GraphNode> path = new ArrayList<GraphNode>();

        while (!path.isEmpty())
        {
            while(!frontier.isEmpty())
            {
                current = frontier.pop();

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
                        path.add(current);
                        visited.add(current);
                        frontier.clear();

                        for (GraphNode n : current.getSuccessors())
                        {
                            if(!visited.contains(n))
                            {
                                frontier.push(n);
                            }
                        }
                    }
                }
            }
        }

        visited.clear();
        return null;
    }
}
