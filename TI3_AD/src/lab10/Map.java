package lab10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface Map<K, V> extends Iterable<Map.Entry<K, V>>
{
    void put(K k, V v);
    List<V> get(K k);
    int size();
    boolean isEmpty();
    Iterator<Entry<K, V>> iterator();
    Set<K> keySet();
    
    public static class Entry<K, V>
    {
        private K key_;
        private List<V> value_;
        
        protected Entry(K k) { this(k, new ArrayList<V>()); }
        protected Entry(K k, List<V> v)
        {
            key_ = k;
            value_ = v;
        }
        
        public K getKey() { return key_; }
        public List<V> getValues() { return value_; }
        
        protected void addValue(V v) { value_.add(v); }
    }
}
