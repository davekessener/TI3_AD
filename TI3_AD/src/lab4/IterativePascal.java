package lab4;

import measurement.AbstractMeasurable;

public class IterativePascal extends AbstractMeasurable implements Pascal
{
    @Override
    public int[] calculateRow(int n)
    {
        assert n >= 1 : "Precondition violated: n >= 1";
        
        int[] r = new int[] {1};

        addCounter(1);
        
        for(int i = 1 ; i < n ; ++i)
        {
            int[] t = new int[r.length + 1];
            
            for(int j = 1 ; j < r.length ; ++j)
            {
                t[j] = r[j - 1] + r[j];
                addCounter(1);
            }
            
            t[0] = t[r.length] = 1;
            addCounter(1);
            
            r = t;
        }
        
        return r;
    }
}
