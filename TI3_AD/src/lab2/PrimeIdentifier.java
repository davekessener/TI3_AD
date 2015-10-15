package lab2;

import measurement.AbstractMeasurable;

public class PrimeIdentifier extends AbstractMeasurable
{
    /**
     * 
     * @param n Zahl die geprueft werden soll
     * @return Ob n eine Primzahl ist oder nicht.
     */
    public boolean isPrime(int n)
    {
        addCounter(1);
        return n > 1 && (n <= 3 || (n % 2 != 0 && n % 3 != 0 && isPrimeImpl(n, 5)));
    }
    
    private boolean isPrimeImpl(int p, int n)
    {
        for(int s = (int) Math.ceil(Math.sqrt(p)); n < s ; n += 6)
        {
            addCounter(1);
            
            if(p % n == 0 || p % (n + 2) == 0)
                return false;
        }
        
        return true;
    }
}
