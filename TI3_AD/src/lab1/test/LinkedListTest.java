package lab1.test;

import lab1.LinkedList;
import lab1.List;

public class LinkedListTest extends ListTest
{
    @Override
    public List<Integer> generateImplementation()
    {
        return new LinkedList<Integer>();
    }
}
