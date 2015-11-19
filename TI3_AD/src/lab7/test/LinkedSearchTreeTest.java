package lab7.test;

import lab7.LinkedAATree;
import lab7.SearchTree;

public class LinkedSearchTreeTest extends SearchTreeTest
{
    @Override
    protected SearchTree<Integer> getImplementation()
    {
        return new LinkedAATree<>();
    }
}
