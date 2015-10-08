package lab1.measurement;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import lab1.LinearArrayList;
import lab1.LinkedArrayList;
import lab1.LinkedList;

@SuppressWarnings("unchecked")
public final class Digest
{
    private Measurer measurer_;
    
    public Digest()
    {
        measurer_ = new Measurer();
    }
    
    public static void run()
    {
        Digest d = new Digest();
        
        d.initialize();
        d.execute();
    }
    
    public void initialize()
    {
        initializeInvocations();
        initializeClasses();
        initializeMethods();
    }
    
    public void execute()
    {
        try
        {
            Format f = new MatlabFormat();
            examine(measurer_.run(), f);
            System.out.println(f.toString());
        }
        catch(InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | SecurityException
                | IllegalArgumentException
                | InvocationTargetException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void examine(Map<Class<?>, Map<String, Map<String, Long>>> results, Format formatter)
    {
        for(Class<?> c : results.keySet())
        {
            Map<String, Map<String, Long>> inv = results.get(c);
            
            formatter.onClass(c);
            
            for(String id : inv.keySet())
            {
                Map<String, Long> r = inv.get(id);
                
                formatter.onInvocation(id);
                
                for(String method : r.keySet())
                {
                    formatter.onMethod(method, r.get(method));
                }
            }
        }
    }
    
    private void initializeInvocations()
    {
        measurer_.addInvocation("k=1", new ListFiller(10));
        measurer_.addInvocation("k=2", new ListFiller(100));
        measurer_.addInvocation("k=3", new ListFiller(1000));
        measurer_.addInvocation("k=4", new ListFiller(10000));
        measurer_.addInvocation("k=5", new ListFiller(100000));
    }
    
    private void initializeClasses()
    {
        measurer_.addClass(LinearArrayList.class);
        measurer_.addClass(LinkedArrayList.class);
        measurer_.addClass(LinkedList.class);
    }
    
    private void initializeMethods()
    {
        measurer_.addMethod(generateConcat());
        measurer_.addMethod(generateRetrieveBegin());
        measurer_.addMethod(generateRetrieveMiddle());
        measurer_.addMethod(generateRetrieveEnd());
        measurer_.addMethod(generateInsertBegin());
        measurer_.addMethod(generateInsertMiddle());
        measurer_.addMethod(generateInsertEnd());
        measurer_.addMethod(generateDeleteBegin());
        measurer_.addMethod(generateDeleteMiddle());
        measurer_.addMethod(generateDeleteEnd());
        measurer_.addMethod(generateFindBegin());
        measurer_.addMethod(generateFindMiddle());
        measurer_.addMethod(generateFindEnd());
    }
    
    private static class ListFiller implements Generator
    {
        private int s_;
        
        private ListFiller(int i) { s_ = i; }
        
        @Override
        public Object generate(Object o)
        {
            lab1.List<Integer> l = (lab1.List<Integer>) o;
            
            for(int i = 0 ; i < s_ ; ++i)
            {
                l.insert(l.end(), i);
            }
            
            return l;
        }
    }
    
    private static ParameterFactory generateConcat()
    {
        ParameterFactory pf = new ParameterFactory("concat", "concat");
        
        pf.addParameter(lab1.List.class, new Generator() {
            @Override
            public Object generate(Object o)
            {
                lab1.List<Integer> l = null;
                
                try
                {
                     l = (lab1.List<Integer>) o.getClass().newInstance();
                     
                     for(int i = 0, s = ((lab1.List<Integer>) o).size() ; i < s ; ++i)
                     {
                         l.insert(l.end(), i);
                     }
                }
                catch(InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                    System.exit(1);
                }
                
                return l;
            }
        });
        
        return pf;
    }

    private static ParameterFactory generateRetrieveBegin()
    {
        ParameterFactory pf = new ParameterFactory("retrieve_begin", "retrieve");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).begin());
        
        return pf;
    }

    private static ParameterFactory generateRetrieveMiddle()
    {
        ParameterFactory pf = new ParameterFactory("retrieve_middle", "retrieve");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).find(((lab1.List<Integer>) o).size() / 2));
        
        return pf;
    }

    private static ParameterFactory generateRetrieveEnd()
    {
        ParameterFactory pf = new ParameterFactory("retrieve_end", "retrieve");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).find(((lab1.List<Integer>) o).size() - 1));
        
        return pf;
    }

    private static ParameterFactory generateInsertBegin()
    {
        ParameterFactory pf = new ParameterFactory("insert_begin", "insert");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).begin());
        pf.addParameter(Object.class, o -> 0);
        
        return pf;
    }

    private static ParameterFactory generateInsertMiddle()
    {
        ParameterFactory pf = new ParameterFactory("insert_middle", "insert");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).find(((lab1.List<Integer>) o).size() / 2));
        pf.addParameter(Object.class, o -> 0);
        
        return pf;
    }

    private static ParameterFactory generateInsertEnd()
    {
        ParameterFactory pf = new ParameterFactory("insert_end", "insert");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).end());
        pf.addParameter(Object.class, o -> 0);
        
        return pf;
    }

    private static ParameterFactory generateDeleteBegin()
    {
        ParameterFactory pf = new ParameterFactory("delete_begin", "delete");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).begin());
        
        return pf;
    }

    private static ParameterFactory generateDeleteMiddle()
    {
        ParameterFactory pf = new ParameterFactory("delete_middle", "delete");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).find(((lab1.List<Integer>) o).size() / 2));
        
        return pf;
    }

    private static ParameterFactory generateDeleteEnd()
    {
        ParameterFactory pf = new ParameterFactory("delete_end", "delete");
        
        pf.addParameter(lab1.List.Pos.class, o -> ((lab1.List<Integer>) o).find(((lab1.List<Integer>) o).size() - 1));
        
        return pf;
    }

    private static ParameterFactory generateFindBegin()
    {
        ParameterFactory pf = new ParameterFactory("find_begin", "find");
        
        pf.addParameter(Object.class, o -> 0);
        
        return pf;
    }

    private static ParameterFactory generateFindMiddle()
    {
        ParameterFactory pf = new ParameterFactory("find_middle", "find");
        
        pf.addParameter(Object.class, o -> ((lab1.List<Integer>) o).size() / 2);
        
        return pf;
    }

    private static ParameterFactory generateFindEnd()
    {
        ParameterFactory pf = new ParameterFactory("find_end", "find");

        pf.addParameter(Object.class, o -> ((lab1.List<Integer>) o).size() - 1);
        
        return pf;
    }
}
