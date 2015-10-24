package lab5;

import java.util.Comparator;
import java.util.Random;

public class RandomQuicksort extends MeasurableQuicksort
{
    private Random rand_ = new Random(System.currentTimeMillis());
    
    @Override
    protected <T> int pivot(T[] a, int i1, int i2, Comparator<? super T> c)
    {
        return rand_.nextInt(i2 - i1 + 1) + i1;
    }
}
