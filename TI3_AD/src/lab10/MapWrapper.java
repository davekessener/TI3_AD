package lab10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MapWrapper implements Map<IP, String>
{
    private java.util.Map<IP, List<String>> map_ = new HashMap<>();

    @Override
    public void put(IP k, String v)
    {
        if(map_.containsKey(k))
        {
            map_.get(k).add(v);
        }
        else
        {
            List<String> l = new ArrayList<>();
            l.add(v);
            map_.put(k, l);
        }
    }

    @Override
    public List<String> get(IP k)
    {
        return map_.get(k);
    }

    @Override
    public int size()
    {
        return map_.size();
    }

    @Override
    public boolean isEmpty()
    {
        return map_.isEmpty();
    }

    @Override
    public Iterator<Entry<IP, String>> iterator()
    {
        return new EntryIterator<IP, String>(map_.entrySet());
    }

    @Override
    public Set<IP> keySet()
    {
        return map_.keySet();
    }
    
    private static class EntryIterator<K, V> implements Iterator<Entry<K, V>>
    {
        private Iterator<java.util.Map.Entry<K, List<V>>> i_;
        
        private EntryIterator(Set<java.util.Map.Entry<K, List<V>>> set)
        {
            i_ = set.iterator();
        }

        @Override
        public boolean hasNext()
        {
            return i_.hasNext();
        }

        @Override
        public Entry<K, V> next()
        {
            java.util.Map.Entry<K, List<V>> e = i_.next();
            
            return new Entry<K, V>(e.getKey(), e.getValue());
        }
    }
}
