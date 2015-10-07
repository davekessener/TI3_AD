package lab1.test;

import lab1.LinkedArrayList;
import lab1.List;

public class LinkedArrayListTest extends ListTest
{
    @Override
    public List<Integer> generateImplementation()
    {
        return new LinkedArrayList<Integer>();
    }
}
