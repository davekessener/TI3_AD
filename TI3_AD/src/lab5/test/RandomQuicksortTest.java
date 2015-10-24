package lab5.test;

import lab5.RandomQuicksort;
import lab5.Sorter;

public class RandomQuicksortTest extends SortTest
{
    @Override
    protected Sorter getImplementation()
    {
        return new RandomQuicksort();
    }
}
