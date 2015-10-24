package lab4.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import lab4.Pascal;

public abstract class PascalTest
{
    private Pascal pascal_;
    
    protected abstract Pascal getImplementation();
    
    @Before
    public void setup()
    {
        pascal_ = getImplementation();
    }
    
    @Test
    public void testPositive()
    {
        int[] odd = {1, 6, 15, 20, 15, 6, 1};
        int[] even = {1, 5, 10, 10, 5, 1};

        Assert.assertArrayEquals(even, pascal_.calculateRow(even.length));
        Assert.assertArrayEquals(odd, pascal_.calculateRow(odd.length));
    }
    
    @Test(expected = AssertionError.class)
    public void testFailure()
    {
        pascal_.calculateRow(-1);
    }
}
