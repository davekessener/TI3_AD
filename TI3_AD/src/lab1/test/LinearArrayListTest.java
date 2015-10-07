package lab1.test;

import lab1.LinearArrayList;
import lab1.List;

public class LinearArrayListTest extends ListTest
{
    @Override
    public List<Integer> generateImplementation()
    {
        return new LinearArrayList<Integer>();
    }
}
