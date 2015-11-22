package lab7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedAATree<T> implements SearchTree<T>
{
    private Comparator<T> comp_;
    private AANode<T> root_;
    private int size_;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LinkedAATree()
    {
        this((o1, o2) -> {
           return o1 instanceof Comparable ? ((Comparable) o1).compareTo(o2) : o1.hashCode() - o2.hashCode(); 
        });
    }
    
    public LinkedAATree(Comparator<T> c)
    {
        comp_ = c;
        clear();
    }
    
    protected AANode<T> getRoot()
    {
        return root_;
    }
    
    protected int compare(T a, T b)
    {
        return comp_.compare(a, b);
    }

    @Override
    public void insert(T v)
    {
        if(v == null) throw new IllegalArgumentException("Tree can't contain null!");
        if(find(v, root_) != null) return;
        
        root_ = insert(v, root_);
        
        ++size_;
    }
    
    private AANode<T> insert(T v, AANode<T> n)
    {
        if(n == null)
            return makeNode(v);
        
        int r = compare(v, n.getValue());
        
        if(r < 0)
            n.setLeft(insert(v, n.getLeft()));
        else if(r > 0)
            n.setRight(insert(v, n.getRight()));
        
        return split(skew(n));
    }
    
    @Override
    public void remove(T v)
    {
        if(find(v, root_) == null)
            throw new IllegalArgumentException("Can't remove " + v + ", it's not in the tree!");
        
        root_ = remove(v, root_);
        
        --size_;
    }
    
    private AANode<T> remove(T v, AANode<T> n)
    {
        if(n == null)
            return null;
        
        int r = compare(v, n.getValue());
        
        if(r < 0)
            n.setLeft(remove(v, n.getLeft()));
        else if(r > 0)
            n.setRight(remove(v, n.getRight()));
        else if(n.isLeaf())
            return null;
        else
            remove_nonleaf(v, n);
        
        n = skew(n.reduce());
        n.setRight(skew(n.getRight()));
        if(n.getRight() != null) n.getRight().setRight(skew(n.getRight().getRight()));
        
        n = split(n);
        n.setRight(split(n.getRight()));
        
        return n;
    }

    @Override
    public boolean contains(T v)
    {
        return find(v, root_) != null;
    }

    @Override
    public void clear()
    {
        root_ = null;
        size_ = 0;
    }

    @Override
    public int size()
    {
        return size_;
    }

    @Override
    public boolean isEmpty()
    {
        return root_ == null;
    }

    private AANode<T> skew(AANode<T> n)
    {
        return  (n == null || n.getLeft() == null || n.getLevel() != n.getLeft().getLevel()) 
                ? n 
                : n.reverse();
    }
    
    private AANode<T> split(AANode<T> n)
    {
        return  (n == null || n.getRight() == null || n.getRight().getRight() == null || n.getLevel() != n.getRight().getRight().getLevel()) 
                ? n 
                : n.elevate();
    }

    private AANode<T> find(T v, AANode<T> n)
    {
        if(n == null || v == null)
            return null;
        
        int r = compare(v, n.getValue());
        
        return r == 0 ? n : find(v, r < 0 ? n.getLeft() : n.getRight());
    }

    private void remove_nonleaf(T v, AANode<T> n)
    {
        
        if(n.getLeft() == null)
        {
            AANode<T> m = n.getRight();
            n.setRight(remove(m.getValue(), m));
            n.setValue(m.getValue());
        }
        else
        {
            AANode<T> m = n.getLeft();
            n.setLeft(remove(m.getValue(), m));
            n.setValue(m.getValue());
        }
    }

    @Override
    public List<T> preorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        preorder(r, root_);
        
        return r;
    }

    @Override
    public List<T> inorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        inorder(r, root_);
        
        return r;
    }

    @Override
    public List<T> postorder()
    {
        List<T> r = new ArrayList<>(size_);
        
        postorder(r, root_);
        
        return r;
    }
    
    private void preorder(List<T> l, AANode<T> n)
    {
        if(n != null)
        {
            l.add(n.getValue());
            preorder(l, n.getLeft());
            preorder(l, n.getRight());
        }
    }
    
    private void inorder(List<T> l, AANode<T> n)
    {
        if(n != null)
        {
            inorder(l, n.getLeft());
            l.add(n.getValue());
            inorder(l, n.getRight());
        }
    }
    
    private void postorder(List<T> l, AANode<T> n)
    {
        if(n != null)
        {
            postorder(l, n.getLeft());
            postorder(l, n.getRight());
            l.add(n.getValue());
        }
    }

    protected AANode<T> makeNode(T v) { return new AANode<T>(v, 1, null, null); }
}
