package lab10;

import java.util.Iterator;

public interface Map<K, V>
{
    void put(K k, V v);
    V get(K k);
    int size();
    boolean isEmpty();
    Iterator<Entry<K, V>> iterator();
    
    public static class Entry<K, V>
    {
        public final K key;
        public final V value;
        
        private Entry(K k, V v)
        {
            key = k;
            value = v;
        }
    }
}
