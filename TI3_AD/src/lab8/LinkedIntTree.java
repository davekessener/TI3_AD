package lab8;

import lab7.AANode;
import lab7.LinkedAATree;

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
    
    public int getSumBetween(int m, int M)
    {
        if(dirty_)
        {
            recalculate();
            dirty_ = false;
        }
        
        IntNode a_m = (IntNode) findBorder(m, -1, getRoot());
        IntNode a_M = (IntNode) findBorder(M, 1, getRoot());
        
        return a_M.getWorth() - a_m.getWorth() + a_m.getValue();
    }
    
    private void recalculate()
    {
        ((IntNode) getRoot()).calculate(0);
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
            
            return m == null ? n : m;
        }
    }
    
    @Override
    protected int compare(Integer a, Integer b)
    {
        int r = super.compare(a, b);
        
        return r == 0 ? 0 : r < 0 ? -1 : 1;
        
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
        
        private int calculate(int leftparent)
        {
            worth_ = getValue() + (getLeft() == null ? leftparent : ((IntNode) getLeft()).calculate(leftparent));
            
            return getRight() == null ? worth_ : ((IntNode) getRight()).calculate(worth_);
        }
    }
}
