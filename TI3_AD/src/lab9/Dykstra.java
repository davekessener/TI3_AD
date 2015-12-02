package lab9;

import java.util.HashMap;
import java.util.Map;

public class Dykstra
{
    public static <T> Map<T, Double> find(Graph<T> graph, T root)
    {
        Algorithm<T> a = new Algorithm<>(graph, root);
        
        return a.getPathCosts();
    }
    
    private static class Algorithm<T>
    {
        private Map<T, Node> nodes = new HashMap<>();
        
        public Algorithm(Graph<T> graph, T root)
        {
            PriorityHeap<Node> open = new PriorityHeap<>();
            
            open.insert(getNode(root));
            
            while(!open.isEmpty())
            {
                Node n = open.pop();
                
                n.marked = true;
                
                for(T v : graph.getOutNeighbors(n.value))
                {
                    Node m = getNode(v);
                    
                    if(!m.marked)
                    {
                        double c = n.cost + graph.getWeight(n.value, m.value);
                        
                        if(m.cost > c)
                        {
                            m.cost = c;
                            m.prev = n;
                        }
                        
                        open.insert(m);
                    }
                }
            }
        }
        
        public Map<T, Double> getPathCosts()
        {
            Map<T, Double> r = new HashMap<>();
            
            for(T v : nodes.keySet())
            {
                r.put(v, nodes.get(v).cost);
            }
            
            return r;
        }
        
        private Node getNode(T v)
        {
            if(!nodes.containsKey(v))
            {
                nodes.put(v, new Node(v));
            }
            
            return nodes.get(v);
        }

        @SuppressWarnings("unused")
        private class Node implements Comparable<Node>
        {
            private T value;
            private Node prev;
            private double cost;
            private boolean marked;
            
            public Node(T v)
            {
                value = v;
                prev = null;
                cost = Double.POSITIVE_INFINITY;
                marked = false;
            }
            
            @Override
            public int compareTo(Node o)
            {
                return Double.valueOf(cost).compareTo(o.cost);
            }
        }
    }
}
