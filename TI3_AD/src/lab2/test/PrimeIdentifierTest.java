package lab2.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import lab2.EratosthenesPrimeSearch;
import lab2.PrimeIdentifier;

import org.junit.Test;

public class PrimeIdentifierTest
{
    @Test
    public void testPrimes()
    {
        boolean[] primes = {false, false, true, true, false, true, false, true, false, false, false, true, false, true};
        PrimeIdentifier pi = new PrimeIdentifier();
        
        for(int i = -1 ; i < primes.length ; ++i)
        {
            assertEquals(pi.isPrime(i), i >= 0 ? primes[i] : false);
        }
    }
    
    @Test
    public void testCompadability()
    {
        PrimeIdentifier pi = new PrimeIdentifier();
        
        for(Integer v : (new EratosthenesPrimeSearch()).getPrimesLessThan(1000000))
        {
            assertTrue(pi.isPrime(v));
        }
    }
}
