package lab5;

import java.util.Comparator;

public class SimpleQuicksort extends MeasurableQuicksort
{
    @Override
    protected <T> int pivot(T[] a, int i1, int i2, Comparator<? super T> c)
    {
        return (i1 + i2) / 2;
    }
}
