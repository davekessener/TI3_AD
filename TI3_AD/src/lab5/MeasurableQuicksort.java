package lab5;

import java.util.Comparator;

import measurement.Measurable;

public abstract class MeasurableQuicksort extends AbstractQuicksort implements Measurable
{
    private long counter_ = 0;
    
    @Override
    public <T> void sort(T[] a, Comparator<? super T> c)
    {
        super.sort(a, (o1, o2) -> { increment(); return c.compare(o1, o2); });
    }
    
    @Override
    public void resetCounter()
    {
        counter_ = 0;
    }

    @Override
    public long getCounter()
    {
        return counter_;
    }
    
    protected void increment()
    {
        ++counter_;
    }
    
    @Override
    protected <T> void swap(T[] a, int i1, int i2)
    {
        increment();
        super.swap(a, i1, i2);
    }
}
