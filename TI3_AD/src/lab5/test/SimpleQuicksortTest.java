package lab5.test;

import lab5.SimpleQuicksort;
import lab5.Sorter;

public class SimpleQuicksortTest extends SortTest
{
    @Override
    protected Sorter getImplementation()
    {
        return new SimpleQuicksort();
    }
}
