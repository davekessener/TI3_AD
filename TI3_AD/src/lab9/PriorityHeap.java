package lab9;

import java.util.Arrays;
import java.util.Comparator;

public class PriorityHeap<T>
{
    private Comparator<T> comp_;
    private T[] vals_;
    private int size_, cap_;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PriorityHeap()
    {
        this((o1, o2) -> {
           return o1 instanceof Comparable ? ((Comparable) o1).compareTo(o2) : o1.hashCode() - o2.hashCode(); 
        });
    }
    
    public PriorityHeap(Comparator<T> c)
    {
        comp_ = c;
        clear();
    }
    
    public void insert(T v)
    {
        if(v == null) throw new IllegalArgumentException("Can't insert null into tree!");
        
        resize();
        vals_[size_++] = v;
        swim(size_ - 1);
    }

    public T pop()
    {
        swap(0, --size_);
        sink(0);
        
        T r = vals_[size_];
        vals_[size_] = null;
        return r;
    }

    public boolean contains(T v)
    {
        return find(v) >= 0;
    }

    public void clear()
    {
        vals_ = null;
        size_ = cap_ = 0;
    }

    public int size()
    {
        return size_;
    }

    public boolean isEmpty()
    {
        return size_ == 0;
    }
    
    private void resize()
    {
        if(size_ == cap_)
        {
            vals_ = Arrays.copyOf(vals_, cap_ = cap_ * 2 + 1);
        }
        else if(size_ < cap_ / 4)
        {
            vals_ = Arrays.copyOf(vals_, cap_ /= 2);
        }
    }
    
    private void swap(int i, int j)
    {
        T t = vals_[i];
        vals_[i] = vals_[j];
        vals_[j] = t;
    }
    
    private int find(T v)
    {
        for(int i = 0 ; i < size_ ; ++i)
        {
            if(comp_.compare(v, vals_[i]) == 0) return i;
        }
        
        return -1;
    }

    private void sink(int n)
    {
        while(n * 2 < size_)
        {
            int m = n * 2;
            
            if(m + 1 < size_ && comp_.compare(vals_[m + 1], vals_[m]) < 0)
                ++m;
            
            if(comp_.compare(vals_[m], vals_[n]) >= 0)
                break;
            
            swap(n, m);
            n = m;
        }
    }
    
    private void swim(int n)
    {
        while(n / 2 > 0 && comp_.compare(vals_[n], vals_[n / 2]) < 0)
        {
            swap(n, n / 2);
            n /= 2;
        }
    }
}
