package lab5.test;

import lab5.MedianQuicksort;
import lab5.Sorter;

public class MedianQuicksortTest extends SortTest
{
    @Override
    protected Sorter getImplementation()
    {
        return new MedianQuicksort();
    }
}
