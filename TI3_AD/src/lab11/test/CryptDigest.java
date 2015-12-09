package lab11.test;

import java.util.Scanner;

import lab11.Crypt;
import lab11.HybridCrypt;
import lab11.RSA;

public class CryptDigest
{
    private Scanner in_ = new Scanner(System.in);
    private Crypt cifer_;
    
    public static void main(String[] args)
    {
        (new CryptDigest()).run();
    }
    
    public void run()
    {
        cifer_ = new HybridCrypt(SEED, getRSA());
        
        printf(">>> c \"str\"\nc == q -> quit\n  == e -> encrypt str\n  == d -> decrypt str\n\n");
        
        while(process(readLine()));
        
        printf("\nGoodbye.\n");
    }
    
    private boolean process(String s)
    {
        try
        {
            if(s != null)
            {
                char a = s.charAt(0);
                
                if(a == 'q') return false;
                
                String msg = s.substring(s.indexOf('"') + 1, s.lastIndexOf('"'));
                
                printLine(a == 'd' ? cifer_.decrypt(msg) : a == 'e' ? cifer_.encrypt(msg) : "Use 'd' for decryption and 'e' for encryption.");
            }
        }
        catch(IndexOutOfBoundsException e)
        {
        }
        
        return true;
    }
    
    private String readLine()
    {
        printf(">>> ");
        String s;
        
        do s = in_.nextLine(); while(s.length() == 0);
        
        return s;
    }
    
    private void printLine(String l)
    {
        printf("\"%s\"\n\n", l);
    }
    
    private RSA getRSA()
    {
        printf("Private Key   (d): ");
        long d = in_.nextLong();
        printf("Public Key I  (n): ");
        long n = in_.nextLong();
        printf("Public Key II (e): ");
        long e = in_.nextLong();
        
        return new RSA(n, e, d);
    }
    
    private void printf(String s, Object ... o)
    {
        System.out.printf(s, o);
    }
    
    private static final long SEED = 20151206L;
}
