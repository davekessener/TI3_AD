package lab11.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.IntStream;

import lab11.MathHelper;

import org.junit.Test;

public class MathHelperTest
{
    @Test
    public void testPow()
    {
        for(int i = -50 ; i < 50 ; ++i)
        {
            for(int j = 0 ; j < 10 ; ++j)
            {
                assertEquals((long) Math.pow(i, j) % 23, MathHelper.pow_mod(i, j, 23));
            }
        }
    }
    
    @Test
    public void testPrimes()
    {
        int[] p = MathHelper.findPrimes(2, 20);
        int[] c = {2, 3, 5, 7, 11, 13, 17, 19};
        
        assertEquals(c.length, p.length);
        assertTrue(IntStream.range(0, p.length).allMatch(i -> p[i] == c[i]));
    }
}
