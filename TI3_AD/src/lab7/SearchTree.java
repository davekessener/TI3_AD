package lab7;

import java.util.List;

/**
 * Binary Search Tree
 * 
 * AD 7
 * 
 * @author Daniel Kessener 2159858
 * @author Andreas Mueller
 *
 * @param <T> Should implement Comparable interface, otherwise it will be compare by hashCode
 */
public interface SearchTree<T>
{
    /**
     * Inserts new element into the tree
     * @param v
     * @require v != null
     * @ensure contains(v)
     * @throws IllegalArgumentException if <tt>v</tt> is <tt>null</tt>
     */
    public void insert(T v);
    
    /**
     * Removes element from tree
     * @param v
     * @require contains(v)
     * @throws IllegalArgumentException if <tt>v</tt> is not in the tree
     */
    public void remove(T v);
    
    /**
     * checks if element <tt>v</tt> is in the tree
     * @param v
     * @return
     * @require v != null
     * @throws IllegalArgumentException if <tt>v</tt> is <tt>null</tt>
     */
    public boolean contains(T v);
    
    /**
     * Empties list
     * @ensure isEmpty()
     */
    public void clear();
    
    /**
     * 
     * @return Number of elements in the tree
     */
    public int size();
    
    /**
     * 
     * @return Whether tree is empty or not
     */
    public boolean isEmpty();
    
    /**
     * 
     * @return List of elements in pre-order
     */
    public List<T> preorder();
    
    /**
     * 
     * @return List of elements in order
     */
    public List<T> inorder();
    
    /**
     * 
     * @return List of elements in prost-order
     */
    public List<T> postorder();
}
