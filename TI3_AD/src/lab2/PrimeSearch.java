package lab2;

import java.util.List;

public interface PrimeSearch
{
    public static final int FIRST_PRIME = 2;

    /**
     * Finded alle Primzahlen im Interval [FIRST_PRIME, p)
     * 
     * @param p Obere Suchgrenze; nicht inklusieve
     * 
     * @require p > FIRST_PRIME
     */
    public abstract List<Integer> getPrimesLessThan(int p);
}
