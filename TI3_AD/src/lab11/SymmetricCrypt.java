package lab11;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class SymmetricCrypt implements Crypt
{
    private Random rand_;
    
    public SymmetricCrypt(long seed)
    {
        rand_ = new Random(seed);
    }
    
    private int nextCryptKey()
    {
        return rand_.nextInt(MAX - 1) + 1;
    }
    
    @Override
    public String encrypt(String s)
    {
        return toString(doEncrypt(s));
    }
    
    protected int[] doEncrypt(String s)
    {
        if(!StandardCharsets.US_ASCII.newEncoder().canEncode(s))
            throw new IllegalArgumentException(s + " is not ASCII and cannot be processed!");
        
        int[] clear = toArray(s);
        int[] crypt = new int[clear.length + 8];
        
        int s0 = nextCryptKey(), s1 = nextCryptKey();
        
        while(s0 == s1) s1 = nextCryptKey();
        
        crypt[0] = s0;
        crypt[1] = s1;
        
        for(int i = 0 ; i < clear.length ; i += 2)
        {
            crypt[i + 8] = (clear[i] + s0) % MAX;
            if(i + 1 < clear.length) crypt[i + 1 + 8] = (clear[i + 1] + s1) % MAX;
        }
        
        return crypt;
    }

    @Override
    public String decrypt(String s)
    {
        return doDecrypt(toArray(s));
    }
    
    protected String doDecrypt(int[] a)
    {
        int[] clear = new int[a.length - 8];
        
        int s0 = a[0], s1 = a[1];
        
        for(int i = 0 ; i < clear.length ; i += 2)
        {
            clear[i] = (a[i + 8] + MAX - s0) % MAX;
            if(i + 1 < clear.length) clear[i + 1] = (a[i + 1 + 8] + MAX - s1) % MAX;
        }
        
        return toString(clear);
    }
    
    protected static String toString(int[] a)
    {
        return new String(Arrays.stream(a).map(i -> i + 32).toArray(), 0, a.length);
    }
    
    protected static int[] toArray(String s)
    {
        return s.chars().map(i -> i - 32).toArray();
    }
    
    protected static final int MAX = 95;
}
