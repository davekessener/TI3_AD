package lab5;

import java.util.Comparator;

public class MedianQuicksort extends MeasurableQuicksort
{
    @Override
    protected <T> int pivot(T[] a, int i1, int i2, Comparator<? super T> c)
    {
        int i3 = (i1 + i2) / 2;
        T o1 = a[i1], o2 = a[i2], o3 = a[i3];
        
        return   c.compare(o1, o2) > 0 
                ? c.compare(o1, o3) > 0 
                    ? i3 
                    : i1 
                : c.compare(o2, o3) > 0 
                    ? i3 
                    : i2;
    }
}
