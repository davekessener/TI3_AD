package lab2.test;

import lab2.PrimeSearch;
import lab2.FastPrimeSearch;

public class FastPrimeSearchTest extends PrimeSearchTest
{
    @Override
    protected PrimeSearch getImplementation()
    {
        return new FastPrimeSearch();
    }
}
