package lab2.test;

import java.util.List;

import lab2.PrimeIdentifier;
import lab2.PrimeSearch;
import measurement.Digest;
import measurement.Format;
import measurement.IdentityGenerator;
import measurement.MatlabFormat;
import measurement.ParameterFactory;

public class PrimeTestDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        addInvocation("Prime Test", new IdentityGenerator());
    }

    @Override
    protected void initializeClasses()
    {
        addClass(PrimeIDWrapper.class);
    }

    @Override
    protected void initializeMethods()
    {
        int[] primes = {
            13,
            86028121,
            179424673,
            275604541,
            373587883,
            472882027,
            573259391,
            674506081,
            776531401,
            879190747,
            982451653,
            1086218491,
            1190494759,
            1295202449,
            1400305337,
            1505776939,
            1611623773,
            1717783147,
            1824261409,
            1931045213,
            2038074743,
            2145390523,
            2147483647
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
    
    public static class PrimeIDWrapper extends PrimeIdentifier implements PrimeSearch
    {
        @Override
        public List<Integer> getPrimesLessThan(int p)
        {
            isPrime(p);
            
            return null;
        }
    }
}
