package lab2.test;

import lab2.EratosthenesPrimeSearch;
import lab2.FastPrimeSearch;
import lab2.SlowPrimeSearch;
import measurement.Digest;
import measurement.Format;
import measurement.IdentityGenerator;
import measurement.MatlabFormat;
import measurement.ParameterFactory;

public class PrimeSearchDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        addInvocation("Prime Search", new IdentityGenerator());
    }

    @Override
    protected void initializeClasses()
    {
        addClass(SlowPrimeSearch.class);
        addClass(FastPrimeSearch.class);
        addClass(EratosthenesPrimeSearch.class);
    }

    @Override
    protected void initializeMethods()
    {
        int[] primes = {
                10,
                100,
                1000,
                10000,
                100000
        };
        
        for(int p : primes)
        {
            addMethod(generateMethod(p));
        }
    }

    @Override
    protected Format getFormat()
    {
        return new MatlabFormat();
    }
    
    private static ParameterFactory generateMethod(int n)
    {
        ParameterFactory p = new ParameterFactory(String.format("%d Primes", n), "getPrimesLessThan");
        
        p.addParameter(int.class, o -> n);
        
        return p;
    }
}
