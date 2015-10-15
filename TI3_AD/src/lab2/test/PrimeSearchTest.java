package lab2.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lab2.PrimeSearch;

public abstract class PrimeSearchTest
{
    protected abstract PrimeSearch getImplementation();
    
    @Test
    public void positiveTest()
    {
        PrimeSearch s = getImplementation();
        List<Integer> cmp = Arrays.asList(new Integer[] {2, 3, 5, 7, 11, 13, 17});
        List<Integer> r = s.getPrimesLessThan(18);
        
        assertEquals(cmp, r);
        
        r = s.getPrimesLessThan(17);
        
        assertFalse(r.contains(17));
    }
    
    @Test(expected = AssertionError.class)
    public void testNegativeFailure()
    {
        getImplementation().getPrimesLessThan(-1);
    }
    
    @Test(expected = AssertionError.class)
    public void testBorderFailure()
    {
        getImplementation().getPrimesLessThan(PrimeSearch.FIRST_PRIME);
    }
}
