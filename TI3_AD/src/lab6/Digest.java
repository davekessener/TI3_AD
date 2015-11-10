package lab6;

import java.util.Arrays;
import java.util.Random;

public class Digest
{
    private static final int RUNS = 100;
    private static final int SIZE = 6;
    private static final int SEED = 201510;
    
    private Random rand_ = new Random(SEED);
    
    public void run()
    {
        for(int k = 1 ; k <= SIZE ; ++k)
        {
            System.out.println("\n#####\nFor k == " + k + ":");
            System.out.println("RadixSort takes " + prettyTime(measure(k, a -> RadixSort.sort(a))));
            System.out.println("QuickSort takes " + prettyTime(measure(k, a -> QuickSort.sort(a))));
            System.out.println(" JavaSort takes " + prettyTime(measure(k, a -> Arrays.sort(a))));
        }
    }
    
    private long measure(int k, Sorter s)
    {
        long time = 0;
        
        reset();
        
        for(int i = 0 ; i < RUNS ; ++i)
        {
            int[] a = generateArray(k);
            long t = 0;
            
            System.gc();
            t = System.nanoTime();
            s.sort(a);
            time += System.nanoTime() - t;
            
            assert isSorted(a) : "Algorithm doesn't actually sort!";
        }
        
        time /= RUNS;
        
        return time;
    }
    
    private void reset()
    {
        rand_ = new Random(SEED);
    }
    
    private boolean isSorted(int[] a)
    {
        for(int i = 1 ; i < a.length ; ++i)
        {
            if(a[i - 1] > a[i]) return false;
        }
        
        return true;
    }
    
    private int[] generateArray(int k)
    {
        int l = 10;
        while(--k > 0) l *= 10;
        int[] a = new int[l];
        
        for(int i = 0 ; i < l ; ++i)
        {
            a[i] = rand_.nextInt(100 * l + 1) + 700 * l;
        }
        
        return a;
    }

    private String prettyTime(long v)
    {
        long ns = v % 1000;
        v /= 1000;
        long us = v % 1000;
        v /= 1000;
        long ms = v % 1000;
        v /= 1000;
        int s = (int) (v % 60);
        v /= 60;
        int m = (int) (v % 60);
        v /= 60;
        int h = (int) v;
        
        return String.format("%02d:%02d:%02d.%03d_%03d_%03d", h, m, s, ms, us, ns);
    }
    
    private static interface Sorter
    {
        public abstract void sort(int[] a);
    }
}
