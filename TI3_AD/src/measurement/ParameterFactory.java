package measurement;

import java.util.ArrayList;
import java.util.List;

public class ParameterFactory
{
    private String methodName_, id_;
    private List<Class<?>> paramTypes_;
    private List<Generator> generators_;
    private Class<?>[] types_;
    
    public ParameterFactory(String id, String name)
    {
        assert name != null : "Precondition violated: name != null";
        assert id != null : "Precondition violated: id != null";
        assert !name.isEmpty() : "Precondition violated: !name.isEmpty()";
        assert !id.isEmpty() : "Precondition violated: !id.isEmpty()";
        
        id_ = id;
        methodName_ = name;
        paramTypes_ = new ArrayList<>();
        generators_ = new ArrayList<>();
        types_ = null;
    }
    
    public void addParameter(Class<?> c, Generator g)
    {
        assert c != null : "Precondition violated: c != null";
        assert g != null : "Precondition violated: g != null";
        
        types_ = null;
        
        paramTypes_.add(c);
        generators_.add(g);
    }
    
    public String getName()
    {
        return methodName_;
    }
    
    public String getID()
    {
        return id_;
    }
    
    public Class<?>[] asTypes()
    {
        if(types_ == null)
            types_ = paramTypes_.toArray(new Class<?>[0]);
        
        return types_;
    }
    
    public Object[] generateParameters(Object o)
    {
        Object[] r = new Object[generators_.size()];
        
        for(int i = 0 ; i < r.length ; ++i)
        {
            r[i] = generators_.get(i).generate(o);
        }
        
        return r;
    }
}
