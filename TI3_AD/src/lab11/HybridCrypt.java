package lab11;

public class HybridCrypt extends SymmetricCrypt
{
    private RSA rsa_;
    
    public HybridCrypt(long seed, RSA rsa)
    {
        super(seed);
        rsa_ = rsa;
    }
    
    @Override
    protected int[] doEncrypt(String s)
    {
        int[] r = super.doEncrypt(s);

        encryptKey(r[1], r, 4);
        encryptKey(r[0], r, 0);
        
        return r;
    }
    
    @Override
    protected String doDecrypt(int[] a)
    {
        a[0] = decryptKey(a, 0);
        a[1] = decryptKey(a, 4);
        
        return super.doDecrypt(a);
    }
    
    private void encryptKey(int k, int[] a, int i)
    {
        long v = rsa_.encrypt(k);

        a[i + 3] = (int) (v % MAX); v /= MAX;
        a[i + 2] = (int) (v % MAX); v /= MAX;
        a[i + 1] = (int) (v % MAX); v /= MAX;
        a[i + 0] = (int) (v % MAX);
    }
    
    private int decryptKey(int[] a, int i)
    {
        long v;
        
        v = a[i + 0];
        v = a[i + 1] + v * MAX;
        v = a[i + 2] + v * MAX;
        v = a[i + 3] + v * MAX;
        
        return (int) rsa_.decrypt(v);
    }
}
