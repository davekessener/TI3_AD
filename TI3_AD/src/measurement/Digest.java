package measurement;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public abstract class Digest
{
    private Measurer measurer_;
    
    public Digest() { this(new Measurer()); }
    public Digest(Measurer m)
    {
        measurer_ = m;
    }

    protected abstract void initializeInvocations();
    protected abstract void initializeClasses();
    protected abstract void initializeMethods();
    protected abstract Format getFormat();
    
    public final void run()
    {
        initialize();
        execute();
    }
    
    protected void initialize()
    {
        initializeInvocations();
        initializeClasses();
        initializeMethods();
    }
    
    protected void addInvocation(String id, Generator f)
    {
        measurer_.addInvocation(id, f);
    }
    
    protected void addClass(Class<?> c)
    {
        measurer_.addClass(c);
    }
    
    protected void addMethod(ParameterFactory pf)
    {
        measurer_.addMethod(pf);
    }
    
    protected void handleOutput(Format f)
    {
        System.out.println(f.toString());
    }
    
    protected void execute()
    {
        try
        {
            Format f = getFormat();
            examine(measurer_.run(), f);
            handleOutput(f);
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

    protected void examine(Map<Class<?>, Map<String, Map<String, Long>>> results, Format formatter)
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
}
