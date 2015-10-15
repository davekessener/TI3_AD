import java.util.ArrayList;
import java.util.List;

import lab2.test.PrimeSearchDigest;
import lab2.test.PrimeTestDigest;


public class Start
{
    public static void main(String[] args)
    {
        List<Thread> ts = new ArrayList<Thread>();
        
        ts.add(new Thread(() -> new PrimeSearchDigest().run()));
        ts.add(new Thread(() -> new PrimeTestDigest().run()));
        
        for(Thread t : ts) t.start();
        for(Thread t : ts)
            try
            {
                t.join();
            }
            catch(InterruptedException e)
            {
            }
    }
}
