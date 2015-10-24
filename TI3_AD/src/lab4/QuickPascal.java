package lab4;

import measurement.AbstractMeasurable;

public class QuickPascal extends AbstractMeasurable implements Pascal
{
    @Override
    public int[] calculateRow(int n)
    {
        assert n >= 1 : "Precondition violated: n >= 1";
        
        int[] r = new int[n];
        
        r[0] = 1;

        addCounter(1);
        
        for(int i = 0 ; i < n - 1 ; ++i)
        {
            r[i + 1] = r[i] * (n - 1 - i) / (i + 1);
            addCounter(1);
        }
        
        return r;
    }
}
