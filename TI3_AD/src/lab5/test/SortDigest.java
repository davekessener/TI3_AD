package lab5.test;

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
        addMethod(new ParameterFactory("sort", "sort"));
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
        protected void fill(Integer[] a, int i1, int i2, int v)
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
                
                fill(a, i1, i - 1, v);
                fill(a, i + 1, i2, v);
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
        protected void fill(Integer[] a, int i1, int i2, int v)
        {
            for(int i = i1 ; i <= i2 ; ++i)
            {
                a[i] = v;
            }
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
        protected void fill(Integer[] a, int i1, int i2, int v)
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
                fill(a, i1, i - 1, v + 1);
                fill(a, i + 1, i2, v + 1);
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
            fill(a_, 0, a_.length - 1, 0);
            
            return this;
        }
        
        public void sort()
        {
            sort_.sort(a_);
        }
        
        protected abstract MeasurableQuicksort generateImplementation();
        protected abstract void fill(Integer[] a, int i1, int i2, int v);

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
