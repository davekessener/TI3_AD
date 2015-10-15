package measurement;

public class AbstractMeasurable implements Measurable
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
