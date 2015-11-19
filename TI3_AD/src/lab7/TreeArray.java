package lab7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 */

/**
 * Project: AD_2_7
 *
 * @author Andreas Mueller
 * @MatrNr 209 918 2
 *
 *         18.11.2015
 */
public class TreeArray<T extends Comparable<T>> implements SearchTree<T>
{
    private T[] arr;

    @SuppressWarnings("unchecked")
    public TreeArray()
    {
        arr = (T[]) Array.newInstance(arr.getClass().getComponentType(), 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#insert(java.lang.Object)
     */
    @Override
    public void insert(T v)
    {
        T[] newArr = Arrays.copyOf(arr, arr.length + 1);

        newArr[newArr.length - 1] = v;

        Arrays.sort(newArr);

        arr = newArr;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#remove(java.lang.Object)
     */
    @Override
    public void remove(T v)
    {
        int i = 0;
        boolean found = false;

        while(!found && i < arr.length)
        {
            if(arr[i] == v)
            {
                found = true;
                for(int j = i ; j < arr.length ; j++)
                {
                    arr[j] = arr[j + 1];
                }
            }
            else
            {
                i++;
            }
        }

        if(found)
            arr = Arrays.copyOf(arr, arr.length - 1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#contains(java.lang.Object)
     */
    @Override
    public boolean contains(T v)
    {
        boolean isIn = false;
        for(int i = 0 ; i < arr.length ; i++)
        {
            if(arr[i] == v)
                isIn = true;
        }
        return isIn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#clear()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear()
    {
        arr = (T[]) Array.newInstance(arr.getClass().getComponentType(), 0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#size()
     */
    @Override
    public int size()
    {
        return arr.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        return arr.length == 0 ? true : false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#preorder()
     */
    @Override
    public List<T> preorder()
    {
        return preorder(1);
    }

    public List<T> preorder(int index)
    {
        List<T> elemPO = new ArrayList<T>();

        // Root
        elemPO.add(arr[index - 1]);

        // Left Leaf
        if(index * 2 - 1 <= arr.length)
        {
            elemPO.addAll(preorder(index * 2 - 1));
        }

        // Right Leaf
        if(index * 2 <= arr.length)
        {
            elemPO.addAll(preorder(index * 2));
        }

        return elemPO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#inorder()
     */
    @Override
    public List<T> inorder()
    {
        return inorder(1);
    }

    public List<T> inorder(int index)
    {
        List<T> elemIO = new ArrayList<T>();

        // Left Leaf
        if(index * 2 - 1 <= arr.length)
        {
            elemIO.addAll(preorder(index * 2 - 1));
        }

        // Root
        elemIO.add(arr[index - 1]);

        // Right Leaf
        if(index * 2 <= arr.length)
        {
            elemIO.addAll(preorder(index * 2));
        }

        return elemIO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see lab7.SearchTree#postorder()
     */
    @Override
    public List<T> postorder()
    {
        return postorder(1);
    }

    public List<T> postorder(int index)
    {
        List<T> elemPO = new ArrayList<T>();

        // Left Leaf
        if(index * 2 - 1 <= arr.length)
        {
            elemPO.addAll(preorder(index * 2 - 1));
        }

        // Right Leaf
        if(index * 2 <= arr.length)
        {
            elemPO.addAll(preorder(index * 2));
        }

        // Root
        elemPO.add(arr[index - 1]);

        return elemPO;
    }
}
