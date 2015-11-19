package lab7.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import lab7.SearchTree;

import org.junit.Test;

public abstract class SearchTreeTest
{
    protected abstract SearchTree<Integer> getImplementation();
    
    @Test
    public void doTest()
    {
        SearchTree<Integer> t = getImplementation();
        int[] disorder = {4, 7, 1, 9, 2, 0, 5, 3, 6, 8};
        
        for(int i = 0 ; i < disorder.length ; ++i)
        {
            t.insert(disorder[i]);
        }
        
        assertEquals(disorder.length, t.size());
        
        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), t.inorder());
        
        for(int i = 0 ; i < 10 ; ++i)
        {
            t.remove(i);
        }

        assertTrue(t.isEmpty());
    }
}
