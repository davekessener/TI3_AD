package lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListGraph<T> extends AbstractGraph<T>
{
    private List<Map<T, Double>> connections_ = new ArrayList<>();
    
    @Override
    protected void doInsert(T t)
    {
        connections_.add(new HashMap<>());
    }
    
    @Override
    protected void doRemove(T t)
    {
        for(Map<T, Double> m : connections_)
        {
            m.remove(t);
        }
    }

    @Override
    public Iterable<T> getNeighbors(T t)
    {
        return (new IteratorSum<T>()).add(getInNeighbors(t)).add(getOutNeighbors(t));
    }

    @Override
    public Iterable<T> getInNeighbors(T t)
    {
        return neighbors(t, (i1, i2) -> connections_.get(i2).containsKey(get(i1)));
    }

    @Override
    public Iterable<T> getOutNeighbors(T t)
    {
        return neighbors(t, (i1, i2) -> connections_.get(i1).containsKey(get(i2)));
    }
    
    @Override
    public boolean checkNeighbors(T t1, T t2)
    {
        return connections_.get(lookup(t1)).containsKey(t2);
    }

    @Override
    protected void setCost(T t1, T t2, double c)
    {
        connections_.get(lookup(t1)).put(t2, c);
    }

    @Override
    protected double getCost(T t1, T t2)
    {
        return connections_.get(lookup(t1)).get(t2);
    }
    
    @Override
    protected void doDisconnect(T t1, T t2)
    {
        connections_.get(lookup(t1)).remove(t2);
    }
}
