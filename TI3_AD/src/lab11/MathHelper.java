package lab11;

import java.util.stream.IntStream;

public class MathHelper
{
    public static long prime(int i)
    {
        return PRIMES[(i < 0 ? -i : i) % PRIMES.length];
    }
    
    public static long gcd(long a, long b)
    {
        return b == 0 ? a : gcd(b, a % b);
    }
    
    public static long mmi(long a, long m)
    {
        if(m <= 1)
            throw new IllegalArgumentException(m + " is not a proper modulus!");
        
        long t = 0, nt = 1, r = m, nr = a % m;
        
        if(a < 0)
        {
            a = m - (-a % m);
        }
        
        while(nr != 0)
        {
            long q = r / nr, tt;
            tt = nt; nt = t - q * nt; t = tt;
            tt = nr; nr = r - q * nr; r = tt;
        }
        
        if(t < 0) t += m;
        if(r > 1) t = -1;
        
        return t;
    }
    
    public static long pow_mod(long x, long n, long m)
    {
        if(m <= 1)
            throw new IllegalArgumentException(m + " is not a proper modulus");
        if(n < 0)
            throw new IllegalArgumentException("You can't handle negative exponents!");
        
        if(n == 0)
            return 1;
        
        long y = 1L;
        
        while(n > 1)
        {
            if((n & 1) == 1)
            {
                y = (x * y) % m;
            }

            x = (x * x) % m;
            n >>= 1;
        }
        
        return (y * x) % m;
    }
    
    public static int[] findPrimes(int min, int max)
    {
        boolean[] a = new boolean[max];
        
        IntStream.range(0, a.length).forEach(i -> a[i] = true);
        
        for(int i = 2 ; i * i < a.length ; ++i)
        {
            if(a[i]) for(int j = i * i ; j < a.length ; j += i)
            {
                a[j] = false;
            }
        }
        
        return IntStream.range(min, max).filter(i -> a[i]).toArray();
    }
    
    private static int[] PRIMES = findPrimes(100, 1000);
    
    private MathHelper() { }
}
