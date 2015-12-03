package lab9.test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lab9.Dykstra;
import lab9.Graph;
import lab9.ListGraph;
import lab9.MapGraph;
import measurement.Digest;
import measurement.Format;
import measurement.Generator;
import measurement.Measurable;
import measurement.ParameterFactory;
import measurement.PrintFormat;

@SuppressWarnings({"unchecked"})
public class GraphDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        for(int k = 5 ; k <= 5 ; ++k)
        {
            for(int i = 0 ; i < 10 ; ++i)
            {
                addInvocation(String.format("%d %02d", k - 1, i), new GraphGenerator(k));
            }
        }
    }

    @Override
    protected void initializeClasses()
    {
        addClass(ListImplementation.class);
        addClass(MapImplementation.class);
    }

    @Override
    protected void initializeMethods()
    {
        addMethod(callPathfind());
    }

    @Override
    protected Format getFormat()
    {
        return new PrintFormat();
    }
    
    private static ParameterFactory callPathfind()
    {
        ParameterFactory pf = new ParameterFactory("findPath", "findPath");
        
        return pf;
    }
    
    private static class GraphGenerator implements Generator
    {
        private int size_;
        
        public GraphGenerator(int i)
        {
            for(size_ = 10 ; --i > 0 ; size_ *= 10);
        }

        @Override
        public Object generate(Object o)
        {
            Graph<Integer> g = (Graph<Integer>) o;
            Random r = ((IGraphData) o).getRand();
            Set<Integer> nodes = new HashSet<Integer>();
            
            while(g.size() < size_)
            {
                int v = r.nextInt();
                
                if(!nodes.contains(v))
                {
                    g.insert(v);
                    nodes.add(v);
                }
            }
            
            Integer[] n = nodes.toArray(new Integer[0]);
            
            for(int i = 0 ; i < 3 * size_ ; ++i)
            {
                g.connect(n[r.nextInt(n.length)], n[r.nextInt(n.length)], r.nextDouble() * 16.0 + 8.0);
            }
            
            return g;
        }
    }
    
    private static interface IGraphData extends Measurable
    {
        public Random getRand();
    }
    
    private static class GraphData implements IGraphData
    {
        private long t_;
        private Random rand_;
        
        public GraphData(long s)
        {
            t_ = 0;
            rand_ = new Random(s);
        }
        
        public void inc()
        {
            ++t_;
        }

        @Override
        public void resetCounter()
        {
            t_ = 0;
        }

        @Override
        public long getCounter()
        {
            return t_;
        }

        @Override
        public Random getRand()
        {
            return rand_;
        }
    }
    
    public static class ListImplementation extends ListGraph<Integer> implements IGraphData
    {
        private static final GraphData d_ = new GraphData(20151203);

        public void findPath()
        {
            Dykstra.find(this, get(getRand().nextInt(size())));
        }
        
        @Override
        protected Iterable<Integer> neighbors(Integer t, Accessor a)
        {
            return super.neighbors(t, (i1, i2) -> { d_.inc(); return a.access(i1, i2); });
        }
        
        @Override
        public Iterable<Integer> getOutNeighbors(Integer t)
        {
            d_.inc();
            
            return super.getOutNeighbors(t);
        }
        
        @Override
        public double getWeight(Integer t1, Integer t2)
        {
            d_.inc();
            
            return super.getWeight(t1, t2);
        }

        @Override
        public void resetCounter()
        {
            d_.resetCounter();
        }

        @Override
        public long getCounter()
        {
            return d_.getCounter();
        }

        @Override
        public Random getRand()
        {
            return d_.getRand();
        }
    }
    
    public static class MapImplementation extends MapGraph<Integer> implements IGraphData
    {
        private static final GraphData d_ = new GraphData(20151203);

        public void findPath()
        {
            Dykstra.find(this, get(getRand().nextInt(size())));
        }

        @Override
        protected Iterable<Integer> neighbors(Integer t, Accessor a)
        {
            return super.neighbors(t, (i1, i2) -> { d_.inc(); return a.access(i1, i2); });
        }
        
        @Override
        public Iterable<Integer> getOutNeighbors(Integer t)
        {
            d_.inc();
            
            return super.getOutNeighbors(t);
        }
        
        @Override
        public double getWeight(Integer t1, Integer t2)
        {
            d_.inc();
            
            return super.getWeight(t1, t2);
        }

        @Override
        public void resetCounter()
        {
            d_.resetCounter();
        }

        @Override
        public long getCounter()
        {
            return d_.getCounter();
        }

        @Override
        public Random getRand()
        {
            return d_.getRand();
        }
    }
}
