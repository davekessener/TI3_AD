package lab7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArrayPriorityHeap<T> implements SearchTree<T>
{
    private Comparator<T> comp_;
    private T[] vals_;
    private int size_, cap_;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ArrayPriorityHeap()
    {
        this((o1, o2) -> {
           return o1 instanceof Comparable ? ((Comparable) o1).compareTo(o2) : o1.hashCode() - o2.hashCode(); 
        });
    }
    
    public ArrayPriorityHeap(Comparator<T> c)
    {
        comp_ = c;
        clear();
    }
    
    @Override
    public void insert(T v)
    {
        if(v == null) throw new IllegalArgumentException("Can't insert null into tree!");
        
        resize();
        vals_[size_++] = v;
        swim(size_ - 1);
    }

    @Override
    public void remove(T v)
    {
        int n = find(v);
        
        if(n >= 0)
        {
            int m = size_ - 1;
            
            if(n != 0) swap(n, 0);
            
            swap(m, 0);
            
            --size_;
            
            if(n != 0)
            {
                swap(n, 0);
                if(comp_.compare(vals_[n], vals_[n / 2]) < 0) swim(n); else sink(n);
            }
            else
            {
                sink(n);
            }
        }
        else throw new IllegalArgumentException("Can't remove " + v + ", it's not in the tree!");
    }

    @Override
    public boolean contains(T v)
    {
        return find(v) >= 0;
    }

    @Override
    public void clear()
    {
        vals_ = null;
        size_ = cap_ = 0;
    }

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
    public List<T> preorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        preorder(r, 0);
        
        return r;
    }

    @Override
    public List<T> inorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        inorder(r, 0);
        
        return r;
    }

    @Override
    public List<T> postorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        postorder(r, 0);
        
        return r;
    }
    
    private void preorder(List<T> l, int n)
    {
        if(n < size_)
        {
            l.add(vals_[n]);
            preorder(l, n * 2);
            preorder(l, n * 2 + 1);
        }
    }
    
    private void inorder(List<T> l, int n)
    {
        if(n < size_)
        {
            inorder(l, n * 2);
            l.add(vals_[n]);
            inorder(l, n * 2 + 1);
        }
    }
    
    private void postorder(List<T> l, int n)
    {
        if(n < size_)
        {
            postorder(l, n * 2);
            postorder(l, n * 2 + 1);
            l.add(vals_[n]);
        }
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
