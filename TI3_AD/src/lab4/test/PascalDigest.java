package lab4.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lab4.IterativePascal;
import lab4.QuickPascal;
import lab4.RecursivePascal;
import measurement.Digest;
import measurement.Format;
import measurement.ParameterFactory;

public class PascalDigest extends Digest
{
    @Override
    protected void initializeInvocations()
    {
        addInvocation("default", o -> o);
    }

    @Override
    protected void initializeClasses()
    {
        addClass(RecursivePascal.class);
        addClass(IterativePascal.class);
        addClass(QuickPascal.class);
    }

    @Override
    protected void initializeMethods()
    {
        for(int i : LOGSPACE)
        {
            addMethod(generateCall(i));
        }
    }

    @Override
    protected Format getFormat()
    { 
        return new Format() {
            private String class_;
            private Map<String, List<Long>> vals_ = new HashMap<>();
            
            @Override
            public void onClass(Class<?> c)
            {
                class_ = c.getSimpleName();
            }

            @Override
            public void onInvocation(String id)
            {
            }

            @Override
            public void onMethod(String name, Long value)
            {
                if(!vals_.containsKey(class_)) vals_.put(class_, new ArrayList<>());
                
                vals_.get(class_).add(value);
            }
            
            @Override
            public String toString()
            {
                StringBuilder sb = new StringBuilder();
                
                sb.append("N = ").append(IntStream.of(LOGSPACE).boxed().collect(Collectors.toList()).toString()).append(";\n");
                
                for(String c : vals_.keySet())
                {
                    sb.append(c).append(" = ").append(vals_.get(c).toString()).append(";\n");
                }
                
                return sb.toString();
            }
        };
    }

    private static ParameterFactory generateCall(int n)
    {
        ParameterFactory pf = new ParameterFactory("calculateRow_" + n, "calculateRow");
        
        pf.addParameter(int.class, o -> n);
        
        return pf;
    }
    
    private static final int[] LOGSPACE = {
        1,
        2,
        3,
        4,
        7,
        11,
        18,
        30,
        48,
        78,
        127,
        207,
        336,
        546,
        886,
        1438,
        2336,
        3793,
        6158,
        10000
    };
}
