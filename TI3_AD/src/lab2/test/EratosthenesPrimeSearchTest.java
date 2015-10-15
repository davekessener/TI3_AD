package lab2.test;

import lab2.PrimeSearch;
import lab2.EratosthenesPrimeSearch;

public class EratosthenesPrimeSearchTest extends PrimeSearchTest
{
    @Override
    protected PrimeSearch getImplementation()
    {
        return new EratosthenesPrimeSearch();
    }
}
