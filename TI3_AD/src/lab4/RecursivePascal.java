package lab4;

import measurement.AbstractMeasurable;

public class RecursivePascal extends AbstractMeasurable implements Pascal
{
    @Override
    public int[] calculateRow(int n)
    {
        assert n >= 1 : "Precondition violated: n >= 1";

        addCounter(1);
        
        if(n == 1) return new int[] {1};
        
        int[] p = calculateRow(n - 1);
        int[] r = new int[p.length + 1];
        
        for(int i = 1 ; i < p.length ; ++i)
        {
            r[i] = p[i - 1] + p[i];
            addCounter(1);
        }
        
        r[0] = r[p.length] = 1;
        
        return r;
    }
}
