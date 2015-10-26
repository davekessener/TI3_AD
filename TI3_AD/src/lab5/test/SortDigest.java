package lab5.test;

import java.util.Random;

import lab5.MeasurableQuicksort;
import lab5.MedianQuicksort;
import lab5.RandomQuicksort;
import lab5.SimpleQuicksort;
import measurement.Digest;
import measurement.Format;
import measurement.Measurable;
import measurement.ParameterFactory;
import measurement.PrintFormat;

public class SortDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        addInvocation("k=1", o -> ((SortWrapper) o).init(10));
        addInvocation("k=2", o -> ((SortWrapper) o).init(100));
        addInvocation("k=3", o -> ((SortWrapper) o).init(1000));
        addInvocation("k=4", o -> ((SortWrapper) o).init(10000));
    }

    @Override
    protected void initializeClasses()
    {
        addClass(SimpleQSWrapper.class);
        addClass(MedianQSWrapper.class);
        addClass(RandomQSWrapper.class);
    }

    @Override
    protected void initializeMethods()
    {
        addMethod(new ParameterFactory("sortBest", "sortBest"));
        addMethod(new ParameterFactory("sortWorst", "sortWorst"));
        
        for(int i = 0 ; i < 10 ; ++i)
        {
            addMethod(new ParameterFactory("sortMean" + (i + 1), "sortMean"));
        }
    }

    @Override
    protected Format getFormat()
    {
        return new PrintFormat();
    }
    
    public static class MedianQSWrapper extends SortWrapper
    {
        @Override
        protected MeasurableQuicksort generateImplementation()
        {
            return new MedianQuicksort();
        }
        
        @Override
        protected void fillBest(Integer[] a, int i1, int i2, int v)
        {
            for(int i = 0 ; i < a.length ; ++i)
            {
                a[i] = v++;
            }
        }

        @Override
        protected void fillWorst(Integer[] a, int i1, int i2, int v)
        {
            if(i1 == i2)
            {
                a[i1] = v;
            }
            else if(i1 + 1 == i2)
            {
                a[i1] = v + 1;
                a[i2] = v;
            }
            else
            {
                int i = (i1 + i2) / 2;
                
                a[i1] = a[i] = v;
                a[i2] = v + 1;
                
                fillWorst(a, i1, i - 1, v);
                fillWorst(a, i + 1, i2, v);
            }
        }
    }
    
    public static class RandomQSWrapper extends SortWrapper
    {
        @Override
        protected MeasurableQuicksort generateImplementation()
        { 
            return new RandomQuicksort(); 
        }
        
        @Override
        protected void fillBest(Integer[] a, int i1, int i2, int v)
        {
            fillRandom(a, i1, i2, v);
        }

        @Override
        protected void fillWorst(Integer[] a, int i1, int i2, int v)
        {
            fillRandom(a, i1, i2, v);
        }
    }
    
    public static class SimpleQSWrapper extends SortWrapper
    {
        @Override
        protected MeasurableQuicksort generateImplementation()
        { 
            return new SimpleQuicksort(); 
        }
        
        @Override
        protected void fillBest(Integer[] a, int i1, int i2, int v)
        {
            for(int i = 0 ; i < a.length ; ++i)
            {
                a[i] = v++;
            }
        }

        @Override
        protected void fillWorst(Integer[] a, int i1, int i2, int v)
        {
            if(i1 >= i2)
            {
                a[i1] = v;
            }
            else if(i1 + 1 == i2)
            {
                a[i1] = v + 1;
                a[i2] = v;
            }
            else
            {
                int i = (i1 + i2) / 2;
                a[i] = v;
                fillWorst(a, i1, i - 1, v + 1);
                fillWorst(a, i + 1, i2, v + 1);
            }
        }
    }
    
    public static abstract class SortWrapper implements Measurable
    {
        private Integer[] a_;
        private MeasurableQuicksort sort_;
        
        public SortWrapper init(int l)
        {
            sort_ = generateImplementation();
            a_ = new Integer[l];
            
            return this;
        }
        
        public void sortBest()
        {
            fillBest(a_, 0, a_.length - 1, 0);
            sort_.sort(a_);
        }
        
        public void sortMean()
        {
            fillRandom(a_, 0, a_.length - 1, 0);
            sort_.sort(a_);
        }
        
        public void sortWorst()
        {
            fillWorst(a_, 0, a_.length - 1, 0);
            sort_.sort(a_);
        }
        
        protected abstract MeasurableQuicksort generateImplementation();
        protected abstract void fillBest(Integer[] a, int i1, int i2, int v);
        protected abstract void fillWorst(Integer[] a, int i1, int i2, int v);

        protected void fillRandom(Integer[] a, int i1, int i2, int v)
        {
            Random rand = new Random(System.currentTimeMillis());
            
            for(int i = i1 ; i <= i2 ; ++i)
            {
                a[i] = rand.nextInt();
            }
        }
        
        @Override
        public void resetCounter()
        {
            sort_.resetCounter();
        }
        
        @Override
        public long getCounter()
        {
            return sort_.getCounter();
        }
    }
}
