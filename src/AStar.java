import java.util.*;

/**
 * @author : Evgeniy
 * @project : bestRTS
 * @date : 07/02/16
 */
public class AStar {


    /*
     * In: Graph
     * Out: Path in a List, or null
     */
    public ArrayList<GraphNode> search(GraphNode start, GraphNode goal){

        Set<GraphNode> visited = new LinkedHashSet<>();
        Map<GraphNode,GraphNode> pred = new LinkedHashMap<>();
        Map<GraphNode,Integer> D = new LinkedHashMap<>();// empty map, contains costs from origin along best known path
        Map<GraphNode,Integer> f = new LinkedHashMap<>();//records estimated total cost
        Comparator<GraphNode> p = (o1, o2) -> {
            int value = f.get(o1);
            int value1= f.get(o2);
            if(value == value1){return 0;}
            if(value < value1){return -1;}
            return 1;
        };
        PriorityQueue<GraphNode> pending = new PriorityQueue<>(5, p);
        pending.add(start);
        D.put(start, 0);
        f.put(start, distance(start,goal));

        while(!pending.isEmpty()){
            GraphNode n =  pending.poll();// dequeue with smallest f value;

            if(n.equals(goal)){
                ArrayList<GraphNode> pathList = new ArrayList<>(); // make list, add goal first
                if(start == goal){
                    pathList.add(start);
                    return pathList;
                }
                else{//not first, add goal
                    pathList.add(n);
                    n = pred.get(n); //get the one before goal
                    while(n != start){//add rest
                        pathList.add(n);
                        n = pred.get(n);

                    }
                    pathList.add(start);		//add first
                    return pathList;
                }

            }


            visited.add(n);
            for(GraphNode s:n.getSuccessors()){
                if(!visited.contains(s)){
                    int cost = D.get(n) + distance(n,s);
                    if(!pending.contains(s) || cost<D.get(s)){
                        pred.put(s,n);
                        D.put(s,cost);
                        f.put(s, D.get(s) + distance(s,goal));
                        if(!pending.contains(s)){
                            pending.add(s);
                        }
                    }
                }
            }
        }
        return null;
    }

    public int distance(GraphNode a,GraphNode  b){
        int xDistance = Math.abs(a.getX() - b.getX());  //CHANGE DEPENDING ON IMPLEMENTATION
        int yDistance = Math.abs(a.getY() - b.getY());  //CHANGE DEPENDING ON IMPLEMENTATION
        return (int)Math.round(Math.sqrt((xDistance * xDistance) + (yDistance * yDistance)));
    }

}
