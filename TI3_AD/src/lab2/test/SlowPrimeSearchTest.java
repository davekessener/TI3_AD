package lab2.test;

import lab2.PrimeSearch;
import lab2.SlowPrimeSearch;

public class SlowPrimeSearchTest extends PrimeSearchTest
{
    @Override
    protected PrimeSearch getImplementation()
    {
        return new SlowPrimeSearch();
    }
}
