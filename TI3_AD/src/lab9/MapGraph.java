package lab9;

public class MapGraph<T> extends AbstractGraph<T>
{
    private double[][] costs_ = new double[0][0];

    @Override
    protected void doRemove(T t)
    {
        int k = lookup(t), s = size();
        
        if(k >= costs_.length)
        {
            for(int i = k ; i < s ; ++i)
            {
                for(int j = 0 ; j < s ; ++j)
                {
                    costs_[k][j] = k + 1 == s ? 0 : costs_[k + 1][j];
                    costs_[j][k] = k + 1 == s ? 0 : costs_[j][k + 1];
                }
            }
        }
    }

    @Override
    public Iterable<T> getInNeighbors(T t)
    {
        return neighbors(t, (i1, i2) -> inBounds(i1, i2) && costs_[i2][i1] != 0);
    }

    @Override
    public Iterable<T> getOutNeighbors(T t)
    {
        return neighbors(t, (i1, i2) -> inBounds(i1, i2) && costs_[i1][i2] != 0);
    }

    @Override
    public Iterable<T> getNeighbors(T t)
    {
        return neighbors(t, (i1, i2) -> inBounds(i1, i2) && costs_[i1][i2] != 0 || costs_[i2][i1] != 0);
    }
    
    @Override
    protected boolean checkNeighbors(T t1, T t2)
    {
        int i1 = lookup(t1), i2 = lookup(t2);
        
        return i1 < costs_.length && i2 < costs_.length && costs_[i1][i2] != 0;
    }
    
    @Override
    protected void setCost(T t1, T t2, double c)
    {
        int i1 = lookup(t1), i2 = lookup(t2);
        
        resize(Math.max(i1, i2));
        
        costs_[i1][i2] = c;
    }
    
    @Override
    protected double getCost(T t1, T t2)
    {
        int i1 = lookup(t1), i2 = lookup(t2);
        
        resize(Math.max(i1, i2));

        return costs_[i1][i2];
    }
    
    @Override
    protected void doDisconnect(T t1, T t2)
    {
        setCost(t1, t2, 0);
    }
    
    private void resize(int s)
    {
        if(costs_.length <= s)
        {
            s = size();
            double[][] c = new double[s][s];
            
            for(int i = 0 ; i < costs_.length ; ++i)
            {
                for(int j = 0 ; j < costs_.length ; ++j)
                {
                    c[i][j] = costs_[i][j];
                }
            }
            
            costs_ = c;
        }
    }
    
    private boolean inBounds(int i1, int i2)
    {
        return inBounds(i1) && inBounds(i2);
    }
    
    private boolean inBounds(int i)
    {
        return i >= 0 && i < costs_.length;
    }
}
