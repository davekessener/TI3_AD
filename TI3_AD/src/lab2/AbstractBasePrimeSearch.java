package lab2;

import java.util.ArrayList;
import java.util.List;

import measurement.AbstractMeasurable;

public abstract class AbstractBasePrimeSearch
extends AbstractMeasurable
implements PrimeSearch
{
    protected abstract void checkActualPrimes(boolean[] primes);
    
    @Override
    public List<Integer> getPrimesLessThan(int p)
    {
        assert p > FIRST_PRIME : "Precondition violated: p > FIRST_PRIME";
        
        boolean[] primes = new boolean[p];
        List<Integer> r = new ArrayList<Integer>();
        
        for(int i = 0 ; i < primes.length ; ++i)
        {
            primes[i] = true;
        }
        
        checkActualPrimes(primes);
        
        for(int i = FIRST_PRIME ; i < primes.length ; ++i)
        {
            if(primes[i]) r.add(i);
        }
        
        return r;
    }
}
