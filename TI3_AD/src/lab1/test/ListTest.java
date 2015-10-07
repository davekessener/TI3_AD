package lab1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import lab1.List;
import lab1.List.Pos;

import org.junit.Before;
import org.junit.Test;

public abstract class ListTest
{
    private List<Integer> list;
    private final Integer i1 = 87322873, i2 = -2367;
    
    @Before
    public void setup()
    {
        list = generateImplementation();
    }
    
    public abstract List<Integer> generateImplementation();
    
    @Test
    public void semantics()
    {
        for(int i = 0 ; i < 10 ; ++i)
        {
            list.insert(list.end(), i);
        }

        assertEquals(list.retrieve(list.begin()), Integer.valueOf(0));
        assertEquals(list.retrieve(list.find(3)), Integer.valueOf(3));
        assertEquals(list.find(list.retrieve(list.begin())), list.begin());
    }
    
    @Test
    public void positiveTest()
    {
        assertEquals(list.size(), 0);
        
        list.insert(list.begin(), i1);
        list.insert(list.begin(), i2);
        
        assertEquals(list.size(), 2);
        
        assertEquals(list.find(i2), list.begin());

        assertEquals(list.retrieve(list.begin()), i2);
        assertEquals(list.retrieve(list.begin().next()), i1);
        
        assertEquals(list.retrieve(list.find(i1)), i1);
        
        list.delete(list.begin());
        
        assertNull(list.find(i2));
        
        assertEquals(list.size(), 1);
        
        assertEquals(list.retrieve(list.begin()), i1);
        
        list.delete(list.begin());
        
        assertEquals(list.size(), 0);
    }
    
    @Test
    public void testConcat()
    {
        int[] is = new int[] {723643, -5492, 342, 9348, 7542, -36412736, 426, 53487, -2364239, 8};
        List<Integer> l1 = generateImplementation();
        List<Integer> l2 = generateImplementation();

        int i = 0;
        for(; i < is.length / 2 ; ++i)
        {
            l1.insert(l1.end(), is[i]);
        }
        for(; i < is.length ; ++i)
        {
            l2.insert(l2.end(), is[i]);
        }
        
        l1.concat(l2);
        
        assertEquals(l1.size(), is.length);
        
        i = 0;
        for(Pos i1 = l1.begin(), i2 = l1.end() ; !i1.equals(i2) ; i1 = i1.next())
        {
            assertEquals(l1.retrieve(i1), Integer.valueOf(is[i++]));
        }
        
        assertEquals(l1.size(), i);
    }
    
    @Test(expected = AssertionError.class)
    public void cantDeleteOnEmpty()
    {
        list.delete(list.begin());
    }
    
    @Test(expected = AssertionError.class)
    public void cantDeleteOutOfBounds()
    {
        list.insert(list.begin(), i1);
        list.delete(list.begin().next());
    }
    
    @Test(expected = AssertionError.class)
    public void cantInsertNull()
    {
        list.insert(list.begin(), null);
    }
    
    @Test
    public void cantFindWhatsNotThere()
    {
        list.insert(list.begin(), i1);
        assertNull(list.find(i2));
        assertEquals(list.find(i1), list.begin());
    }
    
    @Test(expected = AssertionError.class)
    public void cantRetrieveOutOfBounds()
    {
        list.insert(list.begin(), i1);
        list.retrieve(list.begin().next());
    }
}
