package lab9.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Test;

import lab9.Dykstra;
import lab9.Graph;

public abstract class GraphTest
{
    protected abstract Graph<String> getImplementation();
    
    @Test
    public void doTest()
    {
        Graph<String> g = getImplementation();
        
        g.insert("A");
        g.insert("B");
        g.insert("C");
        g.insert("D");
        g.insert("E");
        g.insert("F");
        
        assertEquals(6, g.size());

        g.connect("A", "E", 0.1);
        g.connect("A", "F", 0.9);
        g.connect("B", "A", 0.3);
        g.connect("B", "C", 0.3);
        g.connect("B", "D", 0.4);
        g.connect("C", "D", 0.6);
        g.connect("C", "E", 0.4);
        g.connect("D", "E", 1);
        g.connect("E", "A", 0.55);
        g.connect("E", "F", 0.45);
        g.connect("F", "D", 1);
        
        assertFalse(g.areConnected("A", "B"));
        assertTrue(g.areConnected("B", "A"));
        assertEquals(0.3, g.getWeight("B", "A"), E);
        
        g.connect("F", "E", 0.1);
        assertTrue(g.areConnected("F", "E"));
        g.disconnect("F", "E");
        assertFalse(g.areConnected("F", "E"));
        
        Set<String> b_nei = new HashSet<>();
        
        b_nei.add("E");
        b_nei.add("F");
        for(String s : g.getOutNeighbors("A"))
        {
            assertTrue(b_nei.contains(s));
            b_nei.remove(s);
        }
        assertTrue(b_nei.isEmpty());
        
        Map<String, Double> paths = Dykstra.find(g, "C");
        
        assertEquals(5, paths.size());
        
        check(paths, "A", 0.95);
        check(paths, "C", 0.0);
        check(paths, "D", 0.6);
        check(paths, "E", 0.4);
        check(paths, "F", 0.85);
    }
    
    private void check(Map<String, Double> m, String k, double v)
    {
        assertTrue(m.containsKey(k));
        assertEquals(v, m.get(k).doubleValue(), E);
    }
    
    private static final double E = 0.0005;
}
