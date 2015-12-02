package lab9;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class IteratorSum<T> implements Iterator<T>, Iterable<T>
{
    private Queue<Iterator<T>> iterators_ = new ArrayDeque<>();
    private Iterator<T> cur_ = null;
    
    public IteratorSum<T> add(Iterable<T> i) { return add(i.iterator()); }
    public IteratorSum<T> add(Iterator<T> i)
    {
        if(cur_ == null)
        {
            cur_ = i;
        }
        else
        {
            iterators_.add(i);
        }
        
        return this;
    }
    
    @Override
    public boolean hasNext()
    {
        return cur_ != null;
    }
    
    @Override
    public T next()
    {
        T n = cur_.next();
        
        if(!cur_.hasNext())
        {
            cur_ = iterators_.poll();
        }
        
        return n;
    }

    @Override
    public Iterator<T> iterator()
    {
        return this;
    }
}
