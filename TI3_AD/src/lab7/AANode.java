package lab7;

public class AANode<T>
{
    private T value_;
    private int lvl_;
    private AANode<T> left_, right_;
    
    public AANode(T v, int lvl, AANode<T> l, AANode<T> r)
    {
        value_ = v;
        lvl_ = lvl;
        left_ = l;
        right_ = r;
    }
    
    public T getValue() { return value_; }
    public void setValue(T v) { assert v != null; value_ = v; }
    public AANode<T> getLeft() { return left_; }
    public void setLeft(AANode<T> l) { left_ = l; }
    public AANode<T> getRight() { return right_; }
    public void setRight(AANode<T> r) { right_ = r; }
    public int getLevel() { return lvl_; }
    public void setLevel(int l) { assert l >= 1; lvl_ = l; }
    
    public boolean isLeaf()
    {
        return left_ == null && right_ == null;
    }

    public AANode<T> reverse()
    {
        AANode<T> l = left_;
        left_ = l.right_;
        l.right_ = this;
        return l;
    }
    
    public AANode<T> elevate()
    {
        AANode<T> r = right_;
        right_ = r.left_;
        r.left_ = this;
        ++r.lvl_;
        return r;
    }
    
    public AANode<T> reduce()
    {
        int l = Math.min(left_ == null ? 0 : left_.lvl_, 
                         right_ == null ? 0 : right_.lvl_) + 1;
        
        if(lvl_ > l)
        {
            lvl_ = l;
            if(right_ != null && right_.lvl_ > l)
                right_.lvl_ = l;
        }
        
        return this;
    }
}
