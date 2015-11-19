package lab7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LinkedPriorityHeap<T> implements SearchTree<T>
{
    private Comparator<T> comp_;
    private Node<T> root_;
    private int size_;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LinkedPriorityHeap()
    {
        this((o1, o2) -> {
           return o1 instanceof Comparable ? ((Comparable) o1).compareTo(o2) : o1.hashCode() - o2.hashCode(); 
        });
    }
    
    public LinkedPriorityHeap(Comparator<T> c)
    {
        comp_ = c;
        clear();
    }
    
    @Override
    public void insert(T v)
    {
        if(v == null) throw new IllegalArgumentException("Tree can't contain null!");
        
        if(root_ == null)
        {
            root_ = new Node<T>(v, null);
            size_ = 1;
        }
        else
        {
            Node<T> n = makeLast(root_, size_);
            
            n.value_ = v;
            swim(n);
            
            ++size_;
        }
    }

    @Override
    public void remove(T v)
    {
        Node<T> n = find(v, root_);
        
        if(n != null)
        {
            Node<T> m = makeLast(root_, size_ - 1);
            
            if(n != root_) n.swap(root_);
            
            m.swap(root_);
            
            m.remove();
            
            --size_;
            
            if(n != root_)
            {
                n.swap(root_);                
                if(comp_.compare(n.value_, n.up_.value_) < 0) swim(n); else sink(n);
            }
            else
            {
                sink(n);
            }
        }
        else throw new IllegalArgumentException("Can't remove " + v + ", it's not in the tree!");
    }
    
    @Override
    public boolean contains(T v)
    {
        if(v == null) throw new IllegalArgumentException("null cannot be in the tree!");
        
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
    
    private void preorder(List<T> l, Node<T> n)
    {
        if(n != null)
        {
            l.add(n.value_);
            preorder(l, n.left_);
            preorder(l, n.right_);
        }
    }
    
    private void inorder(List<T> l, Node<T> n)
    {
        if(n != null)
        {
            inorder(l, n.left_);
            l.add(n.value_);
            inorder(l, n.right_);
        }
    }
    
    private void postorder(List<T> l, Node<T> n)
    {
        if(n != null)
        {
            postorder(l, n.left_);
            postorder(l, n.right_);
            l.add(n.value_);
        }
    }

    private void sink(Node<T> n)
    {
        while(n.left_ != null)
        {
            Node<T> m = n.left_;
            
            if(n.right_ != null && comp_.compare(n.right_.value_, m.value_) < 0)
                m = n.right_;
            
            if(comp_.compare(m.value_, n.value_) >= 0)
                break;
            
            n.swap(m);
            n = m;
        }
    }
    
    private void swim(Node<T> n)
    {
        while(n.up_ != null && comp_.compare(n.value_, n.up_.value_) < 0)
        {
            n.swap(n.up_);
            n = n.up_;
        }
    }
    
    private Node<T> find(T v, Node<T> n)
    {
        if(n == null)
            return null;
        
        int r = comp_.compare(v, n.value_);
        
        if(r < 0) n = null;
        else if(r > 0)
        {
            Node<T> m = find(v, n.left_);
            if(m == null) m = find(v, n.right_);
            n = m;
        }
        
        return n;
    }

    private Node<T> makeLast(Node<T> n, int s)
    {
        if(s == 0) return n;
        
        int i = s + 1, j = i, c = 1;
        
        while((j >>= 1) != 1) ++c;
        
        while(--c > 0)
        {
            n = (i & (1 << c)) == 0 ? n.left_ : n.right_;
        }

        Node<T> m = new Node<>(null, n);
        
        j = i & 1;
        
        if(n.left_ == null && j == 0) n.left_ = m;
        else if(n.right_ == null && j == 1) n.right_ = m;
        
        return j == 1 ? n.right_ : n.left_;
    }
    
    private static class Node<T>
    {
        private T value_;
        private Node<T> up_, left_, right_;

        private Node(T v, Node<T> u) { this(v, u, null, null); }
        private Node(T v, Node<T> u, Node<T> l, Node<T> r)
        {
            value_ = v;
            up_ = u;
            left_ = l;
            right_ = r;
        }
        
        private void swap(Node<T> n)
        {
            T v = value_;
            
            value_ = n.value_;
            
            n.value_ = v;
        }
        
        private void remove()
        {
            if(up_ != null) up_.disown(this);
        }
        
        private void disown(Node<T> n)
        {
            assert n != null : "Vorbedingung verletzt: n != null";
            
            if(left_ == n) left_ = null;
            else if(right_ == n) right_ = null;
            else throw new IllegalArgumentException(n + " is not a child of " + this);
        }
    }
}
