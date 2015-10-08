package lab1.measurement;

public abstract class AbstractFormatter implements Format
{
    private StringBuilder sb = new StringBuilder();
    private String cache = null;
    
    protected void add(String s, Object ... o)
    {
        cache = null;
        sb.append(String.format(s, o));
    }
    
    @Override
    public String toString()
    {
        if(cache == null)
        {
            cache = sb.toString();
        }
        
        return cache;
    }
}
