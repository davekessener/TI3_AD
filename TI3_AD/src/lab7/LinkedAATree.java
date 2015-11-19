package lab7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedAATree<T> implements SearchTree<T>
{
    private Comparator<T> comp_;
    private Node root_;
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

    @Override
    public void insert(T v)
    {
        if(v == null) throw new IllegalArgumentException("Tree can't contain null!");
        if(find(v, root_) != null) return;
        
        root_ = insert(v, root_);
        
        ++size_;
    }
    
    private Node insert(T v, Node n)
    {
        if(n == null)
            return new Node(v);
        
        int r = comp_.compare(v, n.value_);
        
        if(r < 0)
            n.left_ = insert(v, n.left_);
        else if(r > 0)
            n.right_ = insert(v, n.right_);
        
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
    
    private Node remove(T v, Node n)
    {
        if(n == null)
            return null;
        
        int r = comp_.compare(v, n.value_);
        
        if(r < 0)
            n.left_ = remove(v, n.left_);
        else if(r > 0)
            n.right_ = remove(v, n.right_);
        else if(n.isLeaf())
            return null;
        else
            n.remove_nonleaf(v);
        
        n = skew(n.reduce());
        n.right_ = skew(n.right_);
        if(n.right_ != null) n.right_.right_ = skew(n.right_.right_);
        
        n = split(n);
        n.right_ = split(n.right_);
        
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

    private Node skew(Node n)
    {
        return  (n == null || n.left_ == null || n.lvl_ != n.left_.lvl_) 
                ? n 
                : n.reverse();
    }
    
    private Node split(Node n)
    {
        return  (n == null || n.right_ == null || n.right_.right_ == null || n.lvl_ != n.right_.right_.lvl_) 
                ? n 
                : n.elevate();
    }

    private Node find(T v, Node n)
    {
        if(n == null || v == null)
            return null;
        
        int r = comp_.compare(v, n.value_);
        
        return r == 0 ? n : find(v, r < 0 ? n.left_ : n.right_);
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
    
    private void preorder(List<T> l, Node n)
    {
        if(n != null)
        {
            l.add(n.value_);
            preorder(l, n.left_);
            preorder(l, n.right_);
        }
    }
    
    private void inorder(List<T> l, Node n)
    {
        if(n != null)
        {
            inorder(l, n.left_);
            l.add(n.value_);
            inorder(l, n.right_);
        }
    }
    
    private void postorder(List<T> l, Node n)
    {
        if(n != null)
        {
            postorder(l, n.left_);
            postorder(l, n.right_);
            l.add(n.value_);
        }
    }

    private class Node
    {
        private T value_;
        private int lvl_;
        private Node left_, right_;
        
        private Node(T v) { this(v, 1, null, null); }
        private Node(T v, int l) { this(v, l, null, null); }
        private Node(T v, int lvl, Node l, Node r)
        {
            value_ = v;
            lvl_ = lvl;
            left_ = l;
            right_ = r;
        }
        
        private boolean isLeaf()
        {
            return left_ == null && right_ == null;
        }

        private Node reverse()
        {
            Node l = left_;
            left_ = l.right_;
            l.right_ = this;
            return l;
        }
        
        private Node elevate()
        {
            Node r = right_;
            right_ = r.left_;
            r.left_ = this;
            ++r.lvl_;
            return r;
        }
        
        private Node reduce()
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

        private void remove_nonleaf(T v)
        {
            if(left_ == null)
            {
                Node m = right_;
                right_ = remove(m.value_, right_);
                value_ = m.value_;
            }
            else
            {
                Node m = left_;
                left_ = remove(m.value_, left_);
                value_ = m.value_;
            }
        }
    }
}
