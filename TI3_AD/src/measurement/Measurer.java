package measurement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Measurer
{
    private List<Class<?>> classes_;
    private List<ParameterFactory> methods_;
    private Map<String, Generator> invocations_;
    
    public Measurer()
    {
        reset();
    }
    
    public void addClass(Class<?> c)
    {
        classes_.add(c);
    }
    
    public void addMethod(ParameterFactory p)
    {
        methods_.add(p);
    }
    
    public void addInvocation(String id, Generator preparation)
    {
        invocations_.put(id, preparation);
    }
    
    public void reset()
    {
        classes_ = new ArrayList<>();
        methods_ = new ArrayList<>();
        invocations_ = new LinkedHashMap<>();
    }

    public Map<Class<?>, Map<String, Map<String, Long>>> run() throws InstantiationException, IllegalAccessException, 
                               NoSuchMethodException, SecurityException, 
                               IllegalArgumentException, InvocationTargetException
    {
        Map<Class<?>, Map<String, Map<String, Long>>> results = new HashMap<>();
        
        for(Class<?> c : classes_)
        {
            Map<String, Map<String, Long>> invocation = new TreeMap<>();
            
            for(String iter_name : invocations_.keySet())
            {
                Map<String, Long> result = new LinkedHashMap<>();
                
                for(ParameterFactory p : methods_)
                {
                    Generator prep = invocations_.get(iter_name);
                    Measurable l = (Measurable) prep.generate(c.newInstance());
                    Method m = c.getMethod(p.getName(), p.asTypes());
                    Object[] ps = p.generateParameters(l);
                    
                    l.resetCounter();

                    m.invoke(l, ps);
                    
                    result.put(p.getID(), l.getCounter());
                }
                
                invocation.put(iter_name, result);
            }
            
            results.put(c, invocation);
        }
        
        reset();
        
        return results;
    }
}
