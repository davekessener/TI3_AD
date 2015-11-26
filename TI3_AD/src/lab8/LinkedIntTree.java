package lab8;

import lab7.AANode;
import lab7.LinkedAATree;

/**
 * A Linked BST that offers a method to compute the
 * sum of all stored values within a given intervall.
 * 
 * @author Daniel Kessener
 */
public class LinkedIntTree extends LinkedAATree<Integer>
{
    private boolean dirty_ = true;
    
    @Override
    public void insert(Integer i)
    {
        super.insert(i);
        
        dirty_ = true;
    }
    
    @Override
    public void remove(Integer i)
    {
        super.remove(i);

        dirty_ = true;
    }
    
    /**
     * Calculates the sum of all elements <tt>i</tt> in the tree
     * where <tt>m &lt;= i &lt;= M</tt>
     * @require m &lt; M
     * @param m Lower bound
     * @param M Upper bound
     * @return 0 if no fitting element can be found
     * @throws IllegalArgumentException if not <tt>m &lt; M</tt>
     */
    public int getSumBetween(int m, int M)
    {
        if(dirty_)
        {
            recalculate();
            dirty_ = false;
        }
        
        if(m >= M)
            throw new IllegalArgumentException("[" + m + ", " + M + "] is an invalid interval!");
        
        IntNode a_m = (IntNode) findBorder(m, -1, getRoot());
        IntNode a_M = (IntNode) findBorder(M, 1, getRoot());
        
        if(a_m == null || a_M == null)
            return 0;
        
        return a_M.getWorth() - a_m.getWorth() + a_m.getValue();
    }
    
    private AANode<Integer> findBorder(int v, int c, AANode<Integer> n)
    {
        if(n == null)
            return null;
        
        int r = compare(v, n.getValue());
        
        if(r == 0)
            return n;
        else
        {
            AANode<Integer> child = r < 0 ? n.getLeft() : n.getRight();
            
            if(child == null)
                return r == c ? n : null;
            
            AANode<Integer> m = findBorder(v, c, child);
            
            return m == null ? (r == c ? n : null) : m;
        }
    }
    
    @Override
    protected int compare(Integer a, Integer b)
    {
        int r = super.compare(a, b);
        
        return r == 0 ? 0 : r < 0 ? -1 : 1;
        
    }

    private void recalculate()
    {
        calculate((IntNode) getRoot(), 0);
    }
    
    private static int calculate(IntNode n, int lp)
    {
        if(n == null)
            return lp;
        
        n.worth_ = n.getValue() + calculate(n.getLeft(), lp);
        
        return calculate(n.getRight(), n.worth_);
    }
    
    @Override
    protected AANode<Integer> makeNode(Integer i) { return new IntNode(i); }
    
    private static class IntNode extends AANode<Integer>
    {
        private int worth_;
        
        private IntNode(Integer i)
        {
            super(i, 1, null, null);
            
            worth_ = i;
        }
        
        private int getWorth() { return worth_; }
        @Override public IntNode getLeft() { return (IntNode) super.getLeft(); }
        @Override public IntNode getRight() { return (IntNode) super.getRight(); }
    }
}
