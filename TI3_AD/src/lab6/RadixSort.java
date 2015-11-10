package lab6;

public class RadixSort
{
    public static void sort(int[] a)
    {
        int r[] = new int[a.length];
        int b[] = new int[256];

        for(int s = 0 ; s < 32 ; s += 8)
        {
            int t[] = new int[256];
            
            for(int i = 0 ; i < a.length ; ++i)
            {
                ++t[(a[i] >> s) & 255]; 
            }
            
            for(int i = 0, j = 0 ; i < 256 ; j += t[i++])
            {
                b[i] = j;
            }
            
            for(int i = 0 ; i < a.length ; ++i)
            {
                r[b[(a[i] >> s) & 255]++] = a[i];
            }
            
            int e[] = a;
            a = r;
            r = e;
        }
    }
}
