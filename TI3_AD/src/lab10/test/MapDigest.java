package lab10.test;

import java.util.Iterator;
import java.util.List;

import lab10.HashMap;
import lab10.IP;
import lab10.LogGenerator;
import measurement.Digest;
import measurement.Format;
import measurement.Generator;
import measurement.Measurable;
import measurement.ParameterFactory;

public class MapDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        for(int k = 1 ; k <= 6 ; ++k)
        {
            addInvocation("k=" + k, new Filler(k));
        }
    }

    @Override
    protected void initializeClasses()
    {
        addClass(MeasurableMap.class);
    }

    @Override
    protected void initializeMethods()
    {
        for(int i = 0 ; i < COUNT ; ++i)
        {
            addMethod(generateGet(i));
        }
    }
    
    private static ParameterFactory generateGet(int i)
    {
        ParameterFactory pf = new ParameterFactory(String.format("get%02d", i), "get");
        
        pf.addParameter(IP.class, new GetKey(i));
        
        return pf;
    }
    
    private static class GetKey implements Generator
    {
        private int idx_;
        
        public GetKey(int i) { idx_ = i; }

        @Override
        public Object generate(Object o)
        {
            Iterator<IP> i = ((MeasurableMap) o).keySet().iterator();
            IP ip = i.next();
            
            for(int j = 0 ; j < idx_ && i.hasNext() ; ++j)
            {
                ip = i.next();
            }
            
            return ip;
        }
    }

    @Override
    protected Format getFormat()
    {
        return new Format() {
            private java.util.Map<String, Long> inv_ = new java.util.TreeMap<>();
            private String cur_;

            @Override
            public void onClass(Class<?> c)
            {
            }

            @Override
            public void onInvocation(String id)
            {
                cur_ = id;
            }

            @Override
            public void onMethod(String name, Long value)
            {
                if(inv_.containsKey(cur_))
                    value += inv_.get(cur_);
                inv_.put(cur_, value);
            }
            
            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder();
                
                for(String k : inv_.keySet())
                {
                    sb.append(k).append(": ").append(inv_.get(k).longValue() / COUNT).append('\n');
                }
                
                return sb.toString();
            }
        };
    }
    
    public static class MeasurableMap extends HashMap<IP, String> implements Measurable
    {
        private long counter_ = 0L;
        
        @Override
        public List<String> get(IP k)
        {
            return super.get(k);
        }

        @Override
        public void resetCounter()
        {
            counter_ = 0L;
        }

        @Override
        public long getCounter()
        {
            return counter_;
        }
        
        @Override
        protected int doHash(int h, int i, int l)
        {
            ++counter_;
            
            return super.doHash(h, i, l);
        }
    }
    
    private static class Filler implements Generator
    {
        private int size_ = 0;
        
        public Filler(int s)
        {
            size_ = 1;
            while(s-- > 0) size_ *= 10;
        }
        
        @Override
        public Object generate(Object o)
        {
            if(MAP == null) MAP = (MeasurableMap) o;
            
            assert size_ >= MAP.size() : "Cant reduce mapsize!";
            
            FILLER.fillMap(MAP, size_ - MAP.size());
            
            return MAP;
        }
    }
    
    private static final int COUNT = 999;
    private static MeasurableMap MAP = null;
    private static final long SEED = 20151206;
    private static final LogGenerator FILLER = new LogGenerator(SEED);
}
