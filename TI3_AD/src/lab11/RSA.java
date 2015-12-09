package lab11;

import java.util.Random;

public final class RSA
{
    public final long n, e, d;
    
    public RSA(long n, long e, long d)
    {
        this.n = n;
        this.e = e;
        this.d = d;
    }
    
    public RSA(long seed)
    {
        Random rand = new Random(seed);
        
        long p, q, t;
        
        p = MathHelper.prime(rand.nextInt());
        
        do q = MathHelper.prime(rand.nextInt()); while(p == q);
        
        n = p * q;
        t = (p - 1) * (q - 1);
        
        {
            long tt; do tt = MathHelper.prime(rand.nextInt()); while(MathHelper.gcd(tt, t) != 1);
            e = tt;
        }
        
        d = MathHelper.mmi(e, t);
    }
    
    public long encrypt(long m)
    {
        return encrypt(m, e, n);
    }
    
    public long decrypt(long m)
    {
        return decrypt(m, d, n);
    }
    
    public static long encrypt(long m, long e, long n)
    {
        return MathHelper.pow_mod(m, e, n);
    }
    
    public static long decrypt(long m, long d, long n)
    {
        return MathHelper.pow_mod(m, d, n);
    }
}
