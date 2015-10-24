package lab5;

import java.util.Comparator;

public abstract class AbstractQuicksort extends Sorter
{
    protected abstract <T> int pivot(T[] a, int i1, int i2, Comparator<? super T> c);
    
    @Override
    public <T> void sort(T[] a, Comparator<? super T> c)
    {
        quicksort(a, 0, a.length - 1, c);
    }
    
    private <T> void quicksort(T[] a, int i1, int i2, Comparator<? super T> c)
    {
        if(i2 - i1 <= 0) return;
        else if(i2 - i1 == 1)
        {
            if(c.compare(a[i1], a[i2]) > 0)
                swap(a, i1, i2);
           
            return;
        }
        
        int pt = pivot(a, i1, i2, c);
        int l = i2, r = i1 + 1;
        T p = a[pt];

        swap(a, pt, i1);

        
        while(true)
        {
            while(c.compare(a[l], p) > 0  && l > i1 + 1) --l;
            while(c.compare(a[r], p) <= 0 && r < i2)     ++r;
            
            if(r >= l) break;
            
            swap(a, l, r);
        }
        
             if(c.compare(a[l], a[i1]) < 0) swap(a, l, i1);
        else if(c.compare(a[l], a[i1]) > 0) l = i1;
        
        quicksort(a, i1, l - 1, c);
        quicksort(a, l + 1, i2, c);
    }
    
    protected <T> void swap(T[] a, int i1, int i2)
    {
        T t = a[i1];
        a[i1] = a[i2];
        a[i2] = t;
    }
}
