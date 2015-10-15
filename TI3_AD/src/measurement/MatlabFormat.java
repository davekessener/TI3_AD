package measurement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatlabFormat extends AbstractFormatter
{
    private Map<String, List<Long>> arrays_ = new LinkedHashMap<>();
    private String currentClass_;
    
    @Override
    public void onClass(Class<?> c)
    {
        assert arrays_ != null : "Precondition violated: arrays_ != null";
        
        currentClass_ = c.toString();
        currentClass_ = currentClass_.substring(currentClass_.indexOf(".") + 1);
    }

    @Override
    public void onInvocation(String id)
    {
        assert arrays_ != null : "Precondition violated: arrays_ != null";
    }

    @Override
    public void onMethod(String name, Long value)
    {
        assert arrays_ != null : "Precondition violated: arrays_ != null";
        
        String id = currentClass_ + "_" + name;
        
        if(!arrays_.containsKey(id))
        {
            arrays_.put(id, new ArrayList<>());
        }
        
        arrays_.get(id).add(value);
    }
    
    @Override
    public String toString()
    {
        if(arrays_ != null)
        {
            for(String s : arrays_.keySet())
            {
                add("%s = %s;\n", s, arrays_.get(s).toString());
            }
            
            arrays_ = null;
        }
        
        return super.toString();
    }
}
