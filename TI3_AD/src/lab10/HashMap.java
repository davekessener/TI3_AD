package lab10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V>
{
    private Object[] map_;
    private int size_;
    private Set<K> keyset_;
    
    public HashMap() { this(MIN_CAP); }
    public HashMap(int c)
    {
        map_ = new Object[c < MIN_CAP ? MIN_CAP : c];
        size_ = 0;
        keyset_ = new HashSet<>();
    }
    
    @Override
    public void put(K k, V v)
    {
        keyset_.add(k);
        
        resize();
        
        put(k, Arrays.asList(v));
    }
    
    private void put(HashEntry e)
    {
        put(e.getKey(), e.getValues());
    }
    
    private void put(K k, List<V> vs)
    {
        int h = hash(k);
        HashEntry e = map_[h] == null ? new HashEntry(k) : get(h);
        
        if(map_[h] == null) ++size_;
        
        for(V v : vs)
        {
            e.addValue(v);
        }
        
        map_[h] = e;
    }

    @Override
    public List<V> get(K k)
    {
        int h = hash(k);
        
        return map_[h] == null ? new ArrayList<V>() : get(h).getValues();
    }
    
    private HashEntry get(int i)
    {
        return (HashEntry) map_[i];
    }
    
    private int hash(K k)
    {
        int h = k.hashCode(), i = 0, l = map_.length;
        
        do
        {
            if(h < 0) h += l - 1;
            h = doHash(h, i, l);
            ++i;
        } while(h < 0 || (map_[h] != null && !get(h).getKey().equals(k)));
        
        return h;
    }
    
    protected int doHash(int h, int i, int l)
    {
        return ((h % 65521) + (1 + (h % (65521 - 2))) * i * i) % l;
    }
    
    private int capacity()
    {
        return map_.length;
    }
    
    private double loadFactor()
    {
        return size() / (double)capacity();
    }
    
    private void resize()
    {
        if(loadFactor() > UPPER_BOUND || loadFactor() < LOWER_BOUND)
        {
            resize((int) (1 + 2 * size() / (LOWER_BOUND + UPPER_BOUND)));
        }
    }
    
    private void resize(int s)
    {
        Object[] old = map_;
        map_ = new Object[s < MIN_CAP ? MIN_CAP : s];
        size_ = 0;
        
        for(Object e : old)
        {
            if(e != null) put((HashEntry) e);
        }
    }

    private static final int MIN_CAP = 15;
    private static final double UPPER_BOUND = 0.8;
    private static final double LOWER_BOUND = 0.5;

    @Override
    public int size()
    {
        return size_;
    }

    @Override
    public boolean isEmpty()
    {
        return size_ == 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator()
    {
        return new MapIterator<>(map_);
    }

    @Override
    public Set<K> keySet()
    {
        return keyset_;
    }
    
    private class HashEntry extends Entry<K, V>
    {
        @SuppressWarnings("unused")
        public boolean removed;
        
        private HashEntry(K k)
        {
            super(k);
            removed = false;
        }
    }
    
    private static class MapIterator<T> implements Iterator<T>
    {
        private Object[] v_;
        private int i_;
        
        public MapIterator(Object[] v)
        {
            v_ = v;
            i_ = 0;
        }

        @Override
        public boolean hasNext()
        {
            return i_ < v_.length;
        }

        @Override
        public T next()
        {
            T n = (T) v_[i_];
            
            do ++i_; while(hasNext() && v_[i_] == null);
            
            return n;
        }
    }
}
