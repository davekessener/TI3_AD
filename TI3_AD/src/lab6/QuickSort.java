package lab6;

public class QuickSort
{
    public static void sort(int[] a)
    {
        quicksort(a, 0, a.length - 1);
    }
    
    private static void quicksort(int[] a, int i1, int i2)
    {
        if(i2 - i1 <= 0) return;
        else if(i2 - i1 == 1)
        {
            if(a[i1] > a[i2])
                swap(a, i1, i2);
           
            return;
        }
        
        int pt = pivot(a, i1, i2);
        int l = i2, r = i1 + 1;
        int p = a[pt];

        swap(a, pt, i1);

        
        while(true)
        {
            while(a[l] > p  && l > i1 + 1) --l;
            while(a[r] <= p && r < i2)     ++r;
            
            if(r >= l) break;
            
            swap(a, l, r);
        }
        
             if(a[l] < a[i1]) swap(a, l, i1);
        else if(a[l] > a[i1]) l = i1;
        
        quicksort(a, i1, l - 1);
        quicksort(a, l + 1, i2);
    }
    
    private static int pivot(int[] a, int i1, int i2)
    {
        return (i1 + i2) / 2;
    }
    
    private static void swap(int[] a, int i1, int i2)
    {
        int t = a[i1];
        a[i1] = a[i2];
        a[i2] = t;
    }
}
