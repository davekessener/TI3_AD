package lab5.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lab5.Sorter;

public abstract class SortTest
{
    private Sorter sort_;
    
    protected abstract Sorter getImplementation();
    
    @Before
    public void setup()
    {
        sort_ = getImplementation();
    }
    
    @Test
    public void testPositive()
    {
        Integer[] a = {7, 1, 9, 1, 5, 2, 9, 1, 8, 9, 5, 7, 4, 0, 4};
        Integer[] s = {0, 1, 1, 1, 2, 4, 4, 5, 5 ,7, 7, 8, 9, 9, 9};
        
        sort_.sort(a);
        
        Assert.assertArrayEquals(s, a);
    }
}
