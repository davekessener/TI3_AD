package lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractGraph<T> implements Graph<T>
{
    private List<T> nodes_ = new ArrayList<>();
    private Map<T, Integer> lookup_ = new HashMap<>();

    @Override
    public void insert(T t)
    {
        checkOut(t);
        
        doInsert(t);
        
        lookup_.put(t, nodes_.size());
        nodes_.add(t);
    }

    @Override
    public void remove(T t)
    {
        checkIn(t);
        
        doRemove(t);
        
        int k = lookup_.get(t), s = nodes_.size();
        
        nodes_.remove(k);
        lookup_.remove(t);
        
        if(--s > 0)
        {
            for(int i = k ; i < s ; ++i)
            {
                lookup_.put(nodes_.get(i), i);
            }
        }
    }

    @Override
    public boolean contains(T t)
    {
        return nodes_.contains(t);
    }
    
    @Override
    public int size()
    {
        return nodes_.size();
    }

    @Override
    public Iterator<T> iterator()
    {
        return nodes_.iterator();
    }

    @Override
    public void connect(T t1, T t2, double w)
    {
        checkIn(t1);
        checkIn(t2);
        
        if(w == 0)
            throw new IllegalArgumentException("Edges can't have a weight of 0.");
    
        setCost(t1, t2, w);
    }
    
    @Override
    public void disconnect(T t1, T t2)
    {
        if(!areConnected(t1, t2))
            throw new IllegalArgumentException(t1 + " & " + t2 + " aren't connected.");
        
        doDisconnect(t1, t2);
    }
    
    @Override
    public boolean areConnected(T t1, T t2)
    {
        checkIn(t1);
        checkIn(t2);
        
        return checkNeighbors(t1, t2);
    }

    @Override
    public double getWeight(T t1, T t2)
    {
        checkIn(t1);
        checkIn(t2);
    
        return areConnected(t1, t2) ? getCost(t1, t2) : Double.POSITIVE_INFINITY;
    }
    
    protected T get(int i) { return nodes_.get(i); }
    protected int lookup(T t) { return lookup_.get(t); }
    
    protected void doInsert(T t) { }
    protected void doRemove(T t) { }
    protected abstract void setCost(T t1, T t2, double c);
    protected abstract double getCost(T t1, T t2);
    protected abstract boolean checkNeighbors(T t1, T t2);
    protected abstract void doDisconnect(T t1, T t2);
    
    protected static interface Accessor { boolean access(int i1, int i2); }

    protected Iterable<T> neighbors(T t, Accessor a)
    {
        checkIn(t);

        List<T> n = new ArrayList<>();
        int k = lookup_.get(t);
        
        for(int i = 0, s = size() ; i < s ; ++i)
        {
            if(i != k && a.access(k, i))
            {
                n.add(nodes_.get(i));
            }
        }
        
        return n;
    }
    
    private void checkIn(T t)
    {
        if(!contains(t))
            throw new IllegalArgumentException(t + " is not in the graph!");
    }
    
    private void checkOut(T t)
    {
        if(contains(t))
            throw new IllegalArgumentException(t + " is already in the graph!");
    }
}
