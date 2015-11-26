package lab8.test;

import static org.junit.Assert.*;
import lab8.LinkedIntTree;

import org.junit.Test;

public class LinkedIntTreeTest
{
    @Test
    public void doTest()
    {
        LinkedIntTree t = new LinkedIntTree();
        int[] rnd = {74, 38, 66, 20, 82, 55, 53, 35, 54, 57};
        
        for(int i = 0 ; i < 10 ; ++i)
        {
            t.insert(rnd[i]);
        }

        assertEquals(20 + 35 + 38, t.getSumBetween(0, 40));
        assertEquals(53 + 54 + 55 + 57 + 66, t.getSumBetween(40, 70));
        assertEquals(74 + 82, t.getSumBetween(70, 100));
        assertEquals(0, t.getSumBetween(-2, -1));
        assertEquals(0, t.getSumBetween(100, 101));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIntervall()
    {
        LinkedIntTree t = new LinkedIntTree();

        t.getSumBetween(2, 1);
    }
}
