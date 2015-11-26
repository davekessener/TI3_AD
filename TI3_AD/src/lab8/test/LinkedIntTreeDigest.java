package lab8.test;

import java.util.Random;

import lab8.LinkedIntTree;
import measurement.Digest;
import measurement.Format;
import measurement.Generator;
import measurement.Measurable;
import measurement.ParameterFactory;
import measurement.PrintFormat;

public class LinkedIntTreeDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        Random r = new Random(20151125L);
        
        for(int k = 0 ; k < 7 ; ++k)
        {
            for(int i = 0 ; i < 100 ; ++i)
            {
                addInvocation("k=" + k + " [" + i + "]", new TreeFiller(k, r));
            }
        }
    }

    @Override
    protected void initializeClasses()
    {
        addClass(IntTree.class);
    }

    @Override
    protected void initializeMethods()
    {
        addMethod(generateFind(Integer.MAX_VALUE));
    }
    
    private static ParameterFactory generateFind(int c)
    {
        ParameterFactory pf = new ParameterFactory("calculate" + c, "getSumBetween");

        pf.addParameter(int.class, o -> -c / 2);
        pf.addParameter(int.class, o ->  c / 2);
        
        return pf;
    }

    @Override
    protected Format getFormat()
    {
        return new PrintFormat();
    }
    
    private static class TreeFiller implements Generator
    {
        private int c_;
        private Random rand_;
        
        private TreeFiller(int c, Random rand)
        {
            c_ = 1;
            while(--c >= 0) c_ *= 10;
            rand_ = rand;
        }
        
        @Override
        public Object generate(Object o)
        {
            LinkedIntTree t = (LinkedIntTree) o;
            
            while(t.size() < c_) t.insert(rand_.nextInt());
            
            return o;
        }
    }
    
    public static class IntTree extends LinkedIntTree implements Measurable
    {
        private long counter_ = 0;
        
        @Override
        protected int compare(Integer a, Integer b)
        {
            ++counter_;
            
            return super.compare(a, b);
        }
        
        @Override
        public void resetCounter()
        {
            counter_ = 0;
        }

        @Override
        public long getCounter()
        {
            return counter_;
        }
    }
}
