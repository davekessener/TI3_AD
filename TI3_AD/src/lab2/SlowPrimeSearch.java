package lab2;

public class SlowPrimeSearch extends AbstractBasePrimeSearch
{
    @Override
    protected void checkActualPrimes(boolean[] primes)
    {
        for(int i = FIRST_PRIME ; i < primes.length ; ++i)
        {
            for(int j = FIRST_PRIME ; j < primes.length ; ++j)
            {
                if(i % j == 0 && i != j) primes[i] = false;
                addCounter(1);
            }
        }
    }
}
