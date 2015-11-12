package lab6;

public class RadixSort
{
    public static void sort(int[] a)
    {
        int[] r = new int[a.length];
        int[] e = null;

        for(int s = 0 ; s < 32 ; s += 8)
        {
            int[] t = new int[256];
            
            for(int i = 0 ; i < a.length ; ++i)
            {
                ++t[(a[i] >> s) & 255]; 
            }
            
            for(int i = 0, j = 0 ; i < 256 ; ++i)
            {
                j += t[i];
                t[i] = j - t[i];
            }
            
            for(int i = 0 ; i < a.length ; ++i)
            {
                r[t[(a[i] >> s) & 255]++] = a[i];
            }
            
            e = a;
            a = r;
            r = e;
        }
    }
}
