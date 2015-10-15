package lab2;

public class FastPrimeSearch extends AbstractBasePrimeSearch
{
    @Override
    protected void checkActualPrimes(boolean[] primes)
    {
        for(int i = FIRST_PRIME ; i < primes.length ; ++i)
        {
            for(int j = FIRST_PRIME ; j < i ; ++j)
            {
                if(i % j == 0) primes[i] = false;
                addCounter(1);
            }
        }
    }
}
