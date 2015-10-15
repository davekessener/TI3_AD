package lab2;

public class EratosthenesPrimeSearch extends AbstractBasePrimeSearch
{
    @Override
    protected void checkActualPrimes(boolean[] primes)
    {
        for(int i = FIRST_PRIME, s = (int) Math.floor(Math.sqrt(primes.length)) ; i < s ; ++i)
        {
            if(primes[i]) for(int j = FIRST_PRIME ; i * j < primes.length ; ++j)
            {
                primes[i * j] = false;
                addCounter(1);
            }
        }
    }
}
