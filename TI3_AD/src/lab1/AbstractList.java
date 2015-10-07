package lab1;

import lab1.measurement.Measurable;

public abstract class AbstractList<T> implements List<T>, Measurable
{
    private long counter_ = 0;
    
    /**
     * implementationen zaehlen ausgefuehrte operationen
     * zur komplezitaetsermittlung
     */
    
    /**
     * @ensure getCounter() == 0
     */
    public final void resetCounter( )
    {
        counter_ = 0;
    }
    
    /**
     * 
     * @return Aktuellen counter-Stand.
     */
    public final long getCounter( )
    {
        return counter_;
    }
    
    protected final void addCounter(long v)
    {
        counter_ += v;
    }
}
